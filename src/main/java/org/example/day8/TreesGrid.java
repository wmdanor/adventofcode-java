package org.example.day8;

import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TreesGrid {
    private final List<List<Integer>> grid;

    public TreesGrid(List<List<Integer>> grid) {
        this.grid = grid;
    }

    public int countVisible() {
        return IntStream
                .range(0, grid.size())
                .map(this::countVisible)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private int countVisible(int row) {
        return (int) IntStream
                .range(0, grid.get(0).size())
                .filter(j -> isVisible(row, j))
                .count();
    }

    public int getMaxScenicScore() {
        return IntStream
                .range(0, grid.size())
                .parallel()
                .map(this::getMaxScenicScore)
                .max()
                .orElse(0);
    }

    private int getMaxScenicScore(int row) {
        return IntStream
                .range(0, grid.get(0).size())
                .map(column -> getScenicScore(row, column))
                .max()
                .orElse(0);
    }

    public int getScenicScore(int row, int column) {
        if (
                row == 0 ||
                column == 0 ||
                row == grid.size() - 1 ||
                column == grid.get(0).size() - 1
        ) {
            return 0;
        }

        int treeHeight = this.get(row, column);

        int leftVisible = countVisible(column - 1, 0, treeHeight, j -> this.get(row, j));
        int rightVisible = countVisible(column + 1, grid.get(0).size() - 1, treeHeight, j -> this.get(row, j));
        int topVisible = countVisible(row - 1, 0, treeHeight, i -> this.get(i, column));
        int bottomVisible = countVisible(row + 1, grid.size() - 1, treeHeight, i -> this.get(i, column));

        return leftVisible * rightVisible * topVisible * bottomVisible;
    }

    private int countVisible(int from, int to, int treeHeight, UnaryOperator<Integer> getter) {
        int result = 0;
        var range = IntStream
                .rangeClosed(Integer.min(from, to), Integer.max(from, to))
                .boxed()
                .sorted(from < to ? Integer::compare : Collections.reverseOrder())
                .toList();

        for (int i : range) {
            result++;

            if (getter.apply(i) >= treeHeight) {
                break;
            }
        }

        return result;
    }

    private static <T> Collector<T, ?, List<T>> takeWhileInclusive(Predicate<T> predicate) {
        return Collector.of(
                ArrayList::new,
                (list, t) -> {
                    if (list.isEmpty() || predicate.test(list.get(list.size() - 1))) {
                        list.add(t);
                    }
                },
                (list1, list2) -> {
                    if (list1.isEmpty()) {
                        return list2;
                    }
                    list1.addAll(list2);
                    return list1;
                });
    }

    public int get(int row, int column) {
        return grid.get(row).get(column);
    }

    public boolean isHidden(int row, int column) {
        return !isVisible(row, column);
    }

    public boolean isVisible(int row, int column) {
        if (
                row == 0 ||
                column == 0 ||
                row == grid.size() - 1 ||
                column == grid.get(0).size() - 1
        ) {
            return true;
        }

        int treeHeight = this.get(row, column);

        boolean leftHidden = IntStream
                .range(0, column)
                .map(j -> this.get(row, j))
                .anyMatch(t -> t >= treeHeight);

        if (!leftHidden) return true;

        boolean rightHidden = IntStream
                .range(column + 1, grid.get(0).size())
                .map(j -> this.get(row, j))
                .anyMatch(t -> t >= treeHeight);

        if (!rightHidden) return true;

        boolean topHidden = IntStream
                .range(0, row)
                .map(i -> this.get(i, column))
                .anyMatch(t -> t >= treeHeight);

        if (!topHidden) return true;

        boolean bottomHidden = IntStream
                .range(row + 1, grid.size())
                .map(i -> this.get(i, column))
                .anyMatch(t -> t >= treeHeight);

        if (!bottomHidden) return true;

        return false;
    }

    public List<List<Integer>> getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        return grid
                .stream()
                .map(this::rowToString)
                .collect(Collectors.joining("\n"));

    }

    private String rowToString(List<Integer> row) {
        return row
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
