package ru.nsu.vkuznetsov.task122.hash;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Хеш-таблица на основе массива и цепочек.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int modCount;

    /**
     * Создаёт пустую хеш-таблицу.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        table = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        size = 0;
        modCount = 0;
    }

    private int getIndex(K key, int capacity) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return (key.hashCode() & 0x7FFFFFFF) % capacity;
    }

    /**
     * Добавляет пару ключ-значение или заменяет значение существующего ключа.
     *
     * @param key ключ
     * @param value значение
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public void put(K key, V value) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }
        Entry<K, V> newEntry = new Entry<>(key, value, table[index]);
        table[index] = newEntry;
        size++;
        modCount++;
        if (size > table.length * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Обновляет значение по существующему ключу.
     *
     * @param key ключ
     * @param value новое значение
     * @throws IllegalArgumentException если ключ равен {@code null} или отсутствует
     */
    public void update(K key, V value) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }
        throw new IllegalArgumentException("Key not found for update: " + key);
    }

    /**
     * Возвращает значение по ключу.
     *
     * @param key ключ
     * @return значение или {@code null}, если ключ отсутствует
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public V get(K key) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Удаляет пару ключ-значение по ключу.
     *
     * @param key ключ
     * @return удалённое значение или {@code null}, если ключ отсутствует
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public V remove(K key) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    table[index] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
                modCount++;
                return current.getValue();
            }
            prev = current;
            current = current.getNext();
        }
        return null;
    }

    /**
     * Проверяет наличие ключа в таблице.
     *
     * @param key ключ
     * @return {@code true}, если ключ найден
     * @throws IllegalArgumentException если ключ равен {@code null}
     */
    public boolean containsKey(K key) {
        int index = getIndex(key, table.length);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[oldTable.length * 2];
        size = 0;
        modCount++;
        for (Entry<K, V> head : oldTable) {
            Entry<K, V> current = head;
            while (current != null) {
                final Entry<K, V> next = current.getNext();
                int newIndex = getIndex(current.getKey(), table.length);
                current.setNext(table[newIndex]);
                table[newIndex] = current;
                size++;
                current = next;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        HashTable<K, V> other = (HashTable<K, V>) obj;

        if (this.size != other.size) {
            return false;
        }

        try {
            for (Entry<K, V> entry : this) {
                Object otherVal = other.get(entry.getKey());
                if (otherVal == null && !other.containsKey(entry.getKey())) {
                    return false;
                }
                if (!Objects.equals(entry.getValue(), otherVal)) {
                    return false;
                }
            }
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Entry<K, V> entry : this) {
            hash += (entry.getKey() == null ? 0 : entry.getKey().hashCode())
                    ^ (entry.getValue() == null ? 0 : entry.getValue().hashCode());
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Entry<K, V> entry : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Узел хеш-таблицы, хранящий пару "ключ-значение".
     *
     * @param <K> тип ключа
     * @param <V> тип значения
     */
    public static class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        /**
         * Создаёт узел хеш-таблицы.
         *
         * @param key ключ
         * @param value значение
         * @param next следующий узел цепочки
         */
        private Entry(K key, V value, Entry<K, V> next) {
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

        private Entry<K, V> getNext() {
            return next;
        }

        private void setNext(Entry<K, V> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Итератор для обхода элементов HashTable.
     */
    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private final int expectedModCount;
        private int currentIndex;
        private Entry<K, V> nextEntry;

        /**
         * Создаёт итератор хеш-таблицы.
         */
        private HashTableIterator() {
            this.expectedModCount = modCount;
            this.currentIndex = 0;
            this.nextEntry = null;
            advance();
        }

        private void advance() {
            if (nextEntry != null && nextEntry.getNext() != null) {
                nextEntry = nextEntry.getNext();
                return;
            }
            while (currentIndex < table.length) {
                nextEntry = table[currentIndex];
                currentIndex++;
                if (nextEntry != null) {
                    return;
                }
            }
            nextEntry = null;
        }

        private void checkModification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("Table modified during iteration");
            }
        }

        @Override
        public boolean hasNext() {
            checkModification();
            return nextEntry != null;
        }

        @Override
        public Entry<K, V> next() {
            checkModification();
            if (nextEntry == null) {
                throw new NoSuchElementException();
            }
            Entry<K, V> result = nextEntry;
            advance();
            return result;
        }
    }
}