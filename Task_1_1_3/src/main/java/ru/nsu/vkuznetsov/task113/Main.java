package ru.nsu.vkuznetsov.task113;

import java.util.Scanner;
import ru.nsu.vkuznetsov.task113.expression.Expression;
import ru.nsu.vkuznetsov.task113.parser.Parser;

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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение (например, (3+(2*x))):");
        String input = scanner.nextLine();

        Expression e = new Parser(input).parse();
        System.out.println("Выражение: " + e.print());

        while (true) {
            System.out.println();
            System.out.println("Что сделать?");
            System.out.println("1 - вычислить значение");
            System.out.println("2 - взять производную");
            System.out.println("0 - выход");
            System.out.print("> ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> {
                    System.out.println("Введите означивание (например, x = 10; y = 13):");
                    String assignments = scanner.nextLine();
                    int result = e.eval(assignments);
                    System.out.println("Значение: " + result);
                }
                case "2" -> {
                    System.out.println("Введите переменную для дифференцирования:");
                    String var = scanner.nextLine();
                    Expression de = e.derivative(var);
                    System.out.println("Производная: " + de.print());
                }
                case "0" -> {
                    System.out.println("Выход.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Введите 1, 2 или 0.");
            }
        }
    }
}