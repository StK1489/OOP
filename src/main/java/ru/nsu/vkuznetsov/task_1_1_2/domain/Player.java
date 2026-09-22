package ru.nsu.vkuznetsov.task_1_1_2.domain;

/**
 * Игрок — участник, решения которого приходят извне.
 */
public class Player extends Participant {
    private final DecisionProvider provider;

    /**
     * Создаёт игрока с заданным провайдером решений.
     *
     * @param provider провайдер решений
     */
    public Player(DecisionProvider provider) {
        this.provider = provider;
    }

    /**
     * Спрашивает у провайдера, хочет ли игрок взять карту.
     *
     * @return true, если игрок хочет взять карту
     */
    @Override
    public boolean shouldHit() {
        return provider.askHitOrStand();
    }
}