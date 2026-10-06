package ru.nsu.vkuznetsov.task113.operations;

import java.util.Map;

import ru.nsu.vkuznetsov.task113.expression.Expression;

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

    @Override
    protected String getOperator() {
        return "*";
    }
}