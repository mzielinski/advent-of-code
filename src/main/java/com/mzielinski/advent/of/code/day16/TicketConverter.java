package com.mzielinski.advent.of.code.day16;

import com.mzielinski.advent.of.code.day16.TicketNotesReader.Ticket;
import com.mzielinski.advent.of.code.day16.TicketNotesReader.TicketNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Definitely, too complicate class. It should be implemented in more cleaner way.
 */
public class TicketConverter {

    public Map<String, Integer> convertToRuleNames(List<Ticket> validTickets) {
        Map<String, Integer> result = new HashMap<>();
        List<Rule> rules = convertToRules(validTickets);
        while (!rules.isEmpty()) {
            rules = tryToFindUniqueRules(result, rules);
        }
        return result;
    }

    private List<Rule> tryToFindUniqueRules(Map<String, Integer> maps, List<Rule> missingRules) {
        // find uniq names
        Map<String, Long> countNames =
                missingRules.stream().map(Rule::name)
                        .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        maps.putAll(findUniqueRules(missingRules, countNames, (uniqueName, rule) -> uniqueName.equals(rule.name())));

        // find uniq indexes
        Map<Integer, Long> countIndexes =
                missingRules.stream().map(Rule::index)
                        .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        maps.putAll(findUniqueRules(missingRules, countIndexes, (index, rule) -> index == rule.index()));

        List<Rule> notFoundRules = filterProcessedRules(maps, missingRules);
        return notFoundRules.size() != missingRules.size() ? notFoundRules : List.of();
    }

    private <T> Map<String, Integer> findUniqueRules(List<Rule> rules, Map<T, Long> counters, BiPredicate<T, Rule> predicate) {
        Map<String, Integer> maps = new HashMap<>();
        List<T> uniqueValues = counters.entrySet().stream()
                .filter((entry) -> entry.getValue() == 1L)
                .map(Map.Entry::getKey)
                .collect(toList());
        for (T uniqueValue : uniqueValues) {
            rules.stream()
                    .filter(rule -> predicate.test(uniqueValue, rule))
                    .forEach(rule -> maps.put(rule.name(), rule.index()));
        }
        return maps;
    }

    private List<Rule> filterProcessedRules(Map<String, Integer> ruleNames, List<Rule> rules) {
        return rules.stream()
                .filter(rule -> !ruleNames.containsKey(rule.name()))
                .filter(rule -> !ruleNames.containsValue(rule.index()))
                .collect(toList());
    }

    private List<Rule> convertToRules(List<Ticket> tickets) {
        var validRules = covertTicketToMatrixOfRules(tickets);
        var reversedRiles = reverseColumnsWithRows(validRules);
        var reversedRules = reduceToMatchingRulesForEachColumn(reversedRiles);
        return insertColumnIndexesToRules(reversedRules);
    }

    private List<List<List<Rule>>> covertTicketToMatrixOfRules(List<Ticket> validTickets) {
        List<List<List<Rule>>> validRules = new ArrayList<>();
        for (Ticket ticket : validTickets) {
            List<TicketNumber> numbers1 = ticket.numbers();
            List<List<Rule>> validRulesForTicket = numbers1.stream()
                    .map(numbers -> {
                        List<TicketNotesReader.TicketRules> rules = numbers.validRules();
                        return rules.stream()
                                .map(rule -> new Rule(0, rule.name(), numbers))
                                .collect(toList());
                    })
                    .collect(toList());
            validRules.add(validRulesForTicket);
        }
        return validRules;
    }

    private List<List<Rule>> reduceToMatchingRulesForEachColumn(List<List<List<Rule>>> rules) {
        return rules.stream()
                .map(columns -> columns.stream()
                        .reduce(new ArrayList<Rule>(), (partialResult, rule) -> {
                            if (partialResult.size() > 0) {
                                partialResult.retainAll(rule);
                                return partialResult;
                            }
                            return new ArrayList<>(rule);
                        }, (c1, c2) -> null))
                .collect(toList());
    }

    private List<Rule> insertColumnIndexesToRules(List<List<Rule>> reversedRules) {
        return IntStream.range(0, reversedRules.size())
                .mapToObj(index -> reversedRules.get(index).stream()
                        .map(rule -> rule.updateIndex(index)))
                .flatMap(Function.identity())
                .collect(toList());
    }

    public static <T> List<List<List<T>>> reverseColumnsWithRows(List<List<List<T>>> list) {
        List<List<List<T>>> newList = new ArrayList<>();
        int columnSize = list.get(0).size();
        for (int i = 0; i < columnSize; i++) {
            List<List<T>> columns = new ArrayList<>();
            for (List<List<T>> rows : list) {
                columns.add(rows.get(i));
            }
            newList.add(columns);
        }
        return newList;
    }
}
