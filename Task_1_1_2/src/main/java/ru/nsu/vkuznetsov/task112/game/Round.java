package ru.nsu.vkuznetsov.task112.game;

import ru.nsu.vkuznetsov.task112.domain.Card;
import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Deck;
import ru.nsu.vkuznetsov.task112.domain.Player;
import ru.nsu.vkuznetsov.task112.domain.RoundResult;

/**
 * Один раунд игры в блэкджек.
 */
public class Round {

    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final GameView view;
    private final RoundResolver resolver;

    /**
     * Создаёт раунд.
     *
     * @param deck колода
     * @param player игрок
     * @param dealer дилер
     * @param view интерфейс вывода
     * @param resolver определитель победителя
     */
    public Round(Deck deck, Player player, Dealer dealer, GameView view, RoundResolver resolver) {
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
        this.view = view;
        this.resolver = resolver;
    }

    /**
     * Играет раунд и возвращает результат.
     *
     * @return результат раунда
     */
    public RoundResult play() {
        dealCards();
        showHands();

        RoundResult blackjackResult = checkBlackjacks();
        if (blackjackResult != null) {
            return blackjackResult;
        }

        playerTurn();
        dealerTurn();

        return resolver.resolve(player, dealer);
    }

    /**
     * Раздаёт по две карты игроку и дилеру.
     */
    private void dealCards() {
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());
        player.addCard(deck.draw());
        dealer.addCard(deck.draw());
    }

    /**
     * Показывает руки игрока и дилера.
     */
    private void showHands() {
        view.showPlayerHand(player.getHand());
        view.showDealerHand(dealer);
    }

    /**
     * Проверяет блэкджеки у игрока и дилера.
     *
     * @return результат, если блэкджек есть, иначе null
     */
    private RoundResult checkBlackjacks() {
        if (player.hasBlackjack() && dealer.hasBlackjack()) {
            return RoundResult.DRAW;
        }
        if (player.hasBlackjack()) {
            return RoundResult.PLAYER_WIN;
        }
        if (dealer.hasBlackjack()) {
            return RoundResult.DEALER_WIN;
        }
        return null;
    }

    /**
     * Ход игрока: игрок берёт карты, пока хочет.
     */
    private void playerTurn() {
        while (player.shouldHit()) {
            Card card = deck.draw();
            player.addCard(card);
            view.showMessage("Вы открыли карту " + card);
            view.showPlayerHand(player.getHand());
            if (player.isBusted()) {
                break;
            }
        }
    }

    /**
     * Ход дилера: открывает закрытую карту и берёт, пока меньше 17.
     * Если игрок перебрал — дилер только открывает карту.
     */
    private void dealerTurn() {
        view.showMessage("Ход дилера");
        Card holeCard = dealer.getHoleCard();
        dealer.revealHoleCard();
        view.showMessage("Дилер открывает закрытую карту " + holeCard);
        view.showPlayerHand(player.getHand());
        view.showDealerHand(dealer);

        if (player.isBusted()) {
            return;
        }

        while (dealer.shouldHit()) {
            Card card = deck.draw();
            dealer.addCard(card);
            view.showMessage("Дилер открывает карту " + card);
            view.showPlayerHand(player.getHand());
            view.showDealerHand(dealer);
            if (dealer.isBusted()) {
                break;
            }
        }
    }
}