package ru.nsu.vkuznetsov.task_1_1_2.domain;

/**
 * Достоинство игральной карты.
 * Хранит название, значение очков и род для склонения масти.
 */
public enum Rank {
    TWO("Двойка", 2, Gender.FEMININE),
    THREE("Тройка", 3, Gender.FEMININE),
    FOUR("Четверка", 4, Gender.FEMININE),
    FIVE("Пятерка", 5, Gender.FEMININE),
    SIX("Шестерка", 6, Gender.FEMININE),
    SEVEN("Семерка", 7, Gender.FEMININE),
    EIGHT("Восьмерка", 8, Gender.FEMININE),
    NINE("Девятка", 9, Gender.FEMININE),
    TEN("Десятка", 10, Gender.FEMININE),
    JACK("Валет", 10, Gender.MASCULINE),
    QUEEN("Дама", 10, Gender.FEMININE),
    KING("Король", 10, Gender.MASCULINE),
    ACE("Туз", 11, Gender.MASCULINE);

    private final String name;
    private final int highValue;
    private final Gender gender;

    Rank(String name, int highValue, Gender gender) {
        this.name = name;
        this.highValue = highValue;
        this.gender = gender;
    }

    /**
     * Проверяет, является ли достоинство картинкой (валет, дама, король).
     *
     * @return true, если картинка
     */
    public boolean isFaceCard() {
        return this == JACK || this == QUEEN || this == KING;
    }

    /**
     * Возвращает название достоинства.
     *
     * @return название достоинства
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает значение очков.
     *
     * @return значение очков
     */
    public int getValue() {
        return highValue;
    }

    /**
     * Возвращает род достоинства.
     *
     * @return род достоинства
     */
    public Gender getGender() {
        return gender;
    }
}