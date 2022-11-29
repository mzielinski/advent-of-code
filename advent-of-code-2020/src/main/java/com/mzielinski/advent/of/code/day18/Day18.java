package com.mzielinski.advent.of.code.day18;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public record Day18(List<Long> results) {

    static class EquationResolverAdditionFirst extends EquationResolver {

        @Override
        String calculateEquationWithoutParentheses(String equationAsString) {
            List<String> equation = convertToList(equationAsString);
            String equationWithoutAdditions = resolveOperation(() -> List.of("+"), equation);
            return resolveOperation(() -> List.of("*"), convertToList(equationWithoutAdditions));
        }

        @Override
        public String toString() {
            return "EquationResolverAdditionFirst";
        }
    }

    static class EquationResolver {

        public long calculate(String equation) {
            String result = equation;
            while (!result.matches("^\\d+$")) {
                result = calculateEquation(result);
            }
            return Long.parseLong(result);
        }

        String calculateEquationWithoutParentheses(String equation) {
            return resolveOperation(() -> List.of("*", "+"), convertToList(equation));
        }

        String resolveOperation(Supplier<List<String>> operationsSupplier, List<String> equation) {
            List<String> operations = operationsSupplier.get();
            if (operations.stream().anyMatch(equation::contains)) {
                String operation = equation.stream().filter(operations::contains).findFirst().orElseThrow();
                int index = equation.indexOf(operation);

                // part of equation before operation
                List<String> partiallyCalculatedEquation = equation.subList(0, index - 1);

                // calculate operation result
                long firstNumber = Long.parseLong(equation.get(index - 1));
                long secondNumber = Long.parseLong(equation.get(index + 1));
                long result = switch (operation) {
                    case "+" -> firstNumber + secondNumber;
                    case "*" -> firstNumber * secondNumber;
                    default -> throw new IllegalArgumentException("Unsupported operation " + operation);
                };
                partiallyCalculatedEquation.add(String.valueOf(result));

                // part of equation after operation
                partiallyCalculatedEquation.addAll(equation.subList(index + 3, equation.size()));
                return resolveOperation(operationsSupplier, partiallyCalculatedEquation);
            }
            return String.join(" ", equation);
        }

        private String calculateEquation(String equation) {
            if (equation.contains("(")) {
                int startIndex = equation.lastIndexOf("(");
                String temp = equation.substring(startIndex);
                int endIndex = temp.indexOf(")");
                String equationWithoutParentheses = temp.substring(1, endIndex);
                String result = calculateEquationWithoutParentheses(equationWithoutParentheses);
                return equation.substring(0, startIndex) + result + temp.substring(endIndex + 1);
            }
            return calculateEquationWithoutParentheses(equation);
        }

        static List<String> convertToList(String equation) {
            return Arrays.stream(equation.split(" ")).collect(toList());
        }

        @Override
        public String toString() {
            return "EquationResolver";
        }
    }

    public long sumAllResults(String filePath, EquationResolver equationResolver) {
        return new EquationsReader(equationResolver)
                .readFile(filePath).stream()
                .reduce(Long::sum)
                .orElse(0L);
    }
}
