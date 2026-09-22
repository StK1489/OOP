package ru.nsu.vkuznetsov.task_1_1_2.domain;

/**
 * Игральная карта — комбинация масти и достоинства.
 */
public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        if (rank.isFaceCard()) {
            return suit.getAdjective(rank.getGender())
                    + " "
                    + rank.getName()
                    + " ("
                    + rank.getValue()
                    + ")";
        }
        return rank.getName()
                + " "
                + suit.getNoun()
                + " ("
                + rank.getValue()
                + ")";
    }
}