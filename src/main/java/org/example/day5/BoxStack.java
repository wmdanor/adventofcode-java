package org.example.day5;

import java.util.*;

public class BoxStack {
    private final List<Character> boxes;

    public BoxStack(List<Character> boxes) {
        this.boxes = boxes;
    }

    public List<Character> getBoxes() {
        return boxes;
    }

    public void add(Character box) {
        boxes.add(box);
    }

    public void addAll(Collection<Character> boxes) {
        this.boxes.addAll(boxes);
    }

    public Character remove() {
        if (boxes.isEmpty()) {
            throw new EmptyStackException();
        }

        return boxes.remove(boxes.size() - 1);
    }

    public List<Character> remove(int amount) {
        int size = boxes.size();

        if (size < amount) {
            throw new IllegalArgumentException("'amount' can't be bigger than amount of boxes in stack");
        }

        var itemsToRemove = new ArrayList<>(boxes.subList(size - amount, size));

        for (int i = 0; i < amount; i++) {
            boxes.remove(boxes.size() - 1);
        }

        return itemsToRemove;
    }

    public void move(BoxStack destination, int amount) {
        var boxes = remove(amount);

        Collections.reverse(boxes);

        destination.addAll(boxes);
    }

    public void moveOrdered(BoxStack destination, int amount) {
        var boxes = remove(amount);

        destination.addAll(boxes);
    }
}
