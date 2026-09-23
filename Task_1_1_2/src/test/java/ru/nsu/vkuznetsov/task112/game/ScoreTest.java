package ru.nsu.vkuznetsov.task112.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.nsu.vkuznetsov.task112.domain.RoundResult;

class ScoreTest {

    @Test
    void testPlayerWin() {
        Score score = new Score();
        score.update(RoundResult.PLAYER_WIN);
        assertEquals(1, score.getPlayerWins());
    }

    @Test
    void testDealerWin() {
        Score score = new Score();
        score.update(RoundResult.DEALER_WIN);
        assertEquals(1, score.getDealerWins());
    }

    @Test
    void testDraw() {
        Score score = new Score();
        score.update(RoundResult.DRAW);
        assertEquals(0, score.getPlayerWins());
        assertEquals(0, score.getDealerWins());
    }
}