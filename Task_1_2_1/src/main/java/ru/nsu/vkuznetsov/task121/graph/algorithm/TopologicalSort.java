package ru.nsu.vkuznetsov.task121.graph.algorithm;

import java.util.List;
import ru.nsu.vkuznetsov.task121.graph.Graph;

/**
 * Алгоритм топологической сортировки вершин ориентированного графа.
 *
 * <p>Топологическая сортировка возможна только для ациклического
 * ориентированного графа. Если граф содержит цикл, выбрасывается
 * {@link IllegalStateException}.
 *
 * @param <V> тип вершины
 */
public interface TopologicalSort<V> {

    /**
     * Возвращает вершины графа в порядке топологической сортировки.
     *
     * @param graph граф
     * @return список вершин в топологическом порядке
     * @throws IllegalStateException если граф содержит цикл
     */
    List<V> sort(Graph<V> graph);
}