package ru.nsu.vkuznetsov.task_1_1_2.domain;

/**
 * Участник игры (игрок или дилер).
 * Содержит руку и общие действия над ней.
 */
public abstract class Participant {
    protected final Hand hand;

    /**
     * Создаёт участника с пустой рукой.
     */
    public Participant() {
        this.hand = new Hand();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card карта
     */
    public void addCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Возвращает сумму очков руки.
     *
     * @return сумма очков
     */
    public int getScore() {
        return hand.getScore();
    }

    /**
     * Проверяет перебор.
     *
     * @return true, если перебор
     */
    public boolean isBusted() {
        return hand.isBusted();
    }

    /**
     * Проверяет блэкджек.
     *
     * @return true, если блэкджек
     */
    public boolean hasBlackjack() {
        return hand.hasBlackjack();
    }

    /**
     * Возвращает руку участника.
     *
     * @return рука
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Очищает руку для нового раунда.
     */
    public void resetHand() {
        hand.reset();
    }

    /**
     * Решает, брать ли ещё карту.
     *
     * @return true, если участник хочет взять карту
     */
    public abstract boolean shouldHit();
}