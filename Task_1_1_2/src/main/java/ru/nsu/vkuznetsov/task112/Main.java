package ru.nsu.vkuznetsov.task112;

import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Player;
import ru.nsu.vkuznetsov.task112.game.Game;
import ru.nsu.vkuznetsov.task112.ui.ConsolePrinter;
import ru.nsu.vkuznetsov.task112.ui.ConsoleReader;
import ru.nsu.vkuznetsov.task112.ui.ConsoleView;

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