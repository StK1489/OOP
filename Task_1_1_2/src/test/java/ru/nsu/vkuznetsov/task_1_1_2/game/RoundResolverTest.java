package ru.nsu.vkuznetsov.task112.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.nsu.vkuznetsov.task112.domain.Card;
import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Player;
import ru.nsu.vkuznetsov.task112.domain.Rank;
import ru.nsu.vkuznetsov.task112.domain.RoundResult;
import ru.nsu.vkuznetsov.task112.domain.Suit;

class RoundResolverTest {

    private final RoundResolver resolver = new RoundResolver();

    @Test
    void testPlayerBusted() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.addCard(new Card(Suit.HEARTS, Rank.TEN));
        player.addCard(new Card(Suit.CLUBS, Rank.TEN));

        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.SPADES, Rank.TEN));

        assertEquals(RoundResult.DEALER_WIN, resolver.resolve(player, dealer));
    }

    @Test
    void testDealerBusted() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));

        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.addCard(new Card(Suit.CLUBS, Rank.TEN));

        assertEquals(RoundResult.PLAYER_WIN, resolver.resolve(player, dealer));
    }

    @Test
    void testPlayerHigher() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.addCard(new Card(Suit.HEARTS, Rank.TEN));

        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.addCard(new Card(Suit.HEARTS, Rank.SEVEN));

        assertEquals(RoundResult.PLAYER_WIN, resolver.resolve(player, dealer));
    }

    @Test
    void testDealerHigher() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.addCard(new Card(Suit.HEARTS, Rank.SEVEN));

        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.SPADES, Rank.TEN));
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));

        assertEquals(RoundResult.DEALER_WIN, resolver.resolve(player, dealer));
    }

    @Test
    void testDraw() {
        Player player = new Player(() -> false);
        player.addCard(new Card(Suit.SPADES, Rank.TEN));
        player.addCard(new Card(Suit.HEARTS, Rank.TEN));

        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.CLUBS, Rank.TEN));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.TEN));

        assertEquals(RoundResult.DRAW, resolver.resolve(player, dealer));
    }
}