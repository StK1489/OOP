package ru.nsu.vkuznetsov.Task_1_1_1;

import java.util.Arrays;

/**
 * Демонстрация работы пирамидальной сортировки.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};
        System.out.println("Before: " + Arrays.toString(arr));
        Sort.heapsort(arr);
        System.out.println("After: " + Arrays.toString(arr));
    }
}