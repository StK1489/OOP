package ru.nsu.vkuznetsov.task113.operations;

import java.util.Map;
import ru.nsu.vkuznetsov.task113.expression.Expression;

/**
 * Сумма двух выражений.
 */
public class Add extends BinaryOperation {

    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    public int eval(Map<String, Integer> vars) {
        return left.eval(vars) + right.eval(vars);
    }

    @Override
    protected String getOperator() {
        return "+";
    }
}