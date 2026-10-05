package ru.nsu.vkuznetsov.task113;

import java.util.Map;

/**
 * Абстрактное математическое выражение.
 */
public abstract class Expression {

    /**
     * Возвращает строковое представление выражения.
     *
     * @return выражение в виде строки
     */
    public abstract String print();

    /**
     * Возвращает производную по переменной.
     *
     * @param var имя переменной
     * @return новое выражение — производная
     */
    public abstract Expression derivative(String var);

    /**
     * Вычисляет значение выражения при заданных значениях переменных.
     *
     * @param vars карта переменных
     * @return значение выражения
     */
    public abstract int eval(Map<String, Integer> vars);

    /**
     * Вычисляет значение выражения по строке означивания.
     *
     * @param assignments строка вида "x = 10; y = 13"
     * @return значение выражения
     */
    public int eval(String assignments) {
        Map<String, Integer> vars = AssignmentParser.parse(assignments);
        return eval(vars);
    }
}