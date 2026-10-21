package ru.nsu.vkuznetsov.task121.graph.algorithms;

import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task121.graph.model.Graph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.AdjacencyListGraph;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class KahnTopologicalSortTest {
    @Test
    void testValidSort() {
        Graph<String> graph = new AdjacencyListGraph<>(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");

        TopologicalSort<String> sorter = new KahnTopologicalSort<>();
        List<String> order = sorter.sort(graph);

        assertEquals(3, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
    }

    @Test
    void testCycleThrowsException() {
        Graph<String> graph = new AdjacencyListGraph<>(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");

        TopologicalSort<String> sorter = new KahnTopologicalSort<>();
        assertThrows(IllegalStateException.class, () -> sorter.sort(graph));
    }
}