package ru.nsu.vkuznetsov.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task122.hash.HashTable;


class HashTableTest {

    private HashTable<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashTable<>();
    }

    @Test
    void emptyTable() {
        assertEquals(0, map.size());
        assertNull(map.get("missing"));
        assertNull(map.remove("missing"));
        assertFalse(map.containsKey("missing"));
        assertEquals("{}", map.toString());

        Iterator<HashTable.Entry<String, Integer>> iterator = map.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void putAndReplaceValue() {
        map.put("key", 15);
        assertEquals(15, map.get("key"));
        assertTrue(map.containsKey("key"));

        map.put("key", 20);
        assertEquals(20, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void updateExistingAndMissingKeys() {
        map.put("Aa", 1);
        map.put("BB", 2);
        map.update("Aa", 3);

        assertEquals(3, map.get("Aa"));
        assertEquals(2, map.get("BB"));
        assertEquals(2, map.size());
        assertThrows(IllegalArgumentException.class, () -> map.update("missing", 4));
    }

    @Test
    void nullValueIsStoredAndRemoved() {
        map.put("key", null);

        assertNull(map.get("key"));
        assertTrue(map.containsKey("key"));
        assertEquals(1, map.size());
        assertNull(map.remove("key"));
        assertFalse(map.containsKey("key"));
        assertEquals(0, map.size());
    }

    @Test
    void nullKeysAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> map.put(null, 1));
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
        assertThrows(IllegalArgumentException.class, () -> map.update(null, 1));
        assertThrows(IllegalArgumentException.class, () -> map.remove(null));
        assertThrows(IllegalArgumentException.class, () -> map.containsKey(null));
    }

    @Test
    void collisionsAndRemovalFromChain() {
        map.put("AaAa", 1);
        map.put("BBBB", 2);
        map.put("AaBB", 3);

        assertEquals(1, map.get("AaAa"));
        assertEquals(2, map.get("BBBB"));
        assertEquals(3, map.get("AaBB"));
        assertNull(map.get("BBAa"));
        assertFalse(map.containsKey("BBAa"));
        assertNull(map.remove("BBAa"));

        assertEquals(2, map.remove("BBBB"));
        assertEquals(2, map.size());
        assertEquals(1, map.get("AaAa"));
        assertEquals(3, map.get("AaBB"));
        assertEquals(1, map.remove("AaAa"));
        assertEquals(3, map.remove("AaBB"));
        assertEquals(0, map.size());
    }

    @Test
    void resizePreservesEntriesAndMinimumHash() {
        String key = "polygenelubricants";
        map.put(key, 42);
        for (int i = 0; i < 40; i++) {
            map.put(String.valueOf(i), i);
        }

        assertEquals(41, map.size());
        assertEquals(42, map.get(key));
        assertTrue(map.containsKey(key));
        for (int i = 0; i < 40; i++) {
            assertEquals(i, map.get(String.valueOf(i)));
        }
    }

    @Test
    void equalsAndHashCodeDependOnContent() {
        map.put("a", 1);
        map.put("b", 2);

        HashTable<String, Integer> other = new HashTable<>();
        other.put("b", 2);
        other.put("a", 1);

        assertTrue(map.equals(map));
        assertFalse(map.equals(null));
        assertFalse(map.equals("not a table"));
        assertEquals(map, other);
        assertEquals(other, map);
        assertEquals(map.hashCode(), other.hashCode());

        other.update("a", 3);
        assertNotEquals(map, other);
        other.remove("b");
        assertNotEquals(map, other);
    }

    @Test
    void equalsDistinguishesNullAndMissingValues() {
        map.put("a", null);
        HashTable<String, Integer> other = new HashTable<>();
        other.put("a", 1);

        assertNotEquals(map, other);
        assertNotEquals(other, map);

        other.remove("a");
        other.put("b", null);
        assertNotEquals(map, other);

        other.remove("b");
        other.put("a", null);
        assertEquals(map, other);
        assertEquals(map.hashCode(), other.hashCode());
    }

    @Test
    void entryAccessorsAndStringRepresentation() {
        map.put("a", 1);
        HashTable.Entry<String, Integer> entry = map.iterator().next();

        assertEquals("a", entry.getKey());
        assertEquals(1, entry.getValue());
        assertEquals("a=1", entry.toString());

        entry.setValue(2);
        assertEquals(2, map.get("a"));
        map.put("b", 3);

        String text = map.toString();
        assertTrue(text.startsWith("{"));
        assertTrue(text.endsWith("}"));
        assertTrue(text.contains("a=2"));
        assertTrue(text.contains("b=3"));
        assertTrue(text.contains(", "));
    }

    @Test
    void iteratorVisitsEntireLastBucket() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(15, "first");
        table.put(31, "second");
        table.put(47, "third");

        Set<Integer> keys = new HashSet<>();
        Iterator<HashTable.Entry<Integer, String>> iterator = table.iterator();
        while (iterator.hasNext()) {
            HashTable.Entry<Integer, String> entry = iterator.next();
            assertTrue(keys.add(entry.getKey()));
            assertEquals(table.get(entry.getKey()), entry.getValue());
        }

        assertEquals(Set.of(15, 31, 47), keys);
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorDetectsStructuralChanges() {
        map.put("a", 1);
        Iterator<HashTable.Entry<String, Integer>> added = map.iterator();
        map.put("b", 2);

        assertThrows(ConcurrentModificationException.class, added::hasNext);
        assertThrows(ConcurrentModificationException.class, added::next);

        Iterator<HashTable.Entry<String, Integer>> removed = map.iterator();
        map.remove("a");
        assertThrows(ConcurrentModificationException.class, removed::hasNext);
        assertThrows(ConcurrentModificationException.class, removed::next);

        Iterator<HashTable.Entry<String, Integer>> updated = map.iterator();
        map.update("b", 3);
        map.put("b", 4);
        assertEquals(4, updated.next().getValue());
        assertFalse(updated.hasNext());
    }
}