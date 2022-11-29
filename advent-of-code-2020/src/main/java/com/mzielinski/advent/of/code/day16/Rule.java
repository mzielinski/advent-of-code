package com.mzielinski.advent.of.code.day16;

import java.util.Objects;

record Rule(int index, String name, TicketNotesReader.TicketNumber number) {

    public Rule updateIndex(int index) {
        return new Rule(index, name(), number());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(name, rule.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
