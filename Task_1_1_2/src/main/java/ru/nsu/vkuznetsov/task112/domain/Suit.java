package ru.nsu.vkuznetsov.task112.domain;

/**
 * Масть игральной карты.
 * Хранит формы прилагательного (мужскую и женскую) и существительное
 * для склонения в зависимости от рода достоинства.
 */
public enum Suit {
    SPADES("Пиковый", "Пиковая", "Пики"),
    HEARTS("Червовый", "Червовая", "Червы"),
    CLUBS("Трефовый", "Трефовая", "Трефы"),
    DIAMONDS("Бубновый", "Бубновая", "Бубны");

    private final String masculine;
    private final String feminine;
    private final String noun;

    Suit(String masculine, String feminine, String noun) {
        this.masculine = masculine;
        this.feminine = feminine;
        this.noun = noun;
    }

    /**
     * Возвращает прилагательное масти в нужном роде.
     *
     * @param gender род достоинства карты
     * @return прилагательное в соответствующем роде
     */
    public String getAdjective(Gender gender) {
        if (gender == Gender.MASCULINE) {
            return masculine;
        }
        return feminine;
    }

    /**
     * Возвращает существительное масти.
     *
     * @return название масти существительным
     */
    public String getNoun() {
        return noun;
    }
}