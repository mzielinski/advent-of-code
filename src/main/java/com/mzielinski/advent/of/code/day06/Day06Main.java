package com.mzielinski.advent.of.code.day06;

import com.mzielinski.advent.of.code.utils.MultilineReadFile;
import com.mzielinski.advent.of.code.utils.ReadFile;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Day06Main {

    static class Group {
        private final List<User> users = new ArrayList<>();

        int countUniqueAnswers() {
            return users.stream()
                    .flatMap(a -> a.answers.stream())
                    .collect(Collectors.toSet())
                    .size();
        }

        int countCommonAnswers() {
            if (users.size() == 1) {
                return countUniqueAnswers();
            } else {
                BiFunction<Set<Character>, User, Set<Character>> accumulator = (partialResult, user) -> {
                    if (partialResult.size() > 0) {
                        partialResult.retainAll(user.answers);
                        return partialResult;
                    }
                    return new HashSet<>(user.answers);
                };
                return users.stream().reduce(new HashSet<>(), accumulator, (c1, c2) -> null).size();
            }
        }

        @Override
        public String toString() {
            return users.toString();
        }
    }

    static class User {
        private final Set<Character> answers;

        User(Set<Character> answers) {
            this.answers = answers;
        }

        @Override
        public String toString() {
            return answers.toString();
        }
    }

    static class Day06Input implements MultilineReadFile<Group> {

        @Override
        public Group getRecordFromLine(String line) {
            Group group = new Group();
            List<User> users = Arrays.stream(line.split(DELIMITER))
                    .map(userAnswers -> {
                        Set<Character> chars = userAnswers.chars().mapToObj(c -> (char) c).collect(toSet());
                        return new User(chars);
                    })
                    .collect(toList());
            group.users.addAll(users);
            return group;
        }
    }

    private List<Group> readFile(String filename) {
        ReadFile<Group> file = new Day06Input();
        return file.readFile(filename);
    }

    private int calculate(List<Group> groups, Function<Group, Integer> consume) {
        BiFunction<Integer, Group, Integer> accumulator = (partialResult, group) -> partialResult + consume.apply(group);
        return groups.stream().reduce(0, accumulator, Integer::sum);
    }

    public static void main(String[] args) {
        Day06Main main = new Day06Main();
        String[] files = new String[]{"day06/01.passengers-form", "day06/02.passengers-form"};
        Arrays.stream(files).forEach(file -> {
            System.out.println("### File " + file);
            List<Group> groups = main.readFile(file);
            System.out.println("# Result Part1: " + main.calculate(groups, Group::countUniqueAnswers));
            System.out.println("# Result Part2: " + main.calculate(groups, Group::countCommonAnswers));
            System.out.println();
        });
    }
}
