package org.example.day1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day1 {
    private final ArrayList<CaloriesInfo> caloriesInfo = new ArrayList<>();

    public static void run(String path) throws IOException {
        var day1 = new Day1(path);

        day1.getMaxCalories().ifPresentOrElse(
                x -> System.out.println("Max calories value is " + x.toString()),
                () -> System.out.println("No value")
        );

        var topCalories = day1.getTopCalories(3);

        for (var c :
                topCalories) {
            System.out.println(c);
        }

        var total = topCalories.stream().reduce(Integer::sum).get();

        System.out.println("Top 3 total " + total);
    }

    public Day1(String path) throws IOException {
        parseFile(path);
    }

    private void parseFile(String path) throws IOException {
        var reader = new BufferedReader(new FileReader(path));

        var line = reader.readLine();

        var calories = new ArrayList<Integer>();

        while (line != null) {
            if (line.isBlank()) {
                caloriesInfo.add(new CaloriesInfo(calories));

                calories = new ArrayList<>();
            } else {
                try {
                    calories.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException exception) {
                    System.out.println("Error parsing this line: \"" + line + "\", skipping");
                }
            }

            line = reader.readLine();
        }
    }

    public Optional<Integer> getMaxCalories() {
        return caloriesInfo
                .stream()
                .map(CaloriesInfo::getCaloriesSum)
                .max(Integer::compare);
    }

    public List<Integer> getTopCalories(int limit) {
        return caloriesInfo
                .stream()
                .map(CaloriesInfo::getCaloriesSum)
                .sorted(Comparator.reverseOrder())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
