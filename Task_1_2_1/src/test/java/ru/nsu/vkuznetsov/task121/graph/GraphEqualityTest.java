package ru.nsu.vkuznetsov.task121.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task121.graph.model.Graph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.AdjacencyListGraph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.AdjacencyMatrixGraph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.IncidenceMatrixGraph;

class GraphEqualityTest {
    private void fillGraph(Graph<String> g) {
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
    }

    @Test
    void testAllRepresentationsAreEqual() {
        Graph<String> list = new AdjacencyListGraph<>(true);
        Graph<String> matrix = new AdjacencyMatrixGraph<>(true);
        Graph<String> incidence = new IncidenceMatrixGraph<>(true);

        fillGraph(list);
        fillGraph(matrix);
        fillGraph(incidence);

        assertEquals(list, matrix);
        assertEquals(list, incidence);
        assertEquals(matrix, incidence);
        assertEquals(list.hashCode(), matrix.hashCode());
    }
}