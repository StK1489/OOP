package ru.nsu.vkuznetsov.task_1_1_2.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void testSimpleScore() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.addCard(new Card(Suit.HEARTS, Rank.SEVEN));
        assertEquals(17, hand.getScore());
    }

    @Test
    void testAceAsEleven() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        assertEquals(21, hand.getScore());
    }

    @Test
    void testAceAsOne() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand.addCard(new Card(Suit.CLUBS, Rank.TEN));
        assertEquals(21, hand.getScore());
    }

    @Test
    void testTwoAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        assertEquals(12, hand.getScore());
    }

    @Test
    void testIsBusted() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand.addCard(new Card(Suit.CLUBS, Rank.TEN));
        assertTrue(hand.isBusted());
    }

    @Test
    void testHasBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.ACE));
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        assertTrue(hand.hasBlackjack());
    }

    @Test
    void testGetCardsReturnsCopy() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.getCards().clear();
        assertEquals(1, hand.getCards().size());
    }

    @Test
    void testReset() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.reset();
        assertEquals(0, hand.getCards().size());
    }
}