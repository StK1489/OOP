package ru.nsu.vkuznetsov.task112.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void testDeckSize() {
        assertEquals(52, new Deck(1).size());
        assertEquals(104, new Deck(2).size());
    }

    @Test
    void testInvalidCount() {
        assertThrows(IllegalArgumentException.class, () -> new Deck(0));
    }

    @Test
    void testDrawReducesSize() {
        Deck deck = new Deck(1);
        deck.draw();
        assertEquals(51, deck.size());
    }

    @Test
    void testShuffleKeepsSize() {
        Deck deck = new Deck(1);
        deck.shuffle();
        assertEquals(52, deck.size());
    }

    @Test
    void testDrawFromEmptyDeck() {
        Deck deck = new Deck(1);
        for (int i = 0; i < 52; i++) {
            deck.draw();
        }
        assertThrows(IllegalStateException.class, deck::draw);
    }
}