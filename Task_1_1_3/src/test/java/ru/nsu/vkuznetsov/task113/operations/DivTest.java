package ru.nsu.vkuznetsov.task113.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task113.expression.Expression;
import ru.nsu.vkuznetsov.task113.expression.Number;
import ru.nsu.vkuznetsov.task113.expression.Variable;

class DivTest {

    @Test
    void testPrint() {
        Expression e = new Div(new Number(10), new Number(2));
        assertEquals("(10/2)", e.print());
    }

    @Test
    void testDerivative() {
        Expression e = new Div(new Variable("x"), new Number(2));
        assertEquals("(((1*2)-(x*0))/(2*2))", e.derivative("x").print());
    }

    @Test
    void testEval() {
        Expression e = new Div(new Number(10), new Number(2));
        Map<String, Integer> vars = new HashMap<>();
        assertEquals(5, e.eval(vars));
    }
}