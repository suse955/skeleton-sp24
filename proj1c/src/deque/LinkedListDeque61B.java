package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LinkedListDeque61B<T> implements deque.Deque61B<T> {

    public class Node {
        public T _item;
        public Node _prev;
        public Node _next;

        public Node() {
            _next = this;
            _prev = this;
            _item = null;
        }
        public Node(T item) {
            _next = this;
            _prev = this;
            _item = item;
        }
    }

    private final Node _sentinel;
    private int _size;

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    public LinkedListDeque61B() {
        _sentinel = new Node();
        _size = 0;
    }
    @Override
    public void addFirst(T x) {
        Node node = new Node(x);
        node._next = _sentinel._next;
        node._prev = _sentinel;
        _sentinel._next._prev = node;
        _sentinel._next = node;
        _size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node last = _sentinel._prev;
        Node node = new Node(x);
        node._next = last._next;
        node._prev = last;
        last._next._prev = node;
        last._next = node;
        _size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (T item : this) {
            result.add(item);
        }
        return result;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return _size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (_size == 0) {
            return null;
        }
        Node node = _sentinel._next;
        _sentinel._next = node._next;
        node._next._prev = _sentinel;
        node._prev = null;
        node._next = null;
        _size--;
        return node._item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (_size == 0) {
            return null;
        }
        Node node = _sentinel._prev;
        _sentinel._prev = node._prev;
        node._prev._next = _sentinel;
        node._prev = null;
        node._next = null;
        _size--;
        return node._item;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= _size) {
            return null;
        }
        Node current = _sentinel._next;
        for (int i = 0; i < index; i++) {
            current = current._next;
        }
        return current._item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = _sentinel._next;


            @Override
            public boolean hasNext() {
                return current != _sentinel;
            }

            @Override
            public T next() {
                T result = current._item;
                current = current._next;
                return result;
            }
        };
    }
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof ArrayDeque61B other)){
            return false;
        }
        if (other.size() != size()) {
            return false;
        }

        for (int i = 0; i < size(); i++) {
            if (!this.get(i).equals(other.get(i)))
                return false;
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            sb.append(get(i));
            if (i != size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
