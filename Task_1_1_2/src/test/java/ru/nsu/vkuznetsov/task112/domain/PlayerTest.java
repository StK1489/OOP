package ru.nsu.vkuznetsov.task112.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void testShouldHitTrue() {
        Player player = new Player(() -> true);
        assertTrue(player.shouldHit());
    }

    @Test
    void testShouldHitFalse() {
        Player player = new Player(() -> false);
        assertFalse(player.shouldHit());
    }

    @Test
    void testAddCardAndScore() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        assertEquals(17, player.getScore());
    }

    @Test
    void testIsBusted() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.addCard(new Card(Suit.HEARTS, Rank.TEN));
        player.addCard(new Card(Suit.CLUBS, Rank.TEN));
        assertTrue(player.isBusted());
    }

    @Test
    void testHasBlackjack() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.ACE));
        player.addCard(new Card(Suit.HEARTS, Rank.TEN));
        assertTrue(player.hasBlackjack());
    }

    @Test
    void testResetHand() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.resetHand();
        assertEquals(0, player.getScore());
    }
}