package com.mzielinski.advent.of.code.day16;

import com.mzielinski.advent.of.code.utils.ReadFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class TicketNotesReader {

    record TicketRule(int from, int to) {
        public boolean match(int value) {
            return value >= from && value <= to;
        }
    }

    record TicketRules(String name, List<TicketRule> patters) {
        static TicketRules of(String name, String[] patterns) {
            return new TicketRules(name, Arrays.stream(patterns)
                    .map(String::trim)
                    .map(rule -> rule.split("-"))
                    .map(rule -> new TicketRule(Integer.parseInt(rule[0]), Integer.parseInt(rule[1])))
                    .collect(toList()));
        }

        boolean anyRuleMatch(int value) {
            return patters.stream().anyMatch(rule -> rule.match(value));
        }
    }

    record Ticket(List<Integer> numbers, List<Integer> invalidNumbers) {
        static Ticket of(String numbers) {
            return new Ticket(
                    Arrays.stream(numbers.split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(toList()), new ArrayList<>());
        }

        public Ticket findInvalidNumber(List<TicketRules> rules) {
            return this.numbers().stream()
                    .filter(number -> rules.stream().noneMatch(rule -> rule.anyRuleMatch(number)))
                    .reduce(this, Ticket::invalidNumber, ($1, $2) -> null);
        }

        private Ticket invalidNumber(Integer number) {
            invalidNumbers.add(number);
            return this;
        }
    }

    record TicketNotes(Ticket myTicket, List<Ticket> nearbyTickets, List<TicketRules> ticketRules) {

    }

    TicketNotes readFile(String filePath) {
        InputStream stream = requireNonNull(ReadFile.class.getClassLoader().getResourceAsStream(filePath));

        Ticket myTicket = null;
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
        return new TicketNotes(myTicket, nearbyTickets, ticketRules);
    }
}
