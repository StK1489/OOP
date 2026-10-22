package ru.nsu.vkuznetsov.task121;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import ru.nsu.vkuznetsov.task121.graph.algorithms.KahnTopologicalSort;
import ru.nsu.vkuznetsov.task121.graph.algorithms.TopologicalSort;
import ru.nsu.vkuznetsov.task121.graph.io.GraphReader;
import ru.nsu.vkuznetsov.task121.graph.model.Graph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.AdjacencyListGraph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.AdjacencyMatrixGraph;
import ru.nsu.vkuznetsov.task121.graph.model.impl.IncidenceMatrixGraph;

/**
 * Точка входа: читает граф из ресурсов, сравнивает три реализации
 * и запускает топологическую сортировку.
 */
public class Main {

    /**
     * Запускает программу.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter graph file name (e.g. graph.txt): ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            System.err.println("File name is empty. Exiting.");
            return;
        }

        String resourcePath = "/" + fileName;

        try (InputStream is1 = Main.class.getResourceAsStream(resourcePath);
             InputStream is2 = Main.class.getResourceAsStream(resourcePath);
             InputStream is3 = Main.class.getResourceAsStream(resourcePath)) {

            if (is1 == null) {
                System.err.println("File '" + fileName + "' not found in resources.");
                return;
            }

            Graph<String> list = GraphReader.read(is1, true, s -> s, AdjacencyListGraph::new);
            Graph<String> matrix = GraphReader.read(is2, true, s -> s, AdjacencyMatrixGraph::new);
            Graph<String> incidence = GraphReader.read(is3, true, s -> s, IncidenceMatrixGraph::new);

            System.out.println();
            System.out.println("Adjacency list:      " + list);
            System.out.println("Adjacency matrix:    " + matrix);
            System.out.println("Incidence matrix:    " + incidence);
            System.out.println();

            System.out.println("list.equals(matrix)      = " + list.equals(matrix));
            System.out.println("list.equals(incidence)   = " + list.equals(incidence));
            System.out.println();

            TopologicalSort<String> sorter = new KahnTopologicalSort<>();
            List<String> order = sorter.sort(list);
            System.out.println("Topological sort: " + order);

        } catch (IOException e) {
            System.err.println("I/O error while reading file: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Sort error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Graph data error: " + e.getMessage());
        }
    }
}