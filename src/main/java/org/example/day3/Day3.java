package org.example.day3;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day3 {
    private final List<Rucksack> rucksacks = new ArrayList<>();

    public static void run(String path) throws IOException, RuntimeException {
        var day3 = new Day3(path);

        System.out.println("Sum of intersection priorities " + day3.getSumOfIntersectionPriorities());
        System.out.println("Sum of group priorities " + day3.getGroupsPrioritySum());
    }

    public Day3(String path) throws IOException, RuntimeException {
        parseFile(path);
    }

    public int getGroupsPrioritySum() {
        if (rucksacks.size() % 3 != 0) {
            throw new RuntimeException("Amount of rucksacks is not dividable by 3");
        }

        int sum = 0;

        for (int i = 0; i < rucksacks.size(); i += 3) {
            int groupPriority = getGroupPriority(
                    rucksacks.get(i),
                    rucksacks.get(i + 1),
                    rucksacks.get(i + 2)
            );

            sum += groupPriority;
        }

        return sum;
    }

    public int getGroupPriority(Rucksack r1, Rucksack r2, Rucksack r3) {
        var intersection = new HashSet<>(r1.getItemsSet());

        intersection.retainAll(r2.getItemsSet());
        intersection.retainAll(r3.getItemsSet());

        Optional<Character> intersectionItem = intersection.stream().findFirst();

        return intersectionItem.map(Rucksack::getPriority).orElse(0);
    }

    public int getSumOfIntersectionPriorities() {
        return rucksacks
                .stream()
                .map(Rucksack::getIntersectionPriority)
                .reduce(Integer::sum).orElse(0);
    }

    private void parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            var rucksack = new Rucksack(line);

            rucksacks.add(rucksack);
        }
    }
}
