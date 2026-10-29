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

    public Entry(K key, V value, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry<K, V> getNext() {
        return next;
    }

    public void setNext(Entry<K, V> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}