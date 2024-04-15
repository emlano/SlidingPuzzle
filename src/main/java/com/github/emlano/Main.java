package com.github.emlano;

public class Main {
    public static void main(String[] args) {
        MapParser.file = "map.txt";
        MapParser.parseMapText();

        Graph graph = new Graph();
        MapParser.parseGraph(graph);
        System.out.println(graph);
    }
}