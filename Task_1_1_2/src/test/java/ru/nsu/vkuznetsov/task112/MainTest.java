package ru.nsu.vkuznetsov.task112;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testMainClassExists() {
        assertDoesNotThrow(() ->
                Class.forName("ru.nsu.vkuznetsov.task112.Main"));
    }
}