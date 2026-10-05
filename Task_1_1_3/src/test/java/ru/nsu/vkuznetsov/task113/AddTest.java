package ru.nsu.vkuznetsov.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void testPrint() {
        Expression e = new Add(new Number(3), new Number(5));
        assertEquals("(3+5)", e.print());
    }

    @Test
    void testDerivative() {
        Expression e = new Add(new Variable("x"), new Number(5));
        assertEquals("(1+0)", e.derivative("x").print());
    }

    @Test
    void testEval() {
        Expression e = new Add(new Number(3), new Number(5));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(8, e.eval(vars));
    }
}