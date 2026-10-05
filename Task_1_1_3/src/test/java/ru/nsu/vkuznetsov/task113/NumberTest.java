package ru.nsu.vkuznetsov.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void testPrint() {
        assertEquals("5", new Number(5).print());
        assertEquals("-3", new Number(-3).print());
    }

    @Test
    void testDerivative() {
        Expression result = new Number(5).derivative("x");
        assertEquals("0", result.print());
    }

    @Test
    void testEval() {
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(5, new Number(5).eval(vars));
        assertEquals(-3, new Number(-3).eval(vars));
    }
}