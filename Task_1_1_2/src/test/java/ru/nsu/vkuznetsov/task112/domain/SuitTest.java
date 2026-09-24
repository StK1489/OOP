package ru.nsu.vkuznetsov.task112.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SuitTest {

    @Test
    void testGetAdjectiveMasculine() {
        assertEquals("Пиковый", Suit.SPADES.getAdjective(Gender.MASCULINE));
    }

    @Test
    void testGetAdjectiveFeminine() {
        assertEquals("Пиковая", Suit.SPADES.getAdjective(Gender.FEMININE));
    }

    @Test
    void testGetNoun() {
        assertEquals("Пики", Suit.SPADES.getNoun());
    }
}