package org.example;

import org.example.day1.Day1;
import org.example.day2.Day2;
import org.example.day3.Day3;
import org.example.day4.Day4;
import org.example.day5.Day5;

import java.util.Map;
import java.util.Objects;

public class Main {
    public static Map<String, RunnableWithException> days = Map.ofEntries(
            Map.entry("day1", () -> Day1.run(getPath("day1.txt"))),
            Map.entry("day2", () -> Day2.run(getPath("day2.txt"))),
            Map.entry("day3", () -> Day3.run(getPath("day3.txt"))),
            Map.entry("day4", () -> Day4.run(getPath("day4.txt"))),
            Map.entry("day5", () -> Day5.run(getPath("day5.txt")))
    );

    public static void main(String[] args) throws Exception {
        days.get("day4").run();
    }

    private static String getPath(String name) throws NullPointerException {
        return Objects.requireNonNull(Main.class.getClassLoader().getResource(name)).getPath();
    }
}
