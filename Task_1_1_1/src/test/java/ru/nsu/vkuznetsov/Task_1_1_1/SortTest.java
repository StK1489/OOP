package ru.nsu.vkuznetsov.Task_1_1_1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    @Test
    void testSimpleSort() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testSingleElement() {
        int[] input = {1488};
        int[] expected = {1488};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testReverseSorted() {
        int[] input = {9, 7, 5, 3, 0};
        int[] expected = {0, 3, 5, 7, 9};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testWithNegativeNumbers() {
        int[] input = {-5, 0, -1, 10, -3};
        int[] expected = {-5, -3, -1, 0, 10};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testWithDuplicates() {
        int[] input = {3, 1, 3, 2, 1};
        int[] expected = {1, 1, 2, 3, 3};
        Sort.heapsort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void testNullArray() {
        Sort.heapsort(null);
    }

    @Test
    void testMainExecution() {
        Main.main(new String[]{});
    }
}
