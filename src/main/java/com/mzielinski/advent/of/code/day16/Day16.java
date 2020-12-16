package com.mzielinski.advent.of.code.day16;

import com.mzielinski.advent.of.code.day16.TicketNotesReader.Ticket;
import com.mzielinski.advent.of.code.day16.TicketNotesReader.TicketNotes;

import java.util.List;
import java.util.stream.Collectors;

public class Day16 {

    private final TicketNotesReader ticketNotesReader;

    public Day16() {
        this.ticketNotesReader = new TicketNotesReader();
    }

    public int countInvalidNumberInTickets(String filePath) {
        TicketNotes notes = ticketNotesReader.readFile(filePath);
        List<Ticket> invalidTickets = notes.nearbyTickets()
                .stream()
                .map(ticket -> ticket.findInvalidNumber(notes.ticketRules()))
                .collect(Collectors.toList());
        return invalidTickets.stream()
                .flatMap(a -> a.invalidNumbers().stream())
                .reduce(Integer::sum)
                .orElse(0);
    }
}
