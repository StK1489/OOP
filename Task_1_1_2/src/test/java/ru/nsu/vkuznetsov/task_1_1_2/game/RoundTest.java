package ru.nsu.vkuznetsov.task112.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Deck;
import ru.nsu.vkuznetsov.task112.domain.Hand;
import ru.nsu.vkuznetsov.task112.domain.Player;
import ru.nsu.vkuznetsov.task112.domain.RoundResult;

class RoundTest {

    private final GameView stubView = new GameView() {
        @Override
        public void showPlayerHand(Hand hand) {
        }

        @Override
        public void showDealerHand(Dealer dealer) {
        }

        @Override
        public void showRoundResult(RoundResult result, int playerWins, int dealerWins) {
        }

        @Override
        public void showMessage(String message) {
        }

        @Override
        public boolean askPlayAgain() {
            return false;
        }
    };

    private final RoundResolver resolver = new RoundResolver();

    @Test
    void testRoundReturnsResult() {
        Deck deck = new Deck(1);
        deck.shuffle();
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();

        Round round = new Round(deck, player, dealer, stubView, resolver);
        RoundResult result = round.play();

        assertNotNull(result);
    }

    @Test
    void testRoundPlayerStandsImmediately() {
        Deck deck = new Deck(1);
        deck.shuffle();
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();

        Round round = new Round(deck, player, dealer, stubView, resolver);
        round.play();

        assertEquals(2, player.getHand().getCards().size());
    }

    @Test
    void testRoundPlayerTakesCard() {
        Deck deck = new Deck(1);
        deck.shuffle();
        Player player = new Player(() -> true);
        Dealer dealer = new Dealer();

        Round round = new Round(deck, player, dealer, stubView, resolver);
        round.play();

        // Игрок брал карты, пока не перебрал — карт больше 2
        assertEquals(true, player.getHand().getCards().size() > 2);
    }

    @Test
    void testRoundDeckReduced() {
        Deck deck = new Deck(1);
        deck.shuffle();
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();

        Round round = new Round(deck, player, dealer, stubView, resolver);
        round.play();

        // Из колоды взято минимум 4 карты (2 игроку + 2 дилеру)
        assertEquals(true, deck.size() < 52);
    }
}