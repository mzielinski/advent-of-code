package com.mzielinski.advent.of.code.day10;

import com.mzielinski.advent.of.code.utils.ReadFile;

public class InputProcessor implements ReadFile<Long> {

    @Override
    public Long getRecordMultiLines(String nextLine, int lineNumber) {
        return Long.parseLong(nextLine);
    }
}
