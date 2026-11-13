package ru.nsu.vkuznetsov.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.vkuznetsov.task131.finder.SubstringFinder;

/**
 * Тесты для проверки работы SubstringFinder.
 */
class SubstringFinderTest {

    /**
     * Проверка базового поиска в строке через Reader.
     */
    @Test
    void testFindInReader() throws IOException {
        String text = "абракадабра";
        StringReader reader = new StringReader(text);

        List<Long> result = SubstringFinder.find(reader, "бра");

        assertEquals(List.of(1L, 8L), result);
    }

    /**
     * Проверка перекрывающихся вхождений.
     */
    @Test
    void testOverlappingMatches() throws IOException {
        String text = "аааа";
        StringReader reader = new StringReader(text);

        List<Long> result = SubstringFinder.find(reader, "аа");

        assertEquals(List.of(0L, 1L, 2L), result);
    }

    /**
     * Проверка, если подстрока не найдена.
     */
    @Test
    void testNoMatches() throws IOException {
        StringReader reader = new StringReader("Привет, мир!");

        List<Long> result = SubstringFinder.find(reader, "кот");

        assertTrue(result.isEmpty());
    }

    /**
     * Проверка пустой искомой подстроки.
     */
    @Test
    void testEmptyTarget() throws IOException {
        StringReader reader = new StringReader("Любой текст");

        List<Long> result = SubstringFinder.find(reader, "");

        assertTrue(result.isEmpty());
    }

    /**
     * Проверка поиска в файле.
     */
    @Test
    void testFindInFile(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("test.txt");
        String text = "абракадабра\nаааа\nПривет, мир!";
        Files.writeString(file, text);

        List<Long> result = SubstringFinder.find(file.toString(), "мир");
        assertEquals(1, result.size());

        List<Long> result2 = SubstringFinder.find(file.toString(), "бра");
        assertEquals(2, result2.size());
    }

    /**
     * Проверка исключений при некорректных аргументах.
     */
    @Test
    void testExceptions() {
        assertThrows(NullPointerException.class, () -> SubstringFinder.find((java.io.Reader) null, "текст"));
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find(new StringReader(""), null));
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find((String) null, "текст"));
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find("", "текст"));
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find("   ", "текст"));
    }
}