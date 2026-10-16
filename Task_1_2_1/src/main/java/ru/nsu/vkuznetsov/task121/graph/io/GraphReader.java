package ru.nsu.vkuznetsov.task121.graph.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import ru.nsu.vkuznetsov.task121.graph.Graph;

/**
 * Чтение графа из файла формата «список рёбер»:
 * каждая строка — две вершины {@code from to}.
 * Пустые строки и строки, начинающиеся с {@code #}, игнорируются.
 */
public final class GraphReader {

    /**
     * Приватный конструктор: класс утилитный, создавать объекты не нужно.
     */
    private GraphReader() {
    }

    /**
     * Читает граф из файла.
     *
     * @param path         путь к файлу
     * @param directed     {@code true}, если граф ориентированный
     * @param vertexParser функция преобразования строки в вершину
     * @param graphFactory фабрика: {@code directed -> пустой граф}
     * @param <V>          тип вершины
     * @return граф
     * @throws IOException если файл не удалось прочитать
     */
    public static <V> Graph<V> read(
            Path path,
            boolean directed,
            Function<String, V> vertexParser,
            Function<Boolean, Graph<V>> graphFactory
    ) throws IOException {
        Graph<V> graph = graphFactory.apply(directed);
        List<String> lines = Files.readAllLines(path);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            String[] tokens = line.split("\\s+");
            if (tokens.length != 2) {
                throw new IOException(
                        "Строка " + (i + 1) + ": ожидалось 2 вершины, найдено " + tokens.length);
            }
            V from = vertexParser.apply(tokens[0]);
            V to = vertexParser.apply(tokens[1]);
            graph.addVertex(from);
            graph.addVertex(to);
            graph.addEdge(from, to);
        }
        return graph;
    }
}