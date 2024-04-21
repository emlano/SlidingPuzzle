package com.github.emlano;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MapParser.file = "benchmark_series/puzzle_640.txt";

        System.out.print("Stage 1 - Parsing Text File... ");
        long currentTime = System.currentTimeMillis();
        MapParser.parseMapText();
        System.out.printf("Complete! Took %dms\n", System.currentTimeMillis() - currentTime);

        System.out.print("Stage 2 - Creating Graph... ");
        currentTime = System.currentTimeMillis();
        Graph graph = new Graph();
        MapParser.parseToGraph(graph);
        System.out.printf("Complete! Took %dms\n", System.currentTimeMillis() - currentTime);

        System.out.print("Stage 3 - Finding Path... ");
        currentTime = System.currentTimeMillis();
        ArrayList<Vertex> path = new ArrayList<>(graph.findShortestPath());
        System.out.printf("Complete! Took %dms\n\n", System.currentTimeMillis() - currentTime);

        String output = MapParser.parseOutput(path);
        System.out.println(output);
    }
}