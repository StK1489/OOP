package ru.nsu.vkuznetsov.task121.graph.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ru.nsu.vkuznetsov.task121.graph.AbstractGraph;
import ru.nsu.vkuznetsov.task121.graph.Edge;

/**
 * Граф в виде матрицы инцидентности.
 *
 * <p>Строки матрицы соответствуют вершинам, столбцы - рёбрам.
 * В ячейке хранится коэффициент инцидентности:
 * {@code +1}, если вершина - начало ребра,
 * {@code -1}, если вершина - конец (для ориентированного графа),
 * {@code 0} - если вершина не инцидентна ребру.
 *
 * <p>Для неориентированного графа обе концевые вершины имеют {@code +1}.
 *
 * @param <V> тип вершины
 */
public class IncidenceMatrixGraph<V> extends AbstractGraph<V> {

    private final Map<V, Map<Edge<V>, Integer>> incidence = new HashMap<>();
    private final Set<Edge<V>> edgeSet = new HashSet<>();

    /**
     * @param directed {@code true}, если граф ориентированный
     */
    public IncidenceMatrixGraph(boolean directed) {
        super(directed);
    }

    @Override
    public void addVertex(V vertex) {
        incidence.computeIfAbsent(vertex, k -> new HashMap<>());
    }

    @Override
    public void removeVertex(V vertex) {
        if (!hasVertex(vertex)) {
            return;
        }
        Set<Edge<V>> toRemove = new HashSet<>();
        for (Map.Entry<Edge<V>, Integer> cell : incidence.get(vertex).entrySet()) {
            if (cell.getValue() != 0) {
                toRemove.add(cell.getKey());
            }
        }
        for (Edge<V> edge : toRemove) {
            removeEdge(edge.from(), edge.to());
        }
        incidence.remove(vertex);
    }

    @Override
    public boolean hasVertex(V vertex) {
        return incidence.containsKey(vertex);
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(incidence.keySet());
    }

    @Override
    public int vertexCount() {
        return incidence.size();
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
        Edge<V> edge = new Edge<>(from, to);
        edgeSet.add(edge);
        incidence.get(from).put(edge, 1);
        incidence.get(to).put(edge, isDirected() ? -1 : 1);
    }

    @Override
    public void removeEdge(V from, V to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return;
        }
        if (!hasEdge(from, to)) {
            return;
        }
        Edge<V> edge = new Edge<>(from, to);
        edgeSet.remove(edge);
        for (Map<Edge<V>, Integer> row : incidence.values()) {
            row.remove(edge);
        }
    }

    @Override
    public boolean hasEdge(V from, V to) {
        return incidence.getOrDefault(from, Collections.emptyMap())
                .containsKey(new Edge<>(from, to));
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
        for (Map.Entry<Edge<V>, Integer> cell : incidence.get(vertex).entrySet()) {
            if (cell.getValue() == 0) {
                continue;
            }
            Edge<V> edge = cell.getKey();
            V other = edge.from().equals(vertex) ? edge.to() : edge.from();
            if (other.equals(vertex)) {
                continue;
            }
            if (isDirected()) {
                if (cell.getValue() == 1) {
                    result.add(other);
                }
            } else {
                result.add(other);
            }
        }
        return Collections.unmodifiableSet(result);
    }

    @Override
    public int inDegree(V vertex) {
        if (!hasVertex(vertex)) {
            return 0;
        }
        if (!isDirected()) {
            return neighbors(vertex).size();
        }
        int count = 0;
        for (Map.Entry<Edge<V>, Integer> cell : incidence.get(vertex).entrySet()) {
            if (cell.getValue() == -1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int outDegree(V vertex) {
        if (!hasVertex(vertex)) {
            return 0;
        }
        if (!isDirected()) {
            return neighbors(vertex).size();
        }
        int count = 0;
        for (Map.Entry<Edge<V>, Integer> cell : incidence.get(vertex).entrySet()) {
            if (cell.getValue() == 1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void clear() {
        incidence.clear();
        edgeSet.clear();
    }
}