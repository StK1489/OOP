package ru.nsu.vkuznetsov.task112.game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Hand;
import ru.nsu.vkuznetsov.task112.domain.Player;
import ru.nsu.vkuznetsov.task112.domain.RoundResult;

class GameTest {

    private final GameView oneRoundView = new GameView() {
        private int roundCount = 0;

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
            roundCount++;
            return roundCount == 1;
        }
    };

    private final GameView noRoundView = new GameView() {
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

    @Test
    void testGameInvalidDeckCount() {
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();
        assertThrows(IllegalArgumentException.class,
                () -> new Game(0, player, dealer, noRoundView));
    }

    @Test
    void testGameNoRounds() {
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();
        Game game = new Game(1, player, dealer, noRoundView);
        assertDoesNotThrow(game::play);
    }

    @Test
    void testGameOneRound() {
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();
        Game game = new Game(1, player, dealer, oneRoundView);
        assertDoesNotThrow(game::play);
    }

    @Test
    void testGameTwoDecksOneRound() {
        Player player = new Player(() -> false);
        Dealer dealer = new Dealer();
        Game game = new Game(2, player, dealer, oneRoundView);
        assertDoesNotThrow(game::play);
    }
}