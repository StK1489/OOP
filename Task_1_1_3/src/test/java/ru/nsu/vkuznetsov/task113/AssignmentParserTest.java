package ru.nsu.vkuznetsov.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

class AssignmentParserTest {

    @Test
    void testParseSingle() {
        Map<String, Integer> vars = AssignmentParser.parse("x = 10");
        assertEquals(1, vars.size());
        assertEquals(10, vars.get("x"));
    }

    @Test
    void testParseMultiple() {
        Map<String, Integer> vars = AssignmentParser.parse("x = 10; y = 13");
        assertEquals(2, vars.size());
        assertEquals(10, vars.get("x"));
        assertEquals(13, vars.get("y"));
    }

    @Test
    void testParseEmpty() {
        Map<String, Integer> vars = AssignmentParser.parse("");
        assertTrue(vars.isEmpty());
    }

    @Test
    void testParseWithSpaces() {
        Map<String, Integer> vars = AssignmentParser.parse("  x  =  10 ; y=13 ");
        assertEquals(10, vars.get("x"));
        assertEquals(13, vars.get("y"));
    }
}