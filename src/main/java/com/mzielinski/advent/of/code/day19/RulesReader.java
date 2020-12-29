package com.mzielinski.advent.of.code.day19;

import com.mzielinski.advent.of.code.day19.Day19.RulesDefinition;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class RulesReader {

    public static Day19.RulesDefinition parseRules(String filePath) {
        return new RulesReader().getRulesDefinition(filePath);
    }

    private RulesDefinition getRulesDefinition(String filePath) {
        Map<String, List<String>> rulesDefinitions = new HashMap<>();
        InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
        try (Scanner scanner = new Scanner(stream)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                if (line.matches("[ab]+")) return new RulesDefinition(rulesDefinitions);
                else if (line.contains(":")) {
                    String[] rule = line.split(":");
                    List<String> conditions = Arrays.stream(rule[1].split("\\|"))
                            .map(String::trim)
                            .map(condition -> condition
                                    .replaceAll(" ", ",")
                                    .replaceAll("\"", ""))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    rulesDefinitions.put(rule[0], conditions);
                }
            }
        }
        throw new IllegalArgumentException("Invalid file format of " + filePath);
    }
}
