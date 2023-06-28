package org.example.day4;

public class Assignment {
    private final int from;
    private final int to;

    public Assignment(int from, int to) throws IllegalArgumentException {
        if (from > to) {
            throw new IllegalArgumentException("\"from\" can't be bigger than \"to\"");
        }

        this.from = from;
        this.to = to;
    }

    public boolean doesContain(Assignment other) {
        return this.from <= other.from && this.to >= other.to;
    }

    public boolean isContainedBy(Assignment other) {
        return other.doesContain(this);
    }

    public boolean doesOverlap(Assignment other) {
        return this.from <= other.to && other.from <= this.to;
    }
}
