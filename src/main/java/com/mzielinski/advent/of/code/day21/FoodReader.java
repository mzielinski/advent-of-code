package com.mzielinski.advent.of.code.day21;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

record Input(Set<String> ingredients, Set<String> allergens) {
}

class FoodReader implements ReadFile<Input> {

    @Override
    public Input convertLine(String nextLine, int lineNumber) {
        String[] food = nextLine
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .split("contains");
        Set<String> ingredients = Arrays.stream(food[0].split(" "))
                .map(String::trim)
                .collect(Collectors.toSet());

        Set<String> allergens = new HashSet<>();
        if (food.length == 2) {
            allergens = Arrays.stream(food[1].split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }
        return new Input(ingredients, allergens);
    }
}
