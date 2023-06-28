package org.example.day4;

import org.example.day3.Rucksack;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    private final List<AssignmentPair> assignmentPairs = new ArrayList<>();

    public static void run(String path) throws IOException, RuntimeException {
        var day4 = new Day4(path);

        System.out.println("Amount of overlapping pairs " + day4.getOverlappingPairsAmount());
        System.out.println("Amount of partially overlapping pairs " + day4.getPartiallyOverlappingPairsAmount());
    }

    public Day4(String path) throws IOException, RuntimeException {
        parseFile(path);
    }

    public long getOverlappingPairsAmount() {
        return assignmentPairs
                .stream()
                .filter(AssignmentPair::areAssignmentsOverlapping)
                .count();
    }

    public long getPartiallyOverlappingPairsAmount() {
        return assignmentPairs
                .stream()
                .filter(AssignmentPair::areAssignmentsPartiallyOverlapping)
                .count();
    }

    private void parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            String pattern = "\\d+-\\d+,\\d+-\\d+";

            if (!line.matches(pattern)) {
                throw new RuntimeException("\"" + line + "\" line does not match the pattern");
            }

            String[] split = line.split(",");
            String[] firstSplit = split[0].split("-");
            String[] secondSplit = split[1].split("-");

            var pair = new AssignmentPair(
                    new Assignment(Integer.parseInt(firstSplit[0]), Integer.parseInt(firstSplit[1])),
                    new Assignment(Integer.parseInt(secondSplit[0]), Integer.parseInt(secondSplit[1]))
            );

            assignmentPairs.add(pair);
        }
    }
}
