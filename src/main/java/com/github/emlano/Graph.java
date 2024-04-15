package com.github.emlano;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    private ArrayList<LinkedList<Vertex>> adjList;

    public Graph() {
        this.adjList = new ArrayList<>();
    }

    public void addVertex(Vertex v) {
        if (adjList.stream().anyMatch(e -> e.getFirst().equals(v))) return;
        LinkedList<Vertex> linkedList = new LinkedList<>();
        linkedList.add(v);
        adjList.add(linkedList);
    }
}
