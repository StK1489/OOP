package ru.nsu.vkuznetsov.task131;

import ru.nsu.vkuznetsov.task131.finder.SubstringFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Демонстрация поиска подстрок в UTF-8-файле.
 */
public final class Main {

    private Main() {
    }

    /**
     * Создаёт временный файл, показывает результаты поиска и удаляет файл.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Path file = null;
        try {
            file = Files.createTempFile("substring-demo-", ".txt");
            String text = "абракадабра\nаааа\nПривет, мир!";
            Files.writeString(file, text);

            System.out.println("Демонстрация поиска подстрок");
            System.out.println("Файл: " + file.getFileName());
            System.out.println("Текст:\n" + text);

            for (String target : List.of("бра", "аа", "мир", "нет")) {
                List<Long> positions = SubstringFinder.find(file.toString(), target);
                System.out.println("\nПодстрока: " + target);
                System.out.println("Количество вхождений: " + positions.size());
                System.out.println("Индексы: " + positions);
            }

            System.out.println("\nПроверка обработки ошибки:");
            try {
                SubstringFinder.find("", "бра");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка перехвачена: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
        } finally {
            if (file != null) {
                try {
                    Files.deleteIfExists(file);
                    System.out.println("\nВременный файл удалён.");
                } catch (IOException e) {
                    System.err.println("Не удалось удалить файл: " + e.getMessage());
                }
            }
        }
    }
}
