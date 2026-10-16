package ru.nsu.vkuznetsov.task121;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import ru.nsu.vkuznetsov.task121.graph.Graph;
import ru.nsu.vkuznetsov.task121.graph.algorithm.KahnTopologicalSort;
import ru.nsu.vkuznetsov.task121.graph.algorithm.TopologicalSort;
import ru.nsu.vkuznetsov.task121.graph.impl.AdjacencyListGraph;
import ru.nsu.vkuznetsov.task121.graph.impl.AdjacencyMatrixGraph;
import ru.nsu.vkuznetsov.task121.graph.impl.IncidenceMatrixGraph;
import ru.nsu.vkuznetsov.task121.graph.io.GraphReader;

/**
 * Точка входа: читает граф из файла, сравнивает три реализации
 * и запускает топологическую сортировку.
 */
public class Main {

    /**
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к файлу с графом: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.err.println("Путь не задан. Завершение работы.");
            return;
        }

        Path path = Path.of(input);

        try {
            Graph<String> list = GraphReader.read(
                    path, true, s -> s, AdjacencyListGraph::new);
            Graph<String> matrix = GraphReader.read(
                    path, true, s -> s, AdjacencyMatrixGraph::new);
            Graph<String> incidence = GraphReader.read(
                    path, true, s -> s, IncidenceMatrixGraph::new);

            System.out.println();
            System.out.println("Список смежности:      " + list);
            System.out.println("Матрица смежности:     " + matrix);
            System.out.println("Матрица инцидентности: " + incidence);
            System.out.println();

            System.out.println("list.equals(matrix)      = " + list.equals(matrix));
            System.out.println("list.equals(incidence)   = " + list.equals(incidence));
            System.out.println();

            TopologicalSort<String> sorter = new KahnTopologicalSort<>();
            List<String> order = sorter.sort(list);
            System.out.println("Топологическая сортировка: " + order);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла '" + path.toAbsolutePath() + "': "
                    + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Ошибка сортировки: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка в данных графа: " + e.getMessage());
        }
    }
}