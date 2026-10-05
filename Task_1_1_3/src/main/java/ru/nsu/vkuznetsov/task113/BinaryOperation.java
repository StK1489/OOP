package ru.nsu.vkuznetsov.task113;

/**
 * Бинарная операция.
 */
public abstract class BinaryOperation extends Expression {
    protected final Expression left;
    protected final Expression right;

    /**
     * Создаёт бинарную операцию.
     *
     * @param left левый операнд
     * @param right правый операнд
     */
    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Возвращает символ операции.
     *
     * @return оператор ("+", "-", "*", "/")
     */
    protected abstract String getOperator();

    @Override
    public String print() {
        return "("
                + left.print()
                + getOperator()
                + right.print()
                + ")";
    }
}