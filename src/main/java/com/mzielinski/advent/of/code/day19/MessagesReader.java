package com.mzielinski.advent.of.code.day19;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.List;

public class MessagesReader implements ReadFile<String> {

    public static List<String> parseMessages(String filePath) {
        return new MessagesReader().readFile(filePath);
    }

    @Override
    public String convertLine(String line, int lineNumber) {
        return line.matches("[ab]+") ? line : null;
    }
}
