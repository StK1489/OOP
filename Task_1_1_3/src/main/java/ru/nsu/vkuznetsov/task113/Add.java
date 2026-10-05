package ru.nsu.vkuznetsov.task113;

import java.util.Map;

/**
 * Сумма двух выражений.
 */
public class Add extends BinaryOperation {

    /**
     * Создаёт сумму.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "+";
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) + right.eval(vars);
    }
}