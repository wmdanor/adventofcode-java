package org.example.day5;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Day5 {
    public static void run(String path) throws IOException, RuntimeException {
        var day5 = new Day5(path);

        System.out.println("Amount of overlapping pairs ");
    }

    public Day5(String path) throws IOException, RuntimeException {
        parseFile(path);
    }

    private void parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            System.out.println(line);
        }
    }
}
