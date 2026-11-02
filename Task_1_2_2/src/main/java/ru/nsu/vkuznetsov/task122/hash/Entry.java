package ru.nsu.vkuznetsov.task122.hash;

/**
 * Узел хеш-таблицы, хранящий пару "ключ-значение".
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class Entry<K, V> {
    private final K key;
    private V value;
    private Entry<K, V> next;

    /**
     * Создает новый узел.
     *
     * @param key   ключ
     * @param value значение
     * @param next  ссылка на следующий узел
     */
    public Entry(K key, V value, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    /**
     * Возвращает ключ.
     *
     * @return ключ
     */
    public K getKey() {
        return key;
    }

    /**
     * Возвращает значение.
     *
     * @return значение
     */
    public V getValue() {
        return value;
    }

    /**
     * Устанавливает новое значение.
     *
     * @param value новое значение
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Возвращает следующий узел в цепочке.
     *
     * @return следующий узел
     */
    public Entry<K, V> getNext() {
        return next;
    }

    /**
     * Устанавливает следующий узел.
     *
     * @param next следующий узел
     */
    public void setNext(Entry<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}