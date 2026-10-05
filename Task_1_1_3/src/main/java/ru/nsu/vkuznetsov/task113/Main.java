package ru.nsu.vkuznetsov.task113;

/**
 * Демонстрация работы с выражениями.
 */
public class Main {

    /**
     * Точка входа.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Expression e = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x")));

        System.out.println("Выражение: " + e.print());
        // (3+(2*x))

        Expression de = e.derivative("x");
        System.out.println("Производная: " + de.print());
        // (0+((0*x)+(2*1)))

        int result = e.eval("x = 10; y = 13");
        System.out.println("Значение при x = 10: " + result);
        // 23

        System.out.println();

        Expression parsed = new Parser("(3+(2*x))").parse();
        System.out.println("Из строки: " + parsed.print());

        Expression parsedDerivative = parsed.derivative("x");
        System.out.println("Производная из строки: " + parsedDerivative.print());

        int parsedResult = parsed.eval("x = 10; y = 13");
        System.out.println("Значение из строки: " + parsedResult);

        System.out.println();

        Expression negative = new Parser("(-5+3)").parse();
        System.out.println("Отрицательное число: " + negative.print());
        System.out.println("Значение: " + negative.eval(""));
    }
}