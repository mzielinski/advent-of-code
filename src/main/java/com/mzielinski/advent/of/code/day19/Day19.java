package com.mzielinski.advent.of.code.day19;

import java.util.*;
import java.util.stream.Collectors;

public record Day19(Map<String, Set<List<String>>> input) {

    record Rule(Set<String> patterns) {
    }

    record Rules(Map<String, Rule> rules) {
    }

    public Rules convertToRules() {
        Map<String, Rule> rules = input.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                    List<Set<List<String>>> cache = new ArrayList<>();
                    cache.add(entry.getValue());
                    return buildRule(cache);
                }));
        return new Rules(rules);
    }

    private Rule buildRule(List<Set<List<String>>> cache) {
        List<Set<List<String>>> temp = new ArrayList<>();
        for (Set<List<String>> rule : cache) {
            if (allLetters(rule)) {
                temp.add(rule);
            } else {
                for (List<String> partOfRule : rule) {
                    for (String s : partOfRule) {
                        temp.add(input.get(s));
                    }
                }
            }
        }
        if (!allLetters(temp)) buildRule(temp);
        System.out.println(cache);
        return new Rule(null);
    }

    private boolean allLetters(Set<List<String>> cache) {
        return cache.stream()
                .flatMap(Collection::stream)
                .allMatch(value -> value.matches("^[a|b]$"));
    }

    private boolean allLetters(List<Set<List<String>>> cache) {
        return cache.stream()
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .allMatch(value -> value.matches("^[a|b]$"));
    }

    public boolean matchAgainstRules(String message, Rule rule) {
        return rule.patterns().contains(message);
    }
}
