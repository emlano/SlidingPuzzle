package com.github.emlano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapParser {
    public static String file = "";
    public static Vertex[][] vertices;
    public static int mapHeight;
    public static int mapWidth;

    public static void parseMapText() {
        if (file.isEmpty()) throw new RuntimeException("Error: No file assigned to read");

        try(FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader)) {

            ArrayList<String> rowList = new ArrayList<>();

            String textRow = reader.readLine();
            while (textRow != null && !textRow.isEmpty()){
                rowList.add(textRow);
                textRow = reader.readLine();
            }

            fileReader.close();
            reader.close();

            mapHeight = rowList.size();
            mapWidth = rowList.getFirst().split("").length;

            vertices = new Vertex[mapHeight][mapWidth];

            for (int row = 0; row < mapHeight; row++) {
                String[] columns = rowList.get(row).split("");

                if (columns.length != mapWidth)
                    throw new RuntimeException("Error: Non uniform map width at row " + (row + 1));

                for (int col = 0; col < mapWidth; col++) {
                    vertices[row][col] = new Vertex(row + 1, col + 1, columns[col]);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error: No such file found named \"%s\"".formatted(file));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error: Incorrect map dimensions, width and height must be 10");
        }
    }

    public static void parseGraph(Graph graph) {
        graph.gridHeight = mapHeight;
        graph.gridWidth = mapWidth;

        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                Vertex curr = vertices[row][col];
                Vertex prev = null;
                graph.addVertex(curr);

                if (curr.value.equals("S")) {
                    if (graph.start != null)
                        throw new RuntimeException("Error: Multiple starts cannot exist!");
                    graph.start = curr;
                }

                if (curr.value.equals("F")) {
                    if (graph.end != null)
                        throw new RuntimeException("Error: Multiple ends cannot exist!");
                    graph.end = curr;
                }

                if (row > 0) {
                    prev = vertices[row - 1][col];
                    graph.addEdge(curr, prev);
                    graph.addEdge(prev, curr);
                }

                if (col > 0) {
                    prev = vertices[row][col - 1];
                    graph.addEdge(curr, prev);
                    graph.addEdge(prev, curr);
                }
            }
        }
    }

    public static String parseOutput(ArrayList<Vertex> path) {
        StringBuilder sb = new StringBuilder();

        int step = 0;
        Vertex prev = null;

        while (step < path.size()) {
            Vertex curr = path.get(step);

            if (prev != null && (prev.y != curr.y && prev.x != curr.x)) {
                throw new RuntimeException("Error: Illegal movement at step " + step);
            }

            sb.append("%s. ".formatted(step + 1));

            if (step == 0) {
                sb.append("Start at ");

            } else if (curr.x < prev.x) {
                sb.append("Move up to ");

            } else if (curr.x > prev.x) {
                sb.append("Move down to ");

            } else if (curr.y < prev.y) {
                sb.append("Move left to ");

            } else if (curr.y > prev.y) {
                sb.append("Move right to ");
            } else throw new RuntimeException("Error: Path stagnates unnaturally at step " + step + 1);

            sb.append("(%d,%d)\n".formatted(curr.y, curr.x));

            step++;
            prev = curr;
        }

        step++;
        sb.append("%s. Done!\n".formatted(step));
        return sb.toString();
    }
}
