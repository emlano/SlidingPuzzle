package com.github.emlano;

import java.util.*;

public class Graph {
    private final Map<Vertex, ArrayList<Vertex>> adjList;
    public Vertex start = null;
    public Vertex end = null;
    public int gridHeight;
    public int gridWidth;

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

    public List<Vertex> findShortestPath() {
        if (start == null || end == null) throw new RuntimeException("Error: start or end not set");

        ArrayList<Vertex> exploredList = new ArrayList<>();
        Queue<Vertex> visitedList = new ArrayDeque<>();
        Vertex parent = null;

        visitedList.add(start);

        while (!visitedList.isEmpty()) {
            Vertex sv = visitedList.remove();
            exploredList.add(sv);

            if (sv.value.equals("F")) break;

            for (Vertex dv : adjList.get(sv)) {
                if (dv.value.equals(".")) {
                    dv = slideOnIce(dv, sv);
                }

                if (!dv.value.equals("0") && !exploredList.contains(dv) && !visitedList.contains(dv)) {
                    visitedList.add(dv);
                    dv.parent = sv;
                }
            }
        }

        ArrayList<Vertex> path = new ArrayList<>();

        Vertex curr = end;
        while (curr != null) {
            path.add(curr);
            curr = curr.parent;
        }

        return path.reversed();
    }

    private Vertex slideOnIce(Vertex curr, Vertex prev) {
        int nextPosX = 0;
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
                next = v;
                break;
            }
        }

        if (next == null || next.value.equals("0")) return curr;
        if (next.value.equals("F") || next.value.equals("S")) return next;
        return slideOnIce(next, curr);
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
