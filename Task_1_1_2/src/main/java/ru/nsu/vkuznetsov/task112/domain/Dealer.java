package ru.nsu.vkuznetsov.task112.domain;

/**
 * Дилер — участник со стратегией «брать, пока меньше 17».
 * Имеет закрытую карту до конца хода игрока.
 */
public class Dealer extends Participant {
    private static final int DEALER_STANDS_AT = 17;
    private static final int HOLE_CARD_INDEX = 1;

    private boolean holeCardRevealed;

    /**
     * Создаёт дилера с закрытой картой.
     */
    public Dealer() {
        holeCardRevealed = false;
    }

    /**
     * Возвращает закрытую карту дилера (вторую в руке).
     *
     * @return закрытая карта
     */
    public Card getHoleCard() {
        return hand.getCards().get(HOLE_CARD_INDEX);
    }

    /**
     * Открывает закрытую карту.
     */
    public void revealHoleCard() {
        holeCardRevealed = true;
    }

    /**
     * Проверяет, есть ли скрытая карта.
     *
     * @return true, если карта скрыта
     */
    public boolean hasHiddenCard() {
        return !holeCardRevealed;
    }

    /**
     * Дилер берёт карту, пока сумма меньше 17.
     *
     * @return true, если дилер должен взять карту
     */
    @Override
    public boolean shouldHit() {
        return getScore() < DEALER_STANDS_AT;
    }

    /**
     * Сбрасывает руку и закрывает карту для нового раунда.
     */
    @Override
    public void resetHand() {
        super.resetHand();
        holeCardRevealed = false;
    }
}