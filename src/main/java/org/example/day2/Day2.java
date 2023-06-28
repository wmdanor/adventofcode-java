package org.example.day2;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {
    private final List<SimpleRoundStrategy> roundStrategies = new ArrayList<>();
    private final List<NewRoundStrategy> newRoundStrategies = new ArrayList<>();

    private final int rockScore = 1;

    public static void run(String path) throws IOException {
        var day2 = new Day2(path);

        System.out.println("Strategy score " + day2.calculateStrategyScore());

        System.out.println("New strategy score " + day2.calculateNewStrategyScore());
    }

    public Day2(String path) throws IOException, RuntimeException {
        parseFile(path);
    }

    public int calculateStrategyScore() {
        return roundStrategies
                .stream()
                .map(s -> s.getYourShapeScore() + s.getRoundResultScore())
                .reduce(Integer::sum)
                .orElse(0);
    }

    public int calculateNewStrategyScore() {
        return newRoundStrategies
                .stream()
                .map(s -> s.getYourShapeScore() + s.getRoundResultScore())
                .reduce(Integer::sum)
                .orElse(0);
    }

    private void parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            roundStrategies.add(parseLine(line));
            newRoundStrategies.add(parseLineNew(line));
        }
    }

    private SimpleRoundStrategy parseLine(String line) throws RuntimeException {
        var opponentShape = switch (line.charAt(0)) {
            case 'A' -> Shapes.Rock;
            case 'B' -> Shapes.Paper;
            case 'C' -> Shapes.Scissors;
            default -> throw new IllegalStateException("Line \"" + line + "\" is broken");
        };

        var yourShape = switch (line.charAt(2)) {
            case 'X' -> Shapes.Rock;
            case 'Y' -> Shapes.Paper;
            case 'Z' -> Shapes.Scissors;
            default -> throw new IllegalStateException("Line \"" + line + "\" is broken");
        };

        return new SimpleRoundStrategy(opponentShape, yourShape);
    }

    private NewRoundStrategy parseLineNew(String line) throws RuntimeException {
        var opponentShape = switch (line.charAt(0)) {
            case 'A' -> Shapes.Rock;
            case 'B' -> Shapes.Paper;
            case 'C' -> Shapes.Scissors;
            default -> throw new IllegalStateException("Line \"" + line + "\" is broken");
        };

        var roundResult = switch (line.charAt(2)) {
            case 'X' -> RoundResult.Loss;
            case 'Y' -> RoundResult.Draw;
            case 'Z' -> RoundResult.Win;
            default -> throw new IllegalStateException("Line \"" + line + "\" is broken");
        };

        return new NewRoundStrategy(opponentShape, roundResult);
    }
}
