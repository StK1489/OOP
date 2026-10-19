package ru.nsu.vkuznetsov.task121.graph.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ru.nsu.vkuznetsov.task121.graph.core.AbstractGraph;
import ru.nsu.vkuznetsov.task121.graph.api.Edge;

/**
 * Граф в виде списка смежности.
 *
 * <p>Для каждой вершины хранятся множества исходящих и входящих соседей,
 * а также явный набор рёбер.
 *
 * @param <V> тип вершины
 */
public class AdjacencyListGraph<V> extends AbstractGraph<V> {

    private final Map<V, Set<V>> outgoing = new HashMap<>();
    private final Map<V, Set<V>> incoming = new HashMap<>();
    private final Set<Edge<V>> edgeSet = new HashSet<>();

    /**
     * Создаёт граф в виде списка смежности.
     *
     * @param directed {@code true}, если граф ориентированный
     */
    public AdjacencyListGraph(boolean directed) {
        super(directed);
    }

    @Override
    public void addVertex(V vertex) {
        outgoing.putIfAbsent(vertex, new HashSet<>());
        incoming.putIfAbsent(vertex, new HashSet<>());
    }

    @Override
    public void removeVertex(V vertex) {
        if (!hasVertex(vertex)) {
            return;
        }
        for (V neighbor : outgoing.get(vertex)) {
            incoming.get(neighbor).remove(vertex);
            edgeSet.remove(new Edge<>(vertex, neighbor));
        }
        for (V neighbor : incoming.get(vertex)) {
            outgoing.get(neighbor).remove(vertex);
            edgeSet.remove(new Edge<>(neighbor, vertex));
        }
        outgoing.remove(vertex);
        incoming.remove(vertex);
    }

    @Override
    public boolean hasVertex(V vertex) {
        return outgoing.containsKey(vertex);
    }

    @Override
    public Set<V> vertices() {
        return Collections.unmodifiableSet(outgoing.keySet());
    }

    @Override
    public int vertexCount() {
        return outgoing.size();
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
        outgoing.get(from).add(to);
        incoming.get(to).add(from);
        edgeSet.add(new Edge<>(from, to));
        if (!isDirected()) {
            outgoing.get(to).add(from);
            incoming.get(from).add(to);
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
        outgoing.get(from).remove(to);
        incoming.get(to).remove(from);
        edgeSet.remove(new Edge<>(from, to));
        if (!isDirected()) {
            outgoing.get(to).remove(from);
            incoming.get(from).remove(to);
            edgeSet.remove(new Edge<>(to, from));
        }
    }

    @Override
    public boolean hasEdge(V from, V to) {
        Set<V> out = outgoing.get(from);
        if (out == null) {
            return false;
        }
        return out.contains(to);
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
        if (isDirected()) {
            return Collections.unmodifiableSet(outgoing.get(vertex));
        }
        Set<V> result = new HashSet<>(outgoing.get(vertex));
        result.addAll(incoming.get(vertex));
        return Collections.unmodifiableSet(result);
    }

    @Override
    public int inDegree(V vertex) {
        Set<V> in = incoming.get(vertex);
        if (in == null) {
            return 0;
        }
        return in.size();
    }

    @Override
    public int outDegree(V vertex) {
        Set<V> out = outgoing.get(vertex);
        if (out == null) {
            return 0;
        }
        return out.size();
    }

    @Override
    public void clear() {
        outgoing.clear();
        incoming.clear();
        edgeSet.clear();
    }
}