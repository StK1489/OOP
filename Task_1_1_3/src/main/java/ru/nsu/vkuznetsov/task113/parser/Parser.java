package ru.nsu.vkuznetsov.task113.parser;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.vkuznetsov.task113.expression.Expression;
import ru.nsu.vkuznetsov.task113.expression.Number;
import ru.nsu.vkuznetsov.task113.expression.Variable;
import ru.nsu.vkuznetsov.task113.operations.Add;
import ru.nsu.vkuznetsov.task113.operations.Div;
import ru.nsu.vkuznetsov.task113.operations.Mul;
import ru.nsu.vkuznetsov.task113.operations.Sub;

/**
 * Парсер математических выражений.
 */
public class Parser {
    private final List<String> tokens;
    private int pos;

    /**
     * Создаёт парсер.
     *
     * @param input строка с выражением
     */
    public Parser(String input) {
        this.tokens = tokenize(input);
        this.pos = 0;
    }

    /**
     * Парсит выражение целиком.
     *
     * @return разобранное выражение
     */
    public Expression parse() {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение");
        }
        Expression result = parseExpression();
        if (pos != tokens.size()) {
            throw new IllegalArgumentException(
                    "Лишние токены: " + tokens.subList(pos, tokens.size()));
        }
        return result;
    }

    /**
     * Разбивает строку на токены.
     *
     * @param input строка с выражением
     * @return список токенов
     */
    private List<String> tokenize(String input) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else if (Character.isLetterOrDigit(c)) {
                StringBuilder token = new StringBuilder();
                while (i < input.length()
                        && Character.isLetterOrDigit(input.charAt(i))) {
                    token.append(input.charAt(i));
                    i++;
                }
                result.add(token.toString());
            } else {
                result.add(String.valueOf(c));
                i++;
            }
        }
        return result;
    }

    /**
     * Парсит одно выражение (рекурсивно).
     *
     * @return выражение
     */
    private Expression parseExpression() {
        if (pos >= tokens.size()) {
            throw new IllegalArgumentException("Неожиданный конец выражения");
        }
        if (tokens.get(pos).equals("(")) {
            pos++;
            final Expression left = parseExpression();
            if (pos >= tokens.size()) {
                throw new IllegalArgumentException("Ожидался оператор");
            }
            final String op = tokens.get(pos);
            pos++;
            final Expression right = parseExpression();
            if (pos >= tokens.size() || !tokens.get(pos).equals(")")) {
                throw new IllegalArgumentException("Ожидалась закрывающая скобка");
            }
            pos++;
            return createBinaryOperation(op, left, right);
        }
        return parseNumberOrVariable();
    }

    /**
     * Читает число (возможно отрицательное) или имя переменной.
     *
     * @return Expression (Number или Variable)
     */
    private Expression parseNumberOrVariable() {
        if (pos >= tokens.size()) {
            throw new IllegalArgumentException("Неожиданный конец выражения");
        }
        String token = tokens.get(pos);
        pos++;

        if (token.equals("-")) {
            if (pos >= tokens.size()) {
                throw new IllegalArgumentException("Ожидалось число после минуса");
            }
            String next = tokens.get(pos);
            pos++;
            if (isNumber(next)) {
                return new Number(-Integer.parseInt(next));
            }
            return new Sub(new Number(0), new Variable(next));
        }

        if (isNumber(token)) {
            return new Number(Integer.parseInt(token));
        }
        return new Variable(token);
    }

    /**
     * Проверяет, является ли токен числом.
     *
     * @param token токен
     * @return true, если токен — число
     */
    private boolean isNumber(String token) {
        return !token.isEmpty() && Character.isDigit(token.charAt(0));
    }

    /**
     * Создаёт бинарную операцию по символу.
     *
     * @param op символ операции
     * @param left левый операнд
     * @param right правый операнд
     * @return выражение
     */
    private Expression createBinaryOperation(String op,
                                             Expression left,
                                             Expression right) {
        return switch (op) {
            case "+" -> new Add(left, right);
            case "-" -> new Sub(left, right);
            case "*" -> new Mul(left, right);
            case "/" -> new Div(left, right);
            default -> throw new IllegalArgumentException(
                    "Неизвестная операция: " + op);
        };
    }
}