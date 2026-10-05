package ru.nsu.vkuznetsov.task113;

import java.util.HashMap;
import java.util.Map;

/**
 * Парсер строки означивания переменных.
 */
public class AssignmentParser {

    /**
     * Парсит строку вида "x = 10; y = 13".
     *
     * @param assignments строка означивания
     * @return карта переменных
     */
    public static Map<String, Integer> parse(String assignments) {
        Map<String, Integer> vars = new HashMap<>();
        if (assignments == null || assignments.isBlank()) {
            return vars;
        }
        String[] parts = assignments.split(";");
        for (String part : parts) {
            String[] kv = part.split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException(
                        "Некорректное означивание: " + part);
            }
            String name = kv[0].trim();
            int value = Integer.parseInt(kv[1].trim());
            vars.put(name, value);
        }
        return vars;
    }
}