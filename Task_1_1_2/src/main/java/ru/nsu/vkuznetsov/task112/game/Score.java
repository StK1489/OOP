package ru.nsu.vkuznetsov.task112.game;

import ru.nsu.vkuznetsov.task112.domain.RoundResult;

/**
 * Счёт игры: количество побед игрока и дилера.
 */
public class Score {
    private int playerWins;
    private int dealerWins;

    /**
     * Создаёт счёт с нулевыми значениями.
     */
    public Score() {
        playerWins = 0;
        dealerWins = 0;
    }

    /**
     * Обновляет счёт по результату раунда.
     *
     * @param result результат раунда
     */
    public void update(RoundResult result) {
        switch (result) {
            case PLAYER_WIN -> playerWins++;
            case DEALER_WIN -> dealerWins++;
            case DRAW -> { }
        }
    }

    /**
     * Возвращает количество побед игрока.
     *
     * @return победы игрока
     */
    public int getPlayerWins() {
        return playerWins;
    }

    /**
     * Возвращает количество побед дилера.
     *
     * @return победы дилера
     */
    public int getDealerWins() {
        return dealerWins;
    }
}