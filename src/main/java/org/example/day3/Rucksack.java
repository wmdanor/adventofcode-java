package org.example.day3;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Rucksack {
    private final String items;
    private final String firstCompartment;
    private final String secondCompartment;
    private final Set<Character> firstCompartmentSet;
    private final Set<Character> secondCompartmentSet;
    private final Set<Character> itemsSet;

    public Rucksack(String items) {
        this.items = items;

        this.itemsSet = fromStringToCharSet(items);

        int halfLength = items.length() / 2;

        this.firstCompartment = items.substring(0, halfLength);
        this.secondCompartment = items.substring(halfLength);

        this.firstCompartmentSet = fromStringToCharSet(this.firstCompartment);
        this.secondCompartmentSet = fromStringToCharSet(this.secondCompartment);
    }

    public static int getPriority(char ch) throws IllegalArgumentException {
        if (ch >= 'a' && ch <= 'z') {
            return ch - 'a' + 1;
        } else if (ch >= 'A' && ch <= 'Z') {
            return ch - 'A' + 27;
        } else {
            throw new IllegalArgumentException("Invalid character \"" + ch + "\"");
        }
    }

    public int getIntersectionPriority() {
        var intersection = new HashSet<>(firstCompartmentSet);

        intersection.retainAll(secondCompartmentSet);

        Optional<Character> intersectionItem = intersection.stream().findFirst();

        return intersectionItem.map(Rucksack::getPriority).orElse(0);
    }

    public String getItems() {
        return items;
    }

    public String getFirstCompartment() {
        return firstCompartment;
    }

    public String getSecondCompartment() {
        return secondCompartment;
    }

    public Set<Character> getFirstCompartmentSet() {
        return firstCompartmentSet;
    }

    public Set<Character> getSecondCompartmentSet() {
        return secondCompartmentSet;
    }

    private Set<Character> fromStringToCharSet(String str) {
        return str.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    }

    public Set<Character> getItemsSet() {
        return itemsSet;
    }
}
