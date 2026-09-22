package ru.nsu.vkuznetsov.task_1_1_2.game;

import ru.nsu.vkuznetsov.task_1_1_2.domain.Deck;
import ru.nsu.vkuznetsov.task_1_1_2.domain.Dealer;
import ru.nsu.vkuznetsov.task_1_1_2.domain.Player;
import ru.nsu.vkuznetsov.task_1_1_2.domain.RoundResult;

/**
 * Игра в блэкджек: цикл раундов и счёт.
 */
public class Game {
    private final int deckCount;
    private final Player player;
    private final Dealer dealer;
    private final GameView view;
    private final Score score;
    private final RoundResolver resolver;

    private int roundNumber;

    /**
     * Создаёт игру.
     *
     * @param deckCount количество колод
     * @param player игрок
     * @param dealer дилер
     * @param view интерфейс вывода
     */
    public Game(int deckCount, Player player, Dealer dealer, GameView view) {
        if (deckCount <= 0) {
            throw new IllegalArgumentException("Количество колод должно быть положительным");
        }
        this.deckCount = deckCount;
        this.player = player;
        this.dealer = dealer;
        this.view = view;
        this.score = new Score();
        this.resolver = new RoundResolver();
        this.roundNumber = 0;
    }

    /**
     * Запускает игру.
     */
    public void play() {
        view.showMessage("Добро пожаловать в Блэкджек!");
        while (wantToPlay()) {
            playRound();
        }
    }

    /**
     * Играет один раунд.
     */
    private void playRound() {
        roundNumber++;
        view.showMessage("Раунд " + roundNumber);

        player.resetHand();
        dealer.resetHand();

        Deck deck = new Deck(deckCount);
        deck.shuffle();
        Round round = new Round(deck, player, dealer, view, resolver);
        RoundResult result = round.play();
        score.update(result);
        view.showRoundResult(result, score.getPlayerWins(), score.getDealerWins());
    }

    /**
     * Спрашивает, продолжать ли игру.
     *
     * @return true, если игрок хочет продолжить
     */
    private boolean wantToPlay() {
        return view.askPlayAgain();
    }
}