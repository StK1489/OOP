package ru.nsu.vkuznetsov.task122.hash;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для обхода элементов HashTable.
 */
public class HashTableIterator<K, V> implements Iterator<Entry<K, V>> {
    private final HashTable<K, V> table;
    private final int expectedModCount;
    private int currentIndex;
    private Entry<K, V> nextEntry;

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
            if (nextEntry != null) return;
        }
        nextEntry = null;
    }

    private void checkModification() {
        if (table.getModCount() != expectedModCount) {
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