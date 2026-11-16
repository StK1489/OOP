package ru.nsu.vkuznetsov.task131.finder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Поиск подстроки в потоке символов или текстовом файле UTF-8.
 */
public final class SubstringFinder {

    private static final int CHUNK_SIZE = 8192; // Размер буфера для чтения - 8 КБ

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
     * @throws NullPointerException     если поток равен null
     * @throws IllegalArgumentException если подстрока равна null
     * @throws IOException              если произошла ошибка чтения
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
        Reader source = new BufferedReader(reader);
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
     * @param target   искомая подстрока
     * @return список индексов начала вхождений
     * @throws IllegalArgumentException если имя файла пустое или один из аргументов равен null
     * @throws IOException              если файл нельзя открыть, прочитать или декодировать
     */
    public static List<Long> find(String filename, String target) throws IOException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        try (Reader reader = Files.newBufferedReader(Path.of(filename))) {
            return find(reader, target);
        }
    }

    /**
     * Читает порцию символов (чанк) из потока в список токенов.
     * Метод читает до {@code CHUNK_SIZE} токенов или до конца потока.
     *
     * @param reader поток символов
     * @param tokens список, в который сохраняются прочитанные токены
     * @return {@code true}, если были прочитаны данные, {@code false} если поток закончился
     * @throws IOException если произошла ошибка чтения
     */
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

    /**
     * Читает один токен (кодовую точку Unicode).
     * Корректно обрабатывает суррогатные пары (например, эмодзи),
     * объединяя два символа в один.
     *
     * @param reader поток символов
     * @return код символа (от 0 до 0x10FFFF) или -1, если достигнут конец потока
     * @throws IOException если произошла ошибка чтения
     */
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