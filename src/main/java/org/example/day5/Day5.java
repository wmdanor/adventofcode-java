package org.example.day5;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Day5 {
    private final Map<String, BoxStack> boxStacks = new HashMap<>();
    private final List<Move> moves = new ArrayList<>();

    public static void run(String path) throws IOException, RuntimeException {
        var day5 = new Day5(path);

        day5.printStacks();

//        day5.doSavedMoves();
        day5.doSavedMovesOrdered();
    }

    public Day5(String path) throws IOException, RuntimeException {
        parseFile(path);
    }

    public void printStacks() {
        for (var entry : boxStacks.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getBoxes());
        }
    }

    public void doSavedMoves() {
        for (var move : moves) {
            var from = boxStacks.get(move.getFrom());
            var to = boxStacks.get(move.getTo());
            var amount = move.getAmount();

            System.out.println("Moving " + amount + " boxes from " + move.getFrom() + " to " + move.getTo());

            from.move(to, amount);

            printStacks();
        }
    }

    public void doSavedMovesOrdered() {
        for (var move : moves) {
            var from = boxStacks.get(move.getFrom());
            var to = boxStacks.get(move.getTo());
            var amount = move.getAmount();

            System.out.println("Moving " + amount + " boxes from " + move.getFrom() + " to " + move.getTo());

            from.moveOrdered(to, amount);

            printStacks();
        }
    }

    private void parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        var boxesPattern = Pattern.compile("(?:(?:\\[(?<name>[A-Z])\\])|(?: {3}))(?: |$)");
        var stacksNamesPattern = Pattern.compile(" (?<name>[a-zA-Z0-9]) ?");
        var movePattern = Pattern.compile("move (?<amount>\\d+) from (?<from>\\d+) to (?<to>\\d+)");

        var boxesLists = new ArrayList<ArrayList<Character>>();
        var stacksNames = new ArrayList<String>();

        // Check setup
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            var boxesMatcher = boxesPattern.matcher(line);
            var stacksNamesMatcher = stacksNamesPattern.matcher(line);

            boolean matchesBoxes = false;
            boolean matchesStacks = false;

            int index = 0;
            while (boxesMatcher.find()) {
                matchesBoxes = true;

                while (boxesLists.size() <= index) {
                    boxesLists.add(new ArrayList<>());
                }

                String nameString = boxesMatcher.group("name");

                index++;

                if (nameString == null) {
                    continue;
                }

                char name = nameString.charAt(0);

                boxesLists.get(index - 1).add(name);
            }

            while (stacksNamesMatcher.find()) {
                matchesStacks = true;

                stacksNames.add(stacksNamesMatcher.group("name"));
            }

            if (matchesStacks) {
                // Skip empty line after stacks names
                scanner.nextLine();

                break;
            }

            if (!matchesBoxes) {
                throw new IllegalStateException("\"" + line + "\n line did not match any patterns");
            }
        }

        if (stacksNames.size() != boxesLists.size()) {
            throw new IllegalStateException("Boxes lists amount is not equal to stacks names amount");
        }

        for (int i = 0; i < stacksNames.size(); i++) {
            var stackName = stacksNames.get(i);
            var boxes = boxesLists.get(i);

            Collections.reverse(boxes);

            var boxStack = new BoxStack(boxes);

            boxStacks.put(stackName, boxStack);
        }

        // Check moves
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            var matcher = movePattern.matcher(line);

            if (!matcher.find()) {
                throw new RuntimeException("\"" + line + "\n line does not match the move pattern");
            }

            int amount = Integer.parseInt(matcher.group("amount"));
            String from = matcher.group("from");
            String to = matcher.group("to");

            moves.add(new Move(amount, from, to));
        }
    }
}
