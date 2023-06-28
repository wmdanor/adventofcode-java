package org.example.day1;

import java.util.List;

public class CaloriesInfo {
    private final List<Integer> calories;
    private final int caloriesSum;

    public CaloriesInfo(List<Integer> calories) {
        this.calories = calories;
        this.caloriesSum = calories.stream().reduce(Integer::sum).orElse(0);
    }

    public List<Integer> getCalories() {
        return calories;
    }

    public int getCaloriesSum() {
        return caloriesSum;
    }
}
