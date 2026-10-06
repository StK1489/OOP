package ru.nsu.vkuznetsov.task113.operations;

import java.util.Map;

import ru.nsu.vkuznetsov.task113.expression.Expression;

/**
 * Разность двух выражений.
 */
public class Sub extends BinaryOperation {

    /**
     * Создаёт разность.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) - right.eval(vars);
    }

    @Override
    protected String getOperator() {
        return "-";
    }
}