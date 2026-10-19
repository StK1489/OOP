package ru.nsu.vkuznetsov.task121.graph.algorithms;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import ru.nsu.vkuznetsov.task121.graph.api.Graph;
import ru.nsu.vkuznetsov.task121.graph.api.TopologicalSort;

/**
 * Топологическая сортировка алгоритмом Кана.
 *
 * <p>Идея: вершины с нулевой входящей степенью можно ставить первыми.
 * После удаления такой вершины у её соседей уменьшается входящая степень,
 * и среди них появляются новые кандидаты с нулём.
 *
 * @param <V> тип вершины
 */
public class KahnTopologicalSort<V> implements TopologicalSort<V> {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> sort(Graph<V> graph) {
        Map<V, Integer> inDegree = new HashMap<>();
        for (V vertex : graph.vertices()) {
            inDegree.put(vertex, graph.inDegree(vertex));
        }

        Queue<V> queue = new ArrayDeque<>();
        for (Map.Entry<V, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<V> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            V vertex = queue.poll();
            result.add(vertex);
            for (V neighbor : graph.neighbors(vertex)) {
                int newDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, newDegree);
                if (newDegree == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != graph.vertexCount()) {
            throw new IllegalStateException(
                    "Graph contains a cycle: sorted " + result.size()
                            + " из " + graph.vertexCount() + " вершин");
        }
        return result;
    }
}