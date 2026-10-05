package ru.nsu.vkuznetsov.task113;

import java.util.Map;

/**
 * Константа.
 */
public class Number extends Expression {
    private final int value;

    /**
     * Создаёт константу.
     *
     * @param value значение
     */
    public Number(int value) {
        this.value = value;
    }

    @Override
    public String print() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return value;
    }
}