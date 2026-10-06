package ru.nsu.vkuznetsov.task113.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import ru.nsu.vkuznetsov.task113.expression.Expression;

class ParserTest {

    @Test
    void testParseNumber() {
        Expression e = new Parser("5").parse();
        assertEquals("5", e.print());
    }

    @Test
    void testParseVariable() {
        Expression e = new Parser("x").parse();
        assertEquals("x", e.print());
    }

    @Test
    void testParseAdd() {
        Expression e = new Parser("(3+5)").parse();
        assertEquals("(3+5)", e.print());
    }

    @Test
    void testParseComplex() {
        Expression e = new Parser("(3+(2*x))").parse();
        assertEquals("(3+(2*x))", e.print());

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(23, e.eval(vars));
    }

    @Test
    void testParseNegative() {
        Expression e = new Parser("(-5+3)").parse();
        assertEquals("(-5+3)", e.print());
        assertEquals(-2, e.eval(new HashMap<>()));
    }

    @Test
    void testParseNegativeVariable() {
        Expression e = new Parser("(-x+3)").parse();
        assertEquals("((0-x)+3)", e.print());

        Map<String, Integer> vars = new HashMap<>();
        vars.put("x", 10);
        assertEquals(-7, e.eval(vars));
    }

    @Test
    void testParseInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parser("(3+5").parse());
    }
}