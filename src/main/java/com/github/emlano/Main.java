package com.github.emlano;

public class Main {
    public static void main(String[] args) {
        MapParser.file = "map.txt";
        String[][] list = MapParser.parseMapText();

        for (String[] i : list) {
            for (String j : i) {
                System.out.print(j);
            }
            System.out.println();
        }
    }
}