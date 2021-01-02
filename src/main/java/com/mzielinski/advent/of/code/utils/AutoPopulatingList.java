package com.mzielinski.advent.of.code.utils;

import com.mzielinski.advent.of.code.day14.Bits;

import java.util.*;

public class AutoPopulatingList implements List<Bits> {

    /**
     * The {@link List} that all operations are eventually delegated to.
     */
    private final List<Bits> backingList;

    /**
     * The {@link ElementFactory} to use to create new {@link List} elements
     * on demand.
     */
    private final ElementFactory<Bits> elementFactory;

    /**
     * Creates a new {@code AutoPopulatingList} that is backed by a standard
     * {@link ArrayList} and creates new elements on demand using the supplied {@link ElementFactory}.
     */
    public AutoPopulatingList(ElementFactory<Bits> elementFactory) {
        this.backingList = new ArrayList<>();
        this.elementFactory = elementFactory;
    }

    @Override
    public void add(int index, Bits element) {
        this.backingList.add(index, element);
    }

    @Override
    public boolean add(Bits o) {
        return this.backingList.add(o);
    }

    @Override
    public boolean addAll(Collection<? extends Bits> c) {
        return this.backingList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Bits> c) {
        return this.backingList.addAll(index, c);
    }

    @Override
    public void clear() {
        this.backingList.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.backingList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.backingList.containsAll(c);
    }

    @Override
    public Bits set(int index, Bits element) {
        int backingListSize = this.backingList.size();
        if (index < backingListSize) {
            this.backingList.set(index, element);
        } else {
            for (int x = backingListSize; x < index; x++) {
                this.backingList.add(Bits.NULL);
            }
            element = this.elementFactory.createElement(element);
            this.backingList.add(element);
        }
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return this.backingList.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return this.backingList.isEmpty();
    }

    @Override
    public Iterator<Bits> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.backingList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Bits> listIterator() {
        return this.backingList.listIterator();
    }

    @Override
    public ListIterator<Bits> listIterator(int index) {
        return this.backingList.listIterator(index);
    }

    @Override
    public Bits remove(int index) {
        return this.backingList.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return this.backingList.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.backingList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.backingList.retainAll(c);
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public List<Bits> subList(int fromIndex, int toIndex) {
        return this.backingList.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        return this.backingList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.backingList.toArray(a);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AutoPopulatingList) {
            return this.backingList.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.backingList.hashCode();
    }

    @Override
    public Bits get(int index) {
        return this.backingList.get(index);
    }

    /**
     * Factory interface for creating elements for an player-based access
     * data structure such as a {@link java.util.List}.
     *
     * @param <Bits> the element type
     */
    @FunctionalInterface
    public interface ElementFactory<Bits> {

        /**
         * Create the element for the supplied player.
         *
         * @return the element object
         */
        Bits createElement(Bits element);
    }
}
