package com.mzielinski.advent.of.code.day16;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class TicketNotesReader {

    record TicketRule(long from, long to) {

        public boolean match(long value) {
            return value >= from && value <= to;
        }
    }

    record TicketRules(String name, List<TicketRule> rules) {
        static TicketRules of(String name, String[] patterns) {
            return new TicketRules(name, Arrays.stream(patterns)
                    .map(String::trim)
                    .map(rule -> rule.split("-"))
                    .map(rule -> new TicketRule(Integer.parseInt(rule[0]), Integer.parseInt(rule[1])))
                    .collect(toList()));
        }
    }

    record RuleValidation(TicketRules parent, TicketRule rule, boolean valid) {
    }

    record TicketNumber(long number, Set<RuleValidation> validations, List<TicketRules> validRules) {

        public void addValidations(TicketRules rules) {
            Set<RuleValidation> newValidationRules = rules.rules().stream()
                    .map(rule -> new RuleValidation(rules, rule, rule.match(number)))
                    .collect(toSet());
            newValidationRules.stream()
                    .filter(RuleValidation::valid)
                    .forEach(validation -> validRules.add(validation.parent()));
            validations.addAll(newValidationRules);
        }

        boolean isValidNumber() {
            return validations().stream().anyMatch(RuleValidation::valid);
        }

        boolean isInvalidNumber() {
            return validations().stream().noneMatch(RuleValidation::valid);
        }

        public List<TicketRules> validRules() {
            return validations().stream()
                    .filter(RuleValidation::valid)
                    .map(RuleValidation::parent)
                    .collect(toList());
        }
    }

    record Ticket(List<TicketNumber> numbers) {

        private final static Ticket NONE = Ticket.of("");

        static Ticket of(String numbersAsString) {
            final List<TicketNumber> numbers = Arrays.stream(numbersAsString.split(","))
                    .map(String::trim)
                    .filter(number -> !number.equals(""))
                    .map(Integer::parseInt)
                    .map(number -> new TicketNumber(number, new HashSet<>(), new ArrayList<>()))
                    .collect(toList());
            return new Ticket(numbers);
        }

        private Ticket addValidations(List<TicketRules> rules) {
            List<TicketNumber> numberWithValidation = numbers().stream()
                    .peek(number -> rules.forEach(number::addValidations))
                    .collect(toList());
            return new Ticket(numberWithValidation);
        }

        public boolean isInvalidTicket() {
            return numbers().stream().anyMatch(TicketNumber::isInvalidNumber);
        }

        public boolean isValidTicket() {
            return numbers().stream().allMatch(TicketNumber::isValidNumber);
        }
    }

    record TicketNotes(Ticket myTicket, List<Ticket> nearbyTickets, List<TicketRules> ticketRules) {

        static TicketNotes of(Ticket myTicket, List<Ticket> nearbyTickets, List<TicketRules> ticketRules) {
            return new TicketNotes(
                    myTicket.addValidations(ticketRules),
                    nearbyTickets.stream().map(rule -> rule.addValidations(ticketRules)).collect(toList()),
                    ticketRules);
        }

    }

    TicketNotes readFile(String filePath) {
        InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));
        Ticket myTicket = Ticket.NONE;
        List<Ticket> nearbyTickets = new ArrayList<>();
        List<TicketRules> ticketRules = new ArrayList<>();
        try (Scanner scanner = new Scanner(stream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("your ticket:")) {
                    myTicket = Ticket.of(scanner.nextLine());
                } else if (line.contains("nearby tickets:")) {
                    while (scanner.hasNextLine()) {
                        nearbyTickets.add(Ticket.of(scanner.nextLine()));
                    }
                } else if (line.matches(".+: [0-9-]+ or [0-9-]+")) {
                    String[] ticketRule = line.split(":");
                    String[] patterns = ticketRule[1].split("or");
                    ticketRules.add(TicketRules.of(ticketRule[0], patterns));
                }
            }
        }
        return TicketNotes.of(myTicket, nearbyTickets, ticketRules);
    }
}
