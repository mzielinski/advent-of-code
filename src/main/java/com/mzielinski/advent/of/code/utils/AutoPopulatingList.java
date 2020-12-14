package com.mzielinski.advent.of.code.utils;

import com.mzielinski.advent.of.code.day14.Day14.Bit;

import java.util.*;

public class AutoPopulatingList implements List<Bit> {

    /**
     * The {@link List} that all operations are eventually delegated to.
     */
    private final List<Bit> backingList;

    /**
     * The {@link ElementFactory} to use to create new {@link List} elements
     * on demand.
     */
    private final ElementFactory<Bit> elementFactory;

    /**
     * Creates a new {@code AutoPopulatingList} that is backed by a standard
     * {@link ArrayList} and creates new elements on demand using the supplied {@link ElementFactory}.
     */
    public AutoPopulatingList(ElementFactory<Bit> elementFactory) {
        this.backingList = new ArrayList<>();
        this.elementFactory = elementFactory;
    }

    @Override
    public void add(int index, Bit element) {
        this.backingList.add(index, element);
    }

    @Override
    public boolean add(Bit o) {
        return this.backingList.add(o);
    }

    @Override
    public boolean addAll(Collection<? extends Bit> c) {
        return this.backingList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Bit> c) {
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
    public Bit set(int index, Bit element) {
        int backingListSize = this.backingList.size();
        if (index < backingListSize) {
            this.backingList.set(index, element);
        } else {
            for (int x = backingListSize; x < index; x++) {
                this.backingList.add(Bit.NULL);
            }
            element = this.elementFactory.createElement(index, element);
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
    public Iterator<Bit> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.backingList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Bit> listIterator() {
        return this.backingList.listIterator();
    }

    @Override
    public ListIterator<Bit> listIterator(int index) {
        return this.backingList.listIterator(index);
    }

    @Override
    public Bit remove(int index) {
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
    public List<Bit> subList(int fromIndex, int toIndex) {
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
    public Bit get(int index) {
        return this.backingList.get(index);
    }

    /**
     * Factory interface for creating elements for an index-based access
     * data structure such as a {@link java.util.List}.
     *
     * @param <Bit> the element type
     */
    @FunctionalInterface
    public interface ElementFactory<Bit> {

        /**
         * Create the element for the supplied index.
         *
         * @return the element object
         */
        Bit createElement(int index, Bit element);
    }
}
