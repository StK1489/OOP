package ru.nsu.vkuznetsov.task112.ui;

import java.util.List;
import ru.nsu.vkuznetsov.task112.domain.Card;
import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Hand;
import ru.nsu.vkuznetsov.task112.domain.RoundResult;

/**
 * Печатает состояние игры в консоль.
 */
public class ConsolePrinter {

    /**
     * Печатает руку игрока.
     *
     * @param hand рука игрока
     */
    public void printPlayerHand(Hand hand) {
        System.out.println("Ваши карты: " + hand.getCards() + " > " + hand.getScore());
    }

    /**
     * Печатает руку дилера.
     * Если карта скрыта — вторая карта заменяется на "&lt;закрытая карта", сумма не печатается.
     *
     * @param dealer дилер
     */
    public void printDealerHand(Dealer dealer) {
        if (dealer.hasHiddenCard()) {
            List<Card> cards = dealer.getHand().getCards();
            System.out.println("Карты дилера: [" + cards.get(0) + ", <закрытая карта ]");
        } else {
            System.out.println("Карты дилера: " + dealer.getHand().getCards()
                    + " > " + dealer.getScore());
        }
    }

    /**
     * Печатает сообщение.
     *
     * @param message сообщение
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Печатает результат раунда и счёт.
     *
     * @param result результат раунда
     * @param playerWins победы игрока
     * @param dealerWins победы дилера
     */
    public void printRoundResult(RoundResult result, int playerWins, int dealerWins) {
        switch (result) {
            case PLAYER_WIN -> System.out.println(
                    "Вы выиграли раунд! Счет "
                            + playerWins + ":" + dealerWins
                            + " в вашу пользу.");
            case DEALER_WIN -> System.out.println(
                    "Вы проиграли раунд. Счет "
                            + playerWins + ":" + dealerWins
                            + " в пользу дилера.");
            case DRAW -> System.out.println(
                    "Ничья. Счет " + playerWins + ":" + dealerWins + ".");
            default -> throw new IllegalStateException("Unknown result: " + result);
        }
    }
}