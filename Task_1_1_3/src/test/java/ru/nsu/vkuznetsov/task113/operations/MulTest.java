package ru.nsu.vkuznetsov.task113.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import ru.nsu.vkuznetsov.task113.expression.Expression;
import ru.nsu.vkuznetsov.task113.expression.Number;
import ru.nsu.vkuznetsov.task113.expression.Variable;

class MulTest {

    @Test
    void testPrint() {
        Expression e = new Mul(new Number(2), new Variable("x"));
        assertEquals("(2*x)", e.print());
    }

    @Test
    void testDerivative() {
        Expression e = new Mul(new Variable("x"), new Variable("x"));
        assertEquals("((1*x)+(x*1))", e.derivative("x").print());
    }

    @Test
    void testEval() {
        Expression e = new Mul(new Number(2), new Variable("x"));
        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(20, e.eval(vars));
    }
}