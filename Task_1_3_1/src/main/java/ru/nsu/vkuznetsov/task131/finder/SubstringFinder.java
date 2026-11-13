package ru.nsu.vkuznetsov.task131.finder;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Поиск подстроки в потоке символов или текстовом файле UTF-8.
 */
public final class SubstringFinder {

    private static final int CHUNK_SIZE = 8192; // Размер буфера: 8 КБ

    private SubstringFinder() {
    }

    /**
     * Ищет все вхождения подстроки с помощью скользящего окна.
     * Обрабатывает поток чанками и учитывает перекрывающиеся вхождения.
     * Токеном является кодовая точка Unicode. Индексы токенов начинаются с нуля.
     * Переданный поток остаётся открытым. Пустая подстрока даёт пустой список.
     *
     * @param reader поток символов
     * @param target искомая подстрока
     * @return список индексов начала вхождений
     * @throws NullPointerException если поток равен null
     * @throws IllegalArgumentException если подстрока равна null
     * @throws IOException если произошла ошибка чтения
     */
    public static List<Long> find(Reader reader, String target) throws IOException {
        if (reader == null) {
            throw new NullPointerException("Reader cannot be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null");
        }
        if (target.isEmpty()) {
            return List.of();
        }

        List<Integer> pattern = target.codePoints().boxed().toList();
        List<Integer> window = new LinkedList<>();
        Reader source = new java.io.BufferedReader(reader);
        List<Integer> tokens = new ArrayList<>(CHUNK_SIZE);
        List<Long> result = new ArrayList<>();
        long position = 0;

        while (readChunk(source, tokens)) {
            for (int token : tokens) {
                window.add(token);
                if (window.size() > pattern.size()) {
                    window.remove(0);
                }
                position++;
                if (window.size() == pattern.size() && window.equals(pattern)) {
                    result.add(position - pattern.size());
                }
            }
        }
        return result;
    }

    /**
     * Открывает файл в UTF-8, выполняет поиск и закрывает файл.
     *
     * @param filename имя файла
     * @param target искомая подстрока
     * @return список индексов начала вхождений
     * @throws IllegalArgumentException если имя файла пустое или один из аргументов равен null
     * @throws IOException если файл нельзя открыть, прочитать или декодировать
     */
    public static List<Long> find(String filename, String target) throws IOException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        try (Reader reader = java.nio.file.Files.newBufferedReader(
                java.nio.file.Path.of(filename))) {
            return find(reader, target);
        }
    }

    private static boolean readChunk(Reader reader, List<Integer> tokens)
            throws IOException {
        tokens.clear();
        while (tokens.size() < CHUNK_SIZE) {
            int token = readToken(reader);
            if (token == -1) {
                break;
            }
            tokens.add(token);
        }
        return !tokens.isEmpty();
    }

    private static int readToken(Reader reader) throws IOException {
        int first = reader.read();
        if (first == -1 || !Character.isHighSurrogate((char) first)) {
            return first;
        }
        reader.mark(1);
        int second = reader.read();
        if (second != -1 && Character.isLowSurrogate((char) second)) {
            return Character.toCodePoint((char) first, (char) second);
        }
        reader.reset();
        return first;
    }
}
