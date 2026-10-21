package ru.nsu.vkuznetsov.task121.graph.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.vkuznetsov.task121.graph.io.GraphReader;
import ru.nsu.vkuznetsov.task121.graph.model.Graph;

class AdjacencyListGraphTest {
    private Graph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListGraph<>(true);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
    }

    @Test
    void testAddAndRemoveVertex() {
        assertTrue(graph.hasVertex("A"));
        graph.removeVertex("A");
        assertFalse(graph.hasVertex("A"));
        assertEquals(2, graph.vertexCount());
    }

    @Test
    void testAddAndRemoveEdge() {
        graph.addEdge("A", "B");
        assertTrue(graph.hasEdge("A", "B"));
        graph.removeEdge("A", "B");
        assertFalse(graph.hasEdge("A", "B"));
        assertEquals(0, graph.edgeCount());
    }

    @Test
    void testNeighborsAndDegrees() {
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        assertEquals(2, graph.neighbors("A").size());
        assertTrue(graph.neighbors("A").contains("B"));
        assertEquals(0, graph.inDegree("A"));
        assertEquals(2, graph.outDegree("A"));
    }

    @Test
    void testEmptyGraph() {
        Graph<String> empty = new AdjacencyListGraph<>(true);
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
    void testUndirectedAndSelfLoops() {
        Graph<String> undirected = new AdjacencyListGraph<>(false);
        undirected.addVertex("A");
        undirected.addVertex("B");
        undirected.addEdge("A", "B");

        assertTrue(undirected.hasEdge("B", "A"));

        undirected.addEdge("A", "A");
        assertEquals(2, undirected.edgeCount());
    }

    @Test
    void testExceptionsAndInvalidEdges() {
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge("A", "X"));
        graph.removeVertex("X");
        graph.removeEdge("A", "X");
        graph.addEdge("A", "B");
        graph.addEdge("A", "B");
        assertEquals(1, graph.edgeCount());
    }

    @Test
    void testEqualsHashCodeAndToString() {
        Graph<String> g2 = new AdjacencyListGraph<>(true);
        g2.addVertex("A");
        g2.addVertex("B");
        g2.addVertex("C");

        assertEquals(graph, g2);
        assertEquals(graph.hashCode(), g2.hashCode());
        assertNotEquals(graph, null);
        assertNotEquals(graph, "String");

        Graph<String> undirected = new AdjacencyListGraph<>(false);
        undirected.addVertex("A");
        undirected.addVertex("B");
        undirected.addVertex("C");
        assertNotEquals(graph, undirected);

        assertNotNull(graph.toString());
        assertTrue(graph.toString().startsWith("{"));
        graph.clear();
        assertEquals(0, graph.vertexCount());
    }

    @Test
    void testGraphReaderCoverage() throws IOException {
        String content = "A B\nB C\n";
        ByteArrayInputStream is = new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8));

        Graph<String> g = GraphReader.read(is, true, s -> s, AdjacencyListGraph::new);

        assertEquals(3, g.vertexCount());
        assertTrue(g.hasEdge("A", "B"));
    }
}