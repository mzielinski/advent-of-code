package com.mzielinski.advent.of.code.day04;

import com.mzielinski.advent.of.code.utils.MultilineReadFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day04Main {

    private static class Part2Passport extends Passport {

        Part2Passport(Map<String, String> fields) {
            super(fields);
        }

        @Override
        boolean isValid() {
            // part 2
            int byr = Integer.parseInt(fields.getOrDefault("byr", "0"));
            if (byr < 1920 || byr > 2002) return false;

            int iyr = Integer.parseInt(fields.getOrDefault("iyr", "0"));
            if (iyr < 2010 || iyr > 2020) return false;

            int eyr = Integer.parseInt(fields.getOrDefault("eyr", "0"));
            if (eyr < 2020 || eyr > 2030) return false;

            String hgt = fields.getOrDefault("hgt", "");
            if (hgt.endsWith("cm")) {
                int cm = Integer.parseInt(hgt.replaceAll("cm", ""));
                if (cm < 150 || cm > 193) return false;
            } else if (hgt.endsWith("in")) {
                int in = Integer.parseInt(hgt.replaceAll("in", ""));
                if (in < 59 || in > 76) return false;
            } else return false;

            String hcl = fields.getOrDefault("hcl", "");
            if (!hcl.matches("#[0-9a-f]{6}")) return false;

            String ecl = fields.getOrDefault("ecl", "");
            if (!List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl)) return false;

            String pid = fields.getOrDefault("pid", "0");
            return pid.length() == 9;
        }
    }

    private static class Passport {

        final Map<String, String> fields;

        Passport(Map<String, String> fields) {
            this.fields = fields;
        }

        boolean isValid() {
            return fields.size() == 8 || (fields.size() == 7 && !fields.containsKey("cid"));
        }

        @Override
        public String toString() {
            return fields.toString();
        }
    }

    static class Day04MainPart2Input implements MultilineReadFile<Passport> {

        @Override
        public Passport getRecordMultiLines(String line, int lineNumber) {
            Map<String, String> fields = new HashMap<>();
            String[] s = line.trim().replaceAll(";", " ").split(" ");
            for (String item : s) {
                String[] field = item.split(":");
                fields.put(field[0], field[1]);
            }

            return new Passport(fields);
        }
    }

    private List<Passport> readFile(String filename) {
        return new Day04MainPart2Input().readFile(filename);
    }

    private List<Part2Passport> readFilePart2(String filename) {
        return new Day04MainPart2Input().readFile(filename).stream()
                .map(passport -> new Part2Passport(passport.fields))
                .collect(Collectors.toList());
    }

    private long calculate(List<? extends Passport> passports) {
        return passports.stream().filter(Passport::isValid).count();
    }

    public static void main(String[] args) {
        Day04Main main = new Day04Main();
        String[] files = new String[]{"day04/01.passport", "day04/02.passport"};
        Arrays.stream(files).forEach(file -> {
            System.out.println("### File " + file);
            System.out.println("# Result Part1: " + main.calculate(main.readFile(file)));
            System.out.println("# Result Part2: " + main.calculate(main.readFilePart2(file)));
            System.out.println();
        });
    }
}
