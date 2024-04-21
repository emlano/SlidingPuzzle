package com.github.emlano;

import java.util.*;

public class Graph {
    private final Map<Vertex, ArrayList<Vertex>> adjList;
    public Vertex start = null; // Staring position
    public Vertex end = null; // Finishing position
    public int gridHeight; // Maps vertical height
    public int gridWidth; // Maps horizontal width

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

    public List<Vertex> findShortestPath() { // Uses Breadth First Search
        if (start == null || end == null) throw new RuntimeException("Error: start or end not set");

        ArrayList<Vertex> exploredList = new ArrayList<>();
        Queue<Vertex> visitedList = new ArrayDeque<>();
        Vertex parent = null;

        visitedList.add(start);

        while (!visitedList.isEmpty()) {
            Vertex sv = visitedList.remove();
            exploredList.add(sv);

            if (sv.value.equals("F")) break; // Exit early if finish node reached

            for (Vertex dv : adjList.get(sv)) {
                if (dv.value.equals(".")) {
                    dv = fastTravel(dv, sv); // To simulate sliding on ice
                }

                if (!dv.value.equals("0") && !exploredList.contains(dv) && !visitedList.contains(dv)) {
                    visitedList.add(dv);
                    dv.parent = sv;
                }
            }
        }

        ArrayList<Vertex> path = new ArrayList<>();

        Vertex curr = end;
        while (curr != null) { // Retrace taken path back to Start. Uses the 'parent' var in Vertex obj
            path.add(curr);
            curr = curr.parent;
        }

        return path.reversed(); // Since End node is added at front and start added at end
    }

    private Vertex fastTravel(Vertex curr, Vertex prev) {
        int nextPosX = 0; // To calculate what the next possible node to slide to
        int nextPosY = 0;
        Vertex next = null;

        if (curr.x - prev.x < 0) {
            nextPosX = curr.x - 1;
            nextPosY = curr.y;

        } else if (curr.x - prev.x > 0) {
            nextPosX = curr.x + 1;
            nextPosY = curr.y;

        } else if (curr.y - prev.y < 0) {
            nextPosX = curr.x;
            nextPosY = curr.y - 1;

        } else if (curr.y - prev.y > 0) {
            nextPosX = curr.x;
            nextPosY = curr.y + 1;
        }

        for (Vertex v : adjList.get(curr)) {
            if (v.x == nextPosX && v.y == nextPosY) {
                next = v; // If node has an adj node  that we can slide into
                break;
            }
        }

        if (next == null || next.value.equals("0")) return curr; // Do not slide into the rock, stop just before it
        if (next.value.equals("F") || next.value.equals("S")) return next; // If next node is the finish or start, stop
        return fastTravel(next, curr); // If next node is ice, continue sliding
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
