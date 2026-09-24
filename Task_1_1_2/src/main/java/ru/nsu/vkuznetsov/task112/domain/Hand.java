package ru.nsu.vkuznetsov.task112.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Рука игрока или дилера — набор карт.
 */
public class Hand {
    private static final int BLACKJACK_VALUE = 21;
    private static final int ACE_REDUCTION = 10;
    private static final int BLACKJACK_CARD_COUNT = 2;

    private final List<Card> cards;

    /**
     * Создаёт пустую руку.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card карта
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Возвращает копию списка карт в руке.
     *
     * @return список карт
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Возвращает сумму очков руки с учётом тузов.
     * Туз считается за 11, пока сумма не превышает 21.
     *
     * @return сумма очков
     */
    public int getScore() {
        int score = 0;
        int aces = 0;
        for (Card card : cards) {
            score += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }
        while (score > BLACKJACK_VALUE && aces > 0) {
            score -= ACE_REDUCTION;
            aces--;
        }
        return score;
    }

    /**
     * Проверяет, превышает ли сумма очков 21.
     *
     * @return true, если перебор
     */
    public boolean isBusted() {
        return getScore() > BLACKJACK_VALUE;
    }

    /**
     * Проверяет, является ли рука блэкджеком (две карты и 21).
     *
     * @return true, если блэкджек
     */
    public boolean hasBlackjack() {
        return cards.size() == BLACKJACK_CARD_COUNT && getScore() == BLACKJACK_VALUE;
    }

    /**
     * Очищает руку.
     */
    public void reset() {
        cards.clear();
    }
}