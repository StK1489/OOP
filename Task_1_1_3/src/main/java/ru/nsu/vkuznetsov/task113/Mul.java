package ru.nsu.vkuznetsov.task113;

import java.util.Map;

/**
 * Произведение двух выражений.
 */
public class Mul extends BinaryOperation {

    /**
     * Создаёт произведение.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "*";
    }

    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var))
        );
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) * right.eval(vars);
    }
}