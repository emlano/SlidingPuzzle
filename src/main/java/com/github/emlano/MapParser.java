package com.github.emlano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapParser {
    public static String file = "";
    public static String[][] nodeList;
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

            nodeList = new String[mapHeight][mapWidth];

            for (int i = 0; i < mapHeight; i++) {
                if (rowList.get(i).split("").length != mapWidth)
                    throw new RuntimeException("Error: Non uniform map width at row " + (i + 1));

                nodeList[i] = rowList.get(i).split("");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error: No such file found named \"%s\"".formatted(file));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error: Incorrect map dimensions, width and height must be 10");
        }
    }

    public static void parseGraph(Graph graph) {
        Vertex prev = null;
        Vertex curr;

        for (int row = 0; row < nodeList.length; row++) {
            for (int col = 0; col < nodeList.length; col++) {
                curr = new Vertex(col, row, nodeList[row][col]);
                graph.addVertex(curr);

                if (col >= 1) {
                    graph.addEdge(curr, prev);
                    graph.addEdge(prev, curr);
                }

                prev = curr;
            }
        }
    }
}
