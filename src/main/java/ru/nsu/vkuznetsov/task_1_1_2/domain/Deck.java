package ru.nsu.vkuznetsov.task_1_1_2.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Колода игральных карт.
 * Создаётся заново на каждый раунд.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Создаёт колоду из указанного количества стандартных колод по 52 карты.
     *
     * @param deckCount количество колод (должно быть положительным)
     * @throws IllegalArgumentException если deckCount меньше или равен нулю
     */
    public Deck(int deckCount) {
        if (deckCount <= 0) {
            throw new IllegalArgumentException("Количество колод должно быть положительным");
        }
        cards = new ArrayList<>();
        for (int i = 0; i < deckCount; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
    }

    /**
     * Перемешивает колоду.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Выдаёт одну карту из колоды и удаляет её из колоды.
     *
     * @return выданная карта
     * @throws IllegalStateException если колода пуста
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Колода пуста");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Возвращает количество оставшихся в колоде карт.
     *
     * @return количество карт
     */
    public int size() {
        return cards.size();
    }
}