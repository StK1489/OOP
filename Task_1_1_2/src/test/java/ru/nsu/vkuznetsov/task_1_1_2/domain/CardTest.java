package ru.nsu.vkuznetsov.task112.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testToStringFaceCard() {
        assertEquals("Пиковая Дама (10)", new Card(Suit.SPADES, Rank.QUEEN).toString());
    }

    @Test
    void testToStringNumberCard() {
        assertEquals("Тройка Червы (3)", new Card(Suit.HEARTS, Rank.THREE).toString());
    }

    @Test
    void testGetters() {
        Card card = new Card(Suit.CLUBS, Rank.ACE);
        assertEquals(Suit.CLUBS, card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
    }
}