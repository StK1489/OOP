package ru.nsu.vkuznetsov.task_1_1_2.ui;

import java.util.Scanner;

/**
 * Читает ввод пользователя из консоли.
 */
public class ConsoleReader {
    private static final int MIN_DECK_COUNT = 1;
    private static final int MAX_DECK_COUNT = 8;

    private final Scanner scanner;

    /**
     * Создаёт читатель консоли.
     */
    public ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    /**
     * Спрашивает количество колод (от 1 до 8).
     * Переспрашивает, пока не введено корректное число.
     *
     * @return количество колод
     */
    public int readDeckCount() {
        while (true) {
            System.out.println("Сколько колод использовать? (1-8)");
            String input = scanner.nextLine().trim();
            try {
                int count = Integer.parseInt(input);
                if (count >= MIN_DECK_COUNT && count <= MAX_DECK_COUNT) {
                    return count;
                }
                System.out.println("Введите число от " + MIN_DECK_COUNT + " до " + MAX_DECK_COUNT + ".");
            } catch (NumberFormatException e) {
                System.out.println("Это не число. Попробуйте снова.");
            }
        }
    }

    /**
     * Спрашивает, взять ли карту.
     *
     * @return true, если взять; false, если остановиться
     */
    public boolean readHitOrStand() {
        while (true) {
            System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться.");
            String input = scanner.nextLine().trim();
            if ("1".equals(input)) {
                return true;
            }
            if ("0".equals(input)) {
                return false;
            }
            System.out.println("Введите 1 или 0.");
        }
    }

    /**
     * Спрашивает, продолжать ли игру.
     *
     * @return true, если продолжать
     */
    public boolean readPlayAgain() {
        while (true) {
            System.out.println("Сыграть раунд? (1 - да, 0 - нет)");
            String input = scanner.nextLine().trim();
            if ("1".equals(input)) {
                return true;
            }
            if ("0".equals(input)) {
                return false;
            }
            System.out.println("Введите 1 или 0.");
        }
    }
}