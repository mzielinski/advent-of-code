package com.mzielinski.advent.of.code.day21;

import java.util.List;

public class Day21 {

    public int calculateNumberOfSaveIngredients(String filePath) {
        List<Input> inputs = new FoodReader().readFile(filePath);
        return new Food(inputs).unknownIngredients().size();
    }

    public String findCanonicalDangerousIngredients(String filePath) {
        List<Input> inputs = new FoodReader().readFile(filePath);
        Food food = new Food(inputs);
        return String.join(",", food.canonicalDangerousIngredients());
    }
}
