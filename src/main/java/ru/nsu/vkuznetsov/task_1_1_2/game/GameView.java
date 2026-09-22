package ru.nsu.vkuznetsov.task_1_1_2.game;

import ru.nsu.vkuznetsov.task_1_1_2.domain.Dealer;
import ru.nsu.vkuznetsov.task_1_1_2.domain.Hand;
import ru.nsu.vkuznetsov.task_1_1_2.domain.RoundResult;

/**
 * Интерфейс для общения игры с пользовательским интерфейсом.
 */
public interface GameView {

    /**
     * Показывает руку игрока.
     *
     * @param hand рука игрока
     */
    void showPlayerHand(Hand hand);

    /**
     * Показывает руку дилера.
     *
     * @param dealer дилер
     */
    void showDealerHand(Dealer dealer);

    /**
     * Показывает результат раунда и текущий счёт.
     *
     * @param result результат раунда
     * @param playerWins победы игрока
     * @param dealerWins победы дилера
     */
    void showRoundResult(RoundResult result, int playerWins, int dealerWins);

    /**
     * Показывает сообщение.
     *
     * @param message сообщение
     */
    void showMessage(String message);

    /**
     * Спрашивает, хочет ли игрок сыграть ещё раунд.
     *
     * @return true, если игрок хочет продолжить
     */
    boolean askPlayAgain();
}