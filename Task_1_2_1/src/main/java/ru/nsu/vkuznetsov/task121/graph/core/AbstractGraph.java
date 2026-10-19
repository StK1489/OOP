package ru.nsu.vkuznetsov.task121.graph.core;

import ru.nsu.vkuznetsov.task121.graph.api.Graph;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Базовая реализация графа. Хранит флаг ориентированности
 * и предоставляет общие {@code equals}, {@code hashCode}, {@code toString}.
 *
 * <p>Конкретные способы хранения (список, матрица смежности,
 * матрица инцидентности) реализуются в подклассах.
 *
 * @param <V> тип вершины
 */
public abstract class AbstractGraph<V> implements Graph<V> {

    private final boolean directed;

    /**
     * Создаёт граф с заданной ориентированностью.
     *
     * @param directed {@code true}, если граф ориентированный
     */
    protected AbstractGraph(boolean directed) {
        this.directed = directed;
    }

    /**
     * Возвращает признак ориентированности графа.
     *
     * @return {@code true}, если граф ориентированный
     */
    public final boolean isDirected() {
        return directed;
    }

    /**
     * Сравнивает графы по содержимому: ориентированность, множество вершин
     * и множество рёбер. Конкретный способ хранения при этом не важен.
     *
     * @param o объект для сравнения
     * @return {@code true}, если графы равны
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractGraph<?> other)) {
            return false;
        }
        return directed == other.directed
                && vertices().equals(other.vertices())
                && edges().equals(other.edges());
    }

    /**
     * Возвращает хеш-код графа.
     *
     * @return хеш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(directed, vertices(), edges());
    }

    /**
     * Возвращает строковое представление в виде списка смежности:
     * {@code {A: [B, C], B: [C], C: []}}.
     * Порядок обхода вершин определяется реализацией {@link #vertices()}.
     *
     * @return строковое представление
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        for (V vertex : vertices()) {
            StringJoiner neighborsJoiner = new StringJoiner(", ", "[", "]");
            for (V neighbor : neighbors(vertex)) {
                neighborsJoiner.add(String.valueOf(neighbor));
            }
            joiner.add(vertex + ": " + neighborsJoiner);
        }
        return joiner.toString();
    }
}