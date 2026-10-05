package ru.nsu.vkuznetsov.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void testPrint() {
        assertEquals("x", new Variable("x").print());
        assertEquals("abc", new Variable("abc").print());
    }

    @Test
    void testDerivativeSameVariable() {
        Expression result = new Variable("x").derivative("x");
        assertEquals("1", result.print());
    }

    @Test
    void testDerivativeDifferentVariable() {
        Expression result = new Variable("x").derivative("y");
        assertEquals("0", result.print());
    }

    @Test
    void testEval() {
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(10, new Variable("x").eval(vars));
    }

    @Test
    void testEvalUnassigned() {
        Map<String, Integer> vars = new HashMap<>();
        assertThrows(IllegalArgumentException.class,
                () -> new Variable("x").eval(vars));
    }
}