package org.example.day4;

public class AssignmentPair {
    private final Assignment first;
    private final Assignment second;

    public AssignmentPair(Assignment first, Assignment second) {
        this.first = first;
        this.second = second;
    }

    public Assignment getFirst() {
        return first;
    }

    public Assignment getSecond() {
        return second;
    }

    public boolean areAssignmentsOverlapping() {
        return first.doesContain(second) || first.isContainedBy(second);
    }

    public boolean areAssignmentsPartiallyOverlapping() {
        return first.doesOverlap(second);
    }
}
