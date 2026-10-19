package ru.nsu.vkuznetsov.task121.graph.api;

/**
 * Ребро графа. Направлено из {@code from} в {@code to}.
 *
 * <p>Для неориентированного графа ребро хранится в одном направлении,
 * а двусторонность обеспечивается реализацией графа.
 *
 * @param <V> тип вершины
 * @param from начальная вершина
 * @param to   конечная вершина
 */
public record Edge<V>(V from, V to) {
}