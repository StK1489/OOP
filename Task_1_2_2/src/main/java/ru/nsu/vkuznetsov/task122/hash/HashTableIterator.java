package ru.nsu.vkuznetsov.task122.hash;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для обхода элементов HashTable.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashTableIterator<K, V> implements Iterator<Entry<K, V>> {
    private final HashTable<K, V> table;
    private final int expectedModCount;
    private int currentIndex;
    private Entry<K, V> nextEntry;

    /**
     * Создает итератор для заданной таблицы.
     *
     * @param table хеш-таблица для обхода
     */
    public HashTableIterator(HashTable<K, V> table) {
        this.table = table;
        this.expectedModCount = table.getModCount();
        this.currentIndex = 0;
        this.nextEntry = null;
        advance();
    }

    private void advance() {
        if (nextEntry != null && nextEntry.getNext() != null) {
            nextEntry = nextEntry.getNext();
            return;
        }
        while (currentIndex < table.getCapacity()) {
            nextEntry = table.getBucket(currentIndex);
            currentIndex++;
            if (nextEntry != null) {
                return;
            }
        }
        nextEntry = null;
    }

    private void checkModification() {
        if (table.getModCount() != expectedModCount) {
            throw new ConcurrentModificationException("Table modified during iteration");
        }
    }

    /**
     * Проверяет наличие следующего элемента.
     *
     * @return {@code true}, если есть следующий элемент
     */
    @Override
    public boolean hasNext() {
        checkModification();
        return nextEntry != null;
    }

    /**
     * Возвращает следующий элемент в обходе.
     *
     * @return следующий узел
     */
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