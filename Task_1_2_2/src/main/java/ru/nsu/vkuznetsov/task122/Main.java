package ru.nsu.vkuznetsov.task122;

import ru.nsu.vkuznetsov.task122.hash.HashTable;

/**
 * Главный класс для демонстрации работы HashTable.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.update("one", 1.0);

        System.out.println(hashTable.get("one"));
    }
}