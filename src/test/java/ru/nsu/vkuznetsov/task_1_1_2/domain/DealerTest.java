package ru.nsu.vkuznetsov.task_1_1_2.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DealerTest {

    @Test
    void testHasHiddenCard() {
        assertTrue(new Dealer().hasHiddenCard());
    }

    @Test
    void testRevealHoleCard() {
        Dealer dealer = new Dealer();
        dealer.revealHoleCard();
        assertFalse(dealer.hasHiddenCard());
    }

    @Test
    void testGetHoleCard() {
        Dealer dealer = new Dealer();
        Card first = new Card(Suit.SPADES, Rank.TEN);
        Card second = new Card(Suit.HEARTS, Rank.SEVEN);
        dealer.addCard(first);
        dealer.addCard(second);
        assertEquals(second, dealer.getHoleCard());
    }

    @Test
    void testShouldHit() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.addCard(new Card(Suit.HEARTS, Rank.SIX));
        assertTrue(dealer.shouldHit());

        dealer.addCard(new Card(Suit.CLUBS, Rank.ACE));
        assertFalse(dealer.shouldHit());
    }

    @Test
    void testResetHand() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.revealHoleCard();
        dealer.resetHand();
        assertTrue(dealer.hasHiddenCard());
        assertEquals(0, dealer.getScore());
    }
}