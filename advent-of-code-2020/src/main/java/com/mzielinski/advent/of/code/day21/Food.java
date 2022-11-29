package com.mzielinski.advent.of.code.day21;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

record Food(List<Input> inputs) {

    public List<String> unknownIngredients() {
        List<String> unknownIngredients = allIngredients();
        unknownIngredients.removeAll(knownIngredients());
        return unknownIngredients;
    }

    public List<String> canonicalDangerousIngredients() {
        return allergenByIngredient().entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(toList());
    }

    private Map<String, String> allergenByIngredient() {
        Map<String, Set<String>> knownIngredients = findKnownIngredients();
        Map<String, String> ingredientsByAllergen = new HashMap<>();
        while (ingredientsByAllergen.size() != knownIngredients.size()) {
            // each time list is filtered by already found ingredients
            knownIngredients.entrySet().stream()
                    .peek(entry -> {
                        entry.getValue().removeAll(ingredientsByAllergen.values());
                    })
                    .filter(entry -> entry.getValue().size() == 1)
                    .forEach(entry -> {
                        String ingredient = entry.getValue().stream().findAny().orElseThrow();
                        String allergen = entry.getKey();
                        ingredientsByAllergen.put(allergen, ingredient);
                    });
        }
        return ingredientsByAllergen;
    }

    private Map<String, Set<String>> findKnownIngredients() {
        Map<String, Set<String>> foodByAllergens = new HashMap<>();
        inputs.forEach(input -> input.allergens()
                .forEach(allergen -> {
                    Set<String> copy = new HashSet<>(input.ingredients());
                    Set<String> ingredients = foodByAllergens.getOrDefault(allergen, copy);
                    ingredients.retainAll(input.ingredients());
                    foodByAllergens.put(allergen, ingredients);
                }));
        return foodByAllergens;
    }

    private Set<String> knownIngredients() {
        return findKnownIngredients().values()
                .stream()
                .flatMap(Collection::stream)
                .collect(toSet());
    }

    private List<String> allIngredients() {
        return inputs.stream()
                .map(Input::ingredients)
                .flatMap(Collection::stream)
                .collect(toList());
    }
}
