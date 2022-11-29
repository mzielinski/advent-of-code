package com.mzielinski.advent.of.code.day12.input;

import com.mzielinski.advent.of.code.day12.model.Instruction;
import com.mzielinski.advent.of.code.utils.StreamReader;

public class InstructionsReader extends StreamReader<Instruction> {

    public InstructionsReader(String filePath) {
        super(filePath);
    }

    @Override
    public Instruction processLines(String line) {
        return Instruction.parse(line);
    }
}
