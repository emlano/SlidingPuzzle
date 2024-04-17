package com.github.emlano;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final Map<Vertex, ArrayList<Vertex>> adjList;
    public Vertex start = null;
    public Vertex end = null;

    public Graph() {
        this.adjList = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        if (adjList.containsKey(v)) throw new RuntimeException("Error: Attempted to insert a duplicate vertex");
        adjList.put(v, new ArrayList<>());
    }

    public void addEdge(Vertex sv, Vertex dv) {
        adjList.get(sv).add(dv);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Vertex i : adjList.keySet()) {
            sb.append("%s -> ".formatted(i.toString()));

            for (Vertex j : adjList.get(i)) {
                sb.append("%s ".formatted(j.toString()));
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
