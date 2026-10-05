package ru.nsu.vkuznetsov.task113;

/**
 * Парсер математических выражений.
 */
public class Parser {
    private final String input;
    private int pos;

    /**
     * Создаёт парсер.
     *
     * @param input строка с выражением
     */
    public Parser(String input) {
        this.input = input;
        this.pos = 0;
    }

    /**
     * Парсит выражение целиком.
     *
     * @return разобранное выражение
     */
    public Expression parse() {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение");
        }
        Expression result = parseExpression();
        if (pos != input.length()) {
            throw new IllegalArgumentException(
                    "Лишние символы в конце: " + input.substring(pos));
        }
        return result;
    }

    /**
     * Парсит одно выражение (рекурсивно).
     *
     * @return выражение
     */
    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Неожиданный конец выражения");
        }
        if (input.charAt(pos) == '(') {
            pos++;
            final Expression left = parseExpression();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался оператор");
            }
            final char op = input.charAt(pos);
            pos++;
            final Expression right = parseExpression();
            if (pos >= input.length() || input.charAt(pos) != ')') {
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
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Неожиданный конец выражения");
        }
        boolean negative = false;
        if (input.charAt(pos) == '-') {
            negative = true;
            pos++;
        }
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Ожидалось число после минуса");
        }

        final int start = pos;
        while (pos < input.length()
                && Character.isLetterOrDigit(input.charAt(pos))) {
            pos++;
        }
        final String token = input.substring(start, pos);

        if (token.isEmpty()) {
            throw new IllegalArgumentException("Ожидалось число или переменная");
        }

        if (Character.isDigit(token.charAt(0))) {
            int value = Integer.parseInt(token);
            if (negative) {
                value = -value;
            }
            return new Number(value);
        }
        return new Variable(token);
    }

    /**
     * Создаёт бинарную операцию по символу.
     *
     * @param op символ операции
     * @param left левый операнд
     * @param right правый операнд
     * @return выражение
     */
    private Expression createBinaryOperation(char op,
                                             Expression left,
                                             Expression right) {
        return switch (op) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException(
                    "Неизвестная операция: " + op);
        };
    }
}