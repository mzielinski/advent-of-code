package com.mzielinski.advent.of.code.day10;

import com.mzielinski.advent.of.code.utils.ReadFile;

public class InputProcessor implements ReadFile<Long> {

    @Override
    public Long getRecordFromLine(String nextLine) {
        return Long.parseLong(nextLine);
    }
}
