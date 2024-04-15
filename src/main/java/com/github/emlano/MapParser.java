package com.github.emlano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapParser {
    public static String file = "";

    public static String[][] parseMapText() {
        if (file.isEmpty()) throw new RuntimeException("Error: No file assigned to read");

        String[][] nodeList = new String[10][10];
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < 10; i++) {
                String row = reader.readLine();
                if (row == null) throw new ArrayIndexOutOfBoundsException();
                nodeList[i] = row.split("", 10);
            }

            return nodeList;

        } catch (IOException e) {
            throw new RuntimeException("Error: No such file found named \"%s\"".formatted(file));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Error: Incorrect map dimensions, width and height must be 10");
        }
    }
}
