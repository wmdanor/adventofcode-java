package org.example.day5;

public class Move {
    private final int amount;
    private final String from;
    private final String to;

    public Move(int amount, String from, String to) {
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public int getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
