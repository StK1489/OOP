package ru.nsu.vkuznetsov.task121.graph.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task121.graph.model.Graph;

class AdjacencyMatrixGraphTest {

    @Test
    void testBasicOperations() {
        Graph<String> graph = new AdjacencyMatrixGraph<>(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("A", "B"));
        assertEquals(1, graph.edgeCount());
        assertEquals(1, graph.inDegree("B"));

        graph.removeVertex("B");
        assertEquals(1, graph.vertexCount());
        assertFalse(graph.hasEdge("A", "B"));
    }

    @Test
    void testEmptyGraph() {
        Graph<String> empty = new AdjacencyMatrixGraph<>(true);
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
        Graph<String> graph = new AdjacencyMatrixGraph<>(false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B");

        assertTrue(graph.hasEdge("B", "A"));
        assertTrue(graph.neighbors("B").contains("A"));
        assertEquals(1, graph.inDegree("A"));
        assertEquals(1, graph.outDegree("A"));
    }

    @Test
    void testExceptionsAndRemovals() {
        Graph<String> graph = new AdjacencyMatrixGraph<>(true);
        graph.addVertex("A");
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "B"));

        graph.removeEdge("A", "B");
        graph.removeVertex("B");

        graph.addVertex("B");
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");
        assertEquals(1, graph.edgeCount());
        graph.removeVertex("B");
    }

    @Test
    void testEqualsHashCodeAndToString() {
        Graph<String> g1 = new AdjacencyMatrixGraph<>(true);
        Graph<String> g2 = new AdjacencyMatrixGraph<>(true);
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