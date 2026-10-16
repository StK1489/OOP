package ru.nsu.vkuznetsov.task121.graph;

import java.util.Set;

/**
 * Граф с вершинами типа {@code V}.
 *
 * <p>Реализации могут использовать разные способы хранения:
 * матрицу смежности, матрицу инцидентности, список смежности и т.д.
 * Граф может быть ориентированным или неориентированным.
 *
 * @param <V> тип вершины
 */
public interface Graph<V> {

    /**
     * Добавляет вершину. Если уже есть, ничего не делает.
     *
     * @param vertex вершина
     */
    void addVertex(V vertex);

    /**
     * Удаляет вершину и все инцидентные рёбра.
     * Если вершины нет, ничего не делает.
     *
     * @param vertex вершина
     */
    void removeVertex(V vertex);

    /**
     * @param vertex вершина
     * @return {@code true}, если вершина есть
     */
    boolean hasVertex(V vertex);

    /**
     * @return множество всех вершин
     */
    Set<V> vertices();

    /**
     * @return количество вершин
     */
    int vertexCount();

    /**
     * Добавляет ребро между вершинами.
     *
     * @param from начальная вершина
     * @param to   конечная вершина
     * @throws IllegalArgumentException если хотя бы одной из вершин нет в графе
     */
    void addEdge(V from, V to);

    /**
     * Удаляет ребро. Если ребра нет, ничего не делает.
     *
     * @param from начальная вершина
     * @param to   конечная вершина
     */
    void removeEdge(V from, V to);

    /**
     * @param from начальная вершина
     * @param to   конечная вершина
     * @return {@code true}, если ребро есть
     */
    boolean hasEdge(V from, V to);

    /**
     * @return множество всех рёбер
     */
    Set<Edge<V>> edges();

    /**
     * @return количество рёбер
     */
    int edgeCount();

    /**
     * Соседи вершины. Для ориентированного графа исходящие,
     * для неориентированного все смежные.
     * Если вершины нет, возвращает пустое множество.
     *
     * @param vertex вершина
     * @return множество соседей
     */
    Set<V> neighbors(V vertex);

    /**
     * @param vertex вершина
     * @return количество входящих рёбер (для неориентированного степень)
     */
    int inDegree(V vertex);

    /**
     * @param vertex вершина
     * @return количество исходящих рёбер (для неориентированного степень)
     */
    int outDegree(V vertex);

    /**
     * Удаляет все вершины и рёбра.
     */
    void clear();
}