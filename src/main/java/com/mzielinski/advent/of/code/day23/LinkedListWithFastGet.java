package com.mzielinski.advent.of.code.day23;

import java.util.*;

// get, indexOf, contain are slow in classic LinkedList
class LinkedListWithFastGet<E> {

    // implementation taken from linkedList
    static class Node<E> {

        E item;

        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return "item=" + item + ", next=" + next.item + ", prev=" + prev.item;
        }
    }

    private final Map<E, Node<E>> linkedList = new LinkedHashMap<>();

    public LinkedListWithFastGet(List<E> list) {
        for (E value : list) {
            linkedList.put(value, new Node<>(null, value, null));
        }

        // fill next and previous
        int lastElement = list.size() - 1;

        for (int index = 0; index < list.size(); index++) {
            E cup = list.get(index);

            Node<E> node = linkedList.get(cup);

            int previousIndex = index == 0 ? lastElement : index - 1;
            node.prev = linkedList.get(list.get(previousIndex));

            int nextIndex = index == lastElement ? 0 : index + 1;
            node.next = linkedList.get(list.get(nextIndex));
        }
    }

    public void moveAfter(E destination, List<E> sequence) {
        E firstSequence = sequence.get(0);
        E lastOfSequence = sequence.get(sequence.size() - 1);
        E afterLastSequence = linkedList.get(lastOfSequence).next.item;
        E currentCup = linkedList.get(firstSequence).prev.item;
        E afterDestination =  linkedList.get(destination).next.item;

        linkedList.get(lastOfSequence).next = linkedList.get(afterDestination);
        linkedList.get(afterDestination).prev = linkedList.get(lastOfSequence);
        linkedList.get(destination).next = linkedList.get(firstSequence);
        linkedList.get(firstSequence).prev = linkedList.get(destination);
        linkedList.get(currentCup).next = linkedList.get(afterLastSequence);
        linkedList.get(afterLastSequence).prev = linkedList.get(currentCup);
    }

    public List<E> getAfter(E key) {
        return getAfter(key, linkedList.size());
    }

    public List<E> getAfter(E key, int limit) {
        Node<E> current = linkedList.get(key).next;
        List<E> result = new ArrayList<>();
        while (!Objects.equals(current.item, key) && result.size() < limit) {
            result.add(current.item);
            current = current.next;
        }
        return result;
    }

    public int size() {
        return linkedList.size();
    }

    List<E> keySet() {
        return new ArrayList<>(linkedList.keySet());
    }

    Node<E> get(E key) {
        return linkedList.get(key);
    }
}
