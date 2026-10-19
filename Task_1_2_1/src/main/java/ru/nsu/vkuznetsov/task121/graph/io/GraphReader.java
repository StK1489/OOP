package ru.nsu.vkuznetsov.task121.graph.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import ru.nsu.vkuznetsov.task121.graph.api.Graph;

/**
 * Чтение графа из потока данных в формате «список рёбер»:
 * каждая строка — две вершины {@code from to}.
 * Пустые строки и строки, начинающиеся с {@code #}, игнорируются.
 */
public final class GraphReader {

    private GraphReader() {
    }

    /**
     * Читает граф из входного потока.
     *
     * @param inputStream  поток данных
     * @param directed     {@code true}, если граф ориентированный
     * @param vertexParser функция преобразования строки в вершину
     * @param graphFactory фабрика для создания пустого графа
     * @param <V>          тип вершины
     * @return граф
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public static <V> Graph<V> read(
            InputStream inputStream,
            boolean directed,
            Function<String, V> vertexParser,
            Function<Boolean, Graph<V>> graphFactory
    ) throws IOException {
        Graph<V> graph = graphFactory.apply(directed);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] tokens = line.split("\\s+");
                if (tokens.length != 2) {
                    throw new IOException(
                            "Line " + lineNumber + ": expected 2 vertices, found " + tokens.length);
                }
                V from = vertexParser.apply(tokens[0]);
                V to = vertexParser.apply(tokens[1]);
                graph.addVertex(from);
                graph.addVertex(to);
                graph.addEdge(from, to);
            }
        }
        return graph;
    }
}