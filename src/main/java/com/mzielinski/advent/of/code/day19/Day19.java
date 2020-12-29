package com.mzielinski.advent.of.code.day19;

import com.mzielinski.advent.of.code.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record Day19(Rules rules) {

    record RulesDefinition(Map<String, List<String>> rules) {

        public String mainRule(String mainRule) {
            return Optional.ofNullable(rules.get(mainRule))
                    .filter(rules -> rules.size() == 1)
                    .map(rule -> rule.get(0))
                    .orElseThrow(() -> new IllegalArgumentException("Cannot find main rule " + mainRule));
        }
    }

    record Rules(Set<String> patterns) {

        public boolean match(String message) {
            return patterns().stream().anyMatch(message::matches);
        }
    }

    record Replacement(String parentRule, String subRule) {

        public String replace(String rule) {
            return StringUtils.replace(rule, parentRule, subRule);
        }
    }

    public int calculateValidMessages(List<String> messages) {
        return messages.stream()
                .filter(this::matchAgainstRules)
                .mapToInt(valid -> 1)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public boolean matchAgainstRules(String message) {
        return rules().match(message);
    }
}
