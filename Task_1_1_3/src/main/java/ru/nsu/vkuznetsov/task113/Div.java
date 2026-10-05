package ru.nsu.vkuznetsov.task113;

import java.util.Map;

/**
 * Частное двух выражений.
 */
public class Div extends BinaryOperation {

    /**
     * Создаёт частное.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "/";
    }

    @Override
    public Expression derivative(String var) {
        return new Div(
                new Sub(
                        new Mul(left.derivative(var), right),
                        new Mul(left, right.derivative(var))
                ),
                new Mul(right, right)
        );
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) / right.eval(vars);
    }
}