package com.mzielinski.advent.of.code.day16;

import com.mzielinski.advent.of.code.day16.TicketNotesReader.Ticket;
import com.mzielinski.advent.of.code.day16.TicketNotesReader.TicketNotes;
import com.mzielinski.advent.of.code.day16.TicketNotesReader.TicketNumber;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Day16 {

    private final TicketNotesReader ticketNotesReader;
    private final TicketConverter ticketConverter;

    public Day16() {
        this.ticketNotesReader = new TicketNotesReader();
        this.ticketConverter = new TicketConverter();
    }

    public long countInvalidTickets(String filePath) {
        TicketNotes notes = ticketNotesReader.readFile(filePath);
        return notes.nearbyTickets().stream()
                .filter(Ticket::isInvalidTicket)
                .flatMap(ticket -> ticket.numbers().stream().filter(TicketNumber::isInvalidNumber))
                .map(TicketNumber::number)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public long multiplyValuesFromMyTickets(String filePath, Pattern pattern) {
        TicketNotes notes = ticketNotesReader.readFile(filePath);
        List<Ticket> validTickets = notes.nearbyTickets().stream()
                .filter(Ticket::isValidTicket)
                .collect(toList());
        Map<String, Integer> ruleNames = ticketConverter.convertToRuleNames(validTickets);
        return multiplyValuesFromMyTickets(notes.myTicket().numbers(), pattern, ruleNames);
    }

    private Long multiplyValuesFromMyTickets(List<TicketNumber> numbers, Pattern pattern, Map<String, Integer> ruleNames) {
        return ruleNames.entrySet().stream()
                .filter(entry -> pattern.matcher(entry.getKey()).matches())
                .map(Map.Entry::getValue)
                .sorted(Integer::compareTo)
                .map(numbers::get)
                .map(TicketNumber::number)
                .reduce(1L, (partialResult, number) -> partialResult * number, ($1, $2) -> null);
    }
}
