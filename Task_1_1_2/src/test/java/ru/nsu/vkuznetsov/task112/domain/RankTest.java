package ru.nsu.vkuznetsov.task112.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    void testIsFaceCard() {
        assertTrue(Rank.QUEEN.isFaceCard());
        assertFalse(Rank.TEN.isFaceCard());
    }

    @Test
    void testGetValue() {
        assertEquals(10, Rank.QUEEN.getValue());
        assertEquals(11, Rank.ACE.getValue());
    }

    @Test
    void testGetNameAndGender() {
        assertEquals("Туз", Rank.ACE.getName());
        assertEquals(Gender.FEMININE, Rank.QUEEN.getGender());
    }
}