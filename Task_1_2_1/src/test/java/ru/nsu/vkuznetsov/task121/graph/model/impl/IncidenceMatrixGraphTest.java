package ru.nsu.vkuznetsov.task121.graph.model.impl;

import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task121.graph.model.Graph;
import static org.junit.jupiter.api.Assertions.*;

class IncidenceMatrixGraphTest {

    @Test
    void testBasicOperations() {
        Graph<String> graph = new IncidenceMatrixGraph<>(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("A", "B"));
        assertEquals(1, graph.edgeCount());
        assertTrue(graph.neighbors("A").contains("B"));

        graph.removeEdge("A", "B");
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testEmptyGraph() {
        Graph<String> empty = new IncidenceMatrixGraph<>(true);
        assertEquals(0, empty.vertexCount());
        assertEquals(0, empty.edgeCount());
        assertTrue(empty.vertices().isEmpty());
        assertTrue(empty.edges().isEmpty());
        assertTrue(empty.neighbors("X").isEmpty());
        assertEquals(0, empty.inDegree("X"));
        assertEquals(0, empty.outDegree("X"));
        assertFalse(empty.hasVertex("X"));
        assertFalse(empty.hasEdge("X", "Y"));
    }

    @Test
    void testUndirectedGraph() {
        Graph<String> graph = new IncidenceMatrixGraph<>(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "A"));
        assertEquals(1, graph.outDegree("A"));
        assertTrue(graph.neighbors("B").contains("A"));
    }

    @Test
    void testExceptionsAndRemovals() {
        Graph<String> graph = new IncidenceMatrixGraph<>(true);
        graph.addVertex("A");
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "B"));

        graph.removeEdge("A", "B");
        graph.removeVertex("B");

        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");
        assertEquals(1, graph.edgeCount());

        graph.removeVertex("B");
        assertEquals(1, graph.vertexCount());
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testEqualsHashCodeAndToString() {
        Graph<String> g1 = new IncidenceMatrixGraph<>(true);
        Graph<String> g2 = new IncidenceMatrixGraph<>(true);
        g1.addVertex("A");
        g2.addVertex("A");

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
        assertNotEquals(g1, null);
        assertNotNull(g1.toString());
        g1.clear();
        assertEquals(0, g1.vertexCount());
    }
}