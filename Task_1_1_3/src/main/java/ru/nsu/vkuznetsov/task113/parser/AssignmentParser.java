package ru.nsu.vkuznetsov.task113.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

        List<String> assignmentList = new ArrayList<>(
                Arrays.asList(assignments.split(";")));

        for (String assignment : assignmentList) {
            List<String> keyValue = new ArrayList<>(
                    Arrays.asList(assignment.split("=")));

            if (keyValue.size() != 2) {
                throw new IllegalArgumentException(
                        "Некорректное означивание: " + assignment);
            }

            String name = keyValue.get(0).trim();
            int value = Integer.parseInt(keyValue.get(1).trim());
            vars.put(name, value);
        }

        return vars;
    }
}