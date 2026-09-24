package ru.nsu.vkuznetsov.Task_1_1_1;

/**
 * Реализация пирамидальной сортировки.
 * Сортирует масив за O (n log n) по времени и O(1) по памяти
 */
public class Sort {

    /**
     * Сортирует массив методом пирамидальной сортировки..
     *
     * @param arr массив для сортировки
     */
    public static void heapsort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    /**
     * Восстанавливает свойство кучи для поддерева с корнем i.
     *
     * @param arr массив
     * @param n размер кучи
     * @param i индекс корня
     */
    private static void heapify(int[] arr, int n, int i) {
        int biggest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[biggest]) {
            biggest = left;
        }
        if (right < n && arr[right] > arr[biggest]) {
            biggest = right;
        }
        if (biggest != i) {
            int temp = arr[i];
            arr[i] = arr[biggest];
            arr[biggest] = temp;
            heapify(arr, n, biggest);
        }
    }
}