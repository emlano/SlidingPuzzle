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

            for (int i = 0; i < 10; i++) {
                rowList.add(reader.readLine());
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
                    vertices[row][col] = new Vertex(col + 1, row + 1, columns[col]);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error: No such file found named \"%s\"".formatted(file));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error: Incorrect map dimensions, width and height must be 10");
        }
    }

    public static void parseGraph(Graph graph) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                Vertex curr = vertices[row][col];
                Vertex prev = null;
                graph.addVertex(curr);

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
}
