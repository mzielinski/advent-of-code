package com.mzielinski.advent.of.code.day18;

import com.mzielinski.advent.of.code.utils.ReadFile;

public record EquationsReader(Day18.EquationResolver equationResolver) implements ReadFile<Long> {

    @Override
    public Long getRecordMultiLines(String nextLine, int lineNumber) {
        return equationResolver.calculate(nextLine);
    }
}
