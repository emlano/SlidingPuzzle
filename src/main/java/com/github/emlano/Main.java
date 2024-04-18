package com.github.emlano;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MapParser.file = "map.txt";
        MapParser.parseMapText();

        Graph graph = new Graph();
        MapParser.parseGraph(graph);

        ArrayList<Vertex> path = new ArrayList<>(graph.findShortestPath());
        String output = MapParser.parseOutput(path);
        System.out.println(output);
    }
}