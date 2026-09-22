package ru.nsu.vkuznetsov.task_1_1_2.domain;

/**
 * Провайдер решений игрока: брать карту или остановиться.
 */
public interface DecisionProvider {
    /**
     * Спрашивает, хочет ли игрок взять карту.
     *
     * @return true, если игрок хочет взять карту
     */
    boolean askHitOrStand();
}