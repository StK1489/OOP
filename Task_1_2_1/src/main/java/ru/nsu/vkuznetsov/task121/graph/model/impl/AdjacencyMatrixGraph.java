package ru.nsu.vkuznetsov.task121.graph.model.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ru.nsu.vkuznetsov.task121.graph.model.AbstractGraph;
import ru.nsu.vkuznetsov.task121.graph.model.Edge;

/**
 * Граф в виде матрицы смежности.
 *
 * <p>Хранит для каждой вершины отображение «сосед → наличие ребра».
 * Логически эквивалентно квадратной матрице N×N,
 * но реализовано через {@link Map}, чтобы работать с любыми вершинами,
 * а не только с числовыми индексами.
 *
 * @param <V> тип вершины
 */
public class AdjacencyMatrixGraph<V> extends AbstractGraph<V> {

    private final Map<V, Map<V, Boolean>> matrix = new HashMap<>();
    private final Set<Edge<V>> edgeSet = new HashSet<>();

    /**
     * Создаёт граф в виде матрицы смежности.
     *
     * @param directed {@code true}, если граф ориентированный
     */
    public AdjacencyMatrixGraph(boolean directed) {
        super(directed);
    }

    @Override
    public void addVertex(V vertex) {
        matrix.computeIfAbsent(vertex, k -> new HashMap<>());
    }

    @Override
    public void removeVertex(V vertex) {
        if (!hasVertex(vertex)) {
            return;
        }
        for (V neighbor : new HashSet<>(matrix.get(vertex).keySet())) {
            matrix.get(neighbor).remove(vertex);
            edgeSet.remove(new Edge<>(vertex, neighbor));
            edgeSet.remove(new Edge<>(neighbor, vertex));
        }
        for (Map.Entry<V, Map<V, Boolean>> entry : matrix.entrySet()) {
            if (entry.getValue().remove(vertex) != null) {
                edgeSet.remove(new Edge<>(entry.getKey(), vertex));
                edgeSet.remove(new Edge<>(vertex, entry.getKey()));
            }
        }
        matrix.remove(vertex);
    }

    @Override
    public boolean hasVertex(V vertex) {
        return matrix.containsKey(vertex);
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(matrix.keySet());
    }

    @Override
    public int vertexCount() {
        return matrix.size();
    }

    @Override
    public void addEdge(V from, V to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            throw new IllegalArgumentException(
                    "Вершины " + from + " или " + to + " отсутствуют в графе");
        }
        if (hasEdge(from, to)) {
            return;
        }
        matrix.get(from).put(to, Boolean.TRUE);
        edgeSet.add(new Edge<>(from, to));
        if (!isDirected()) {
            matrix.get(to).put(from, Boolean.TRUE);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return;
        }
        if (!hasEdge(from, to)) {
            return;
        }
        matrix.get(from).remove(to);
        edgeSet.remove(new Edge<>(from, to));
        if (!isDirected()) {
            matrix.get(to).remove(from);
            edgeSet.remove(new Edge<>(to, from));
        }
    }

    @Override
    public boolean hasEdge(V from, V to) {
        Map<V, Boolean> row = matrix.get(from);
        if (row == null) {
            return false;
        }
        return row.getOrDefault(to, false);
    }

    @Override
    public Set<Edge<V>> edges() {
        return Collections.unmodifiableSet(edgeSet);
    }

    @Override
    public int edgeCount() {
        return edgeSet.size();
    }

    @Override
    public Set<V> neighbors(V vertex) {
        if (!hasVertex(vertex)) {
            return Collections.emptySet();
        }
        Set<V> result = new HashSet<>();
        for (Map.Entry<V, Boolean> entry : matrix.get(vertex).entrySet()) {
            if (entry.getValue()) {
                result.add(entry.getKey());
            }
        }
        if (!isDirected()) {
            for (Map.Entry<V, Map<V, Boolean>> row : matrix.entrySet()) {
                if (row.getValue().getOrDefault(vertex, false)) {
                    result.add(row.getKey());
                }
            }
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public int inDegree(V vertex) {
        if (!hasVertex(vertex)) {
            return 0;
        }
        if (isDirected()) {
            int count = 0;
            for (Map<V, Boolean> row : matrix.values()) {
                if (row.getOrDefault(vertex, false)) {
                    count++;
                }
            }
            return count;
        }
        return neighbors(vertex).size();
    }

    @Override
    public int outDegree(V vertex) {
        return neighbors(vertex).size();
    }

    @Override
    public void clear() {
        matrix.clear();
        edgeSet.clear();
    }
}