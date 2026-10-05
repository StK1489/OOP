package ru.nsu.vkuznetsov.task113;

import java.util.Map;

/**
 * Переменная.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * Создаёт переменную.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public Expression derivative(String var) {
        if (name.equals(var)) {
            return new Number(1);
        }
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        if (vars.containsKey(name)) {
            return vars.get(name);
        }
        throw new IllegalArgumentException("Переменная " + name + " не задана");
    }
}