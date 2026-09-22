package ru.nsu.vkuznetsov.task_1_1_2;

import ru.nsu.vkuznetsov.task_1_1_2.domain.Dealer;
import ru.nsu.vkuznetsov.task_1_1_2.domain.Player;
import ru.nsu.vkuznetsov.task_1_1_2.game.Game;
import ru.nsu.vkuznetsov.task_1_1_2.ui.ConsolePrinter;
import ru.nsu.vkuznetsov.task_1_1_2.ui.ConsoleReader;
import ru.nsu.vkuznetsov.task_1_1_2.ui.ConsoleView;

/**
 * Точка входа в игру Блэкджек.
 */
public class Main {

    /**
     * Запускает игру.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        ConsolePrinter printer = new ConsolePrinter();
        ConsoleReader reader = new ConsoleReader();
        ConsoleView view = new ConsoleView(printer, reader);

        int deckCount = view.askDeckCount();

        Player player = new Player(view);
        Dealer dealer = new Dealer();

        Game game = new Game(deckCount, player, dealer, view);
        game.play();
    }
}