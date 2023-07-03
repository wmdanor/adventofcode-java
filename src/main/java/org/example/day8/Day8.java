package org.example.day8;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 {
    private final TreesGrid treesGrid;
    public static void run(String path) throws IOException, RuntimeException {
        var day8 = new Day8(path);

        System.out.println("Visible amount " + day8.countVisible());

        System.out.println("Max scenic score " + day8.getMaxScenicScore());
    }

    public Day8(String path) throws IOException, RuntimeException {
        var grid = parseFile(path);

        treesGrid = new TreesGrid(grid);
    }

    public int countVisible() {
        return treesGrid.countVisible();
    }

    public int getMaxScenicScore() {
        return treesGrid.getMaxScenicScore();
    }

    private List<List<Integer>> parseFile(String path) throws IOException, RuntimeException {
        var grid = new ArrayList<List<Integer>>();
        var scanner = new Scanner(new FileReader(path));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            List<Integer> row = stringToList(line)
                    .stream()
                    .map(Object::toString)
                    .map(Integer::parseInt)
                    .toList();

            grid.add(row);
        }

        return grid;
    }

    private List<Character> stringToList(String str) {
        List<Character> charList = new ArrayList<>();

        for(char c : str.toCharArray()){
            charList.add(c);
        }

        return charList;
    }
}
