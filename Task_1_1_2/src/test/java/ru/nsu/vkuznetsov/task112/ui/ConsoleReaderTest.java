package ru.nsu.vkuznetsov.task112.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class ConsoleReaderTest {

    private ConsoleReader readerWithInput(String input) {
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));
        return new ConsoleReader();
    }

    @Test
    void testReadHitOrStandTrue() {
        ConsoleReader reader = readerWithInput("1\n");
        assertTrue(reader.readHitOrStand());
    }

    @Test
    void testReadHitOrStandFalse() {
        ConsoleReader reader = readerWithInput("0\n");
        assertFalse(reader.readHitOrStand());
    }

    @Test
    void testReadPlayAgainTrue() {
        ConsoleReader reader = readerWithInput("1\n");
        assertTrue(reader.readPlayAgain());
    }

    @Test
    void testReadPlayAgainFalse() {
        ConsoleReader reader = readerWithInput("0\n");
        assertFalse(reader.readPlayAgain());
    }

    @Test
    void testReadDeckCountValid() {
        ConsoleReader reader = readerWithInput("3\n");
        assertEquals(3, reader.readDeckCount());
    }

    @Test
    void testReadDeckCountRetries() {
        ConsoleReader reader = readerWithInput("abc\n0\n5\n");
        assertEquals(5, reader.readDeckCount());
    }
}