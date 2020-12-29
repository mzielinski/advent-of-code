package com.mzielinski.advent.of.code.day19;

import com.mzielinski.advent.of.code.day19.Day19.RulesDefinition;
import com.mzielinski.advent.of.code.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RulesResolver {

    private final RulesDefinition input;
    private final Map<String, String> lettersRuleId;

    public RulesResolver(RulesDefinition input) {
        this.input = input;
        this.lettersRuleId = findRulesWithLetters(input.rules());
    }

    public Day19.Rules convertToRules() {
        return convertToRules(Integer.MAX_VALUE);
    }

    public Day19.Rules convertToRules(int maxLength) {
        String mainRule = input().mainRule("0");
        Set<String> combinations = findFinalCombinations(Set.of(mainRule), maxLength);
        return new Day19.Rules(combinations);
    }

    private Set<String> findFinalCombinations(Set<String> finalCombinations, int maxLength) {
        Set<String> combinations = new HashSet<>();
        for (String rule : finalCombinations) {
            if (rule.length() > maxLength) continue; // optimization
            combinations.addAll(findCombinationsForSingleRule(rule));
        }
        if (combinations.isEmpty()) return convertToPattern(finalCombinations);
        return findFinalCombinations(combinations, maxLength);
    }

    private Set<String> convertToPattern(Set<String> finalCombinations) {
        return finalCombinations.stream()
                .map(rule -> rule.replaceAll(",", ""))
                .collect(Collectors.toSet());
    }

    private Set<String> findCombinationsForSingleRule(String rule) {
        String cleanRule = replaceToLetters(rule);
        if (onlyLetter(cleanRule)) {
            return Set.of();
        }
        return Arrays.stream(cleanRule.split(","))
                .flatMap(this::findRule)
                .flatMap(this::convertToReplacement)
                .map(replacement -> replacement.replace(cleanRule))
                .collect(Collectors.toSet());
    }

    // optimization
    private String replaceToLetters(String rule) {
        String clearRun = StringUtils.replace(rule, lettersRuleId.get("a"), "a");
        return StringUtils.replace(clearRun, lettersRuleId.get("b"), "b");
    }

    // fast search
    private boolean onlyLetter(String rule) {
        return rule.chars().allMatch(c -> c == 'a' || c == 'b' || c == ',');
    }

    // optimization
    private Map<String, String> findRulesWithLetters(Map<String, List<String>> rules) {
        return rules.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(value -> value.matches("^[ab]+$")))
                .filter(entry -> entry.getValue().size() == 1)
                .collect(Collectors.toMap(entry -> entry.getValue().get(0), Map.Entry::getKey));
    }

    private Stream<Day19.Replacement> convertToReplacement(Map.Entry<String, List<String>> entry) {
        return entry.getValue().stream()
                .map(this::replaceToLetters)
                .map(subRule -> new Day19.Replacement(entry.getKey(), subRule));
    }

    private Stream<Map.Entry<String, List<String>>> findRule(String rule) {
        return input().rules().entrySet().stream()
                .filter(entry -> entry.getKey().equals(rule));
    }

    public RulesDefinition input() {
        return input;
    }
}
