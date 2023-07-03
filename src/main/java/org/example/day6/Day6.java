package org.example.day6;

import org.example.day5.Day5;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day6 {
    private final String line;

    public static void run(String path) throws IOException, RuntimeException {
        var day6 = new Day6(path);

        System.out.println(day6.getFirstStartOfPacketPosition(4));

        System.out.println(day6.getFirstStartOfPacketPosition(14));
    }

    public Day6(String path) throws IOException, RuntimeException {
        line = parseFile(path);
    }

    public int getFirstStartOfPacketPosition(int uniqueAmount) {
        if (line.length() < uniqueAmount) {
            throw new IllegalStateException();
        }

        for (int i = 0; i < line.length() - uniqueAmount; i++) {
            String sub = line.substring(i, i + uniqueAmount);
            var list = stringToList(sub);
            var chars = new HashSet<>(list);

            if (chars.size() == uniqueAmount) {
                return i + uniqueAmount;
            }
        }

        return -1;
    }

    private List<Character> stringToList(String str) {
        List<Character> charList = new ArrayList<>();

        for(char c : str.toCharArray()){
            charList.add(c);
        }

        return charList;
    }

    private String parseFile(String path) throws IOException, RuntimeException {
        var scanner = new Scanner(new FileReader(path));

        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("File is empty");
        }

        return scanner.nextLine();
    }
}
