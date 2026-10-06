package ru.nsu.vkuznetsov.task113.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import ru.nsu.vkuznetsov.task113.expression.Expression;
import ru.nsu.vkuznetsov.task113.expression.Number;
import ru.nsu.vkuznetsov.task113.expression.Variable;

class SubTest {

    @Test
    void testPrint() {
        Expression e = new Sub(new Number(10), new Number(3));
        assertEquals("(10-3)", e.print());
    }

    @Test
    void testDerivative() {
        Expression e = new Sub(new Variable("x"), new Number(5));
        assertEquals("(1-0)", e.derivative("x").print());
    }

    @Test
    void testEval() {
        Expression e = new Sub(new Number(10), new Number(3));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(7, e.eval(vars));
    }
}