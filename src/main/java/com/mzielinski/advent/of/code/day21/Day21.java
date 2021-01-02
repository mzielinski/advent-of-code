package com.mzielinski.advent.of.code.day21;

import java.util.*;

public class Day21 {

    public int calculateNumberOfSaveIngredients(String filePath) {
        List<Input> inputs = new FoodReader().readFile(filePath);
        return new Food(inputs).unknownIngredients().size();
    }
}
