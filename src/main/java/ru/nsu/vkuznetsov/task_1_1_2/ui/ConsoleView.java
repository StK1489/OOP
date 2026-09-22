package ru.nsu.vkuznetsov.task_1_1_2.ui;

import ru.nsu.vkuznetsov.task_1_1_2.domain.DecisionProvider;
import ru.nsu.vkuznetsov.task_1_1_2.domain.Dealer;
import ru.nsu.vkuznetsov.task_1_1_2.domain.Hand;
import ru.nsu.vkuznetsov.task_1_1_2.domain.RoundResult;
import ru.nsu.vkuznetsov.task_1_1_2.game.GameView;

/**
 * Консольный интерфейс: фасад над ConsolePrinter и ConsoleReader.
 * Реализует GameView и DecisionProvider.
 */
public class ConsoleView implements GameView, DecisionProvider {
    private final ConsolePrinter printer;
    private final ConsoleReader reader;

    /**
     * Создаёт консольный интерфейс.
     *
     * @param printer принтер
     * @param reader читатель
     */
    public ConsoleView(ConsolePrinter printer, ConsoleReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    /**
     * Спрашивает количество колод у игрока.
     *
     * @return количество колод
     */
    public int askDeckCount() {
        return reader.readDeckCount();
    }

    @Override
    public void showPlayerHand(Hand hand) {
        printer.printPlayerHand(hand);
    }

    @Override
    public void showDealerHand(Dealer dealer) {
        printer.printDealerHand(dealer);
    }

    @Override
    public void showRoundResult(RoundResult result, int playerWins, int dealerWins) {
        printer.printRoundResult(result, playerWins, dealerWins);
    }

    @Override
    public void showMessage(String message) {
        printer.printMessage(message);
    }

    @Override
    public boolean askPlayAgain() {
        return reader.readPlayAgain();
    }

    @Override
    public boolean askHitOrStand() {
        return reader.readHitOrStand();
    }
}