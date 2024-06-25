



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node {
        public T _item;
        public Node _prev;
        public Node _next;
        public Node(T item){
            _item = item;
            _prev = this;
            _next = this;
        }
    }
    private int size;
    private final Node sentinel;
//    public LinkedListDeque61B(T x) {
//        sentinel = new Node(null);
//        sentinel._next = new Node(x);
//        size = 1;
//    }

    public LinkedListDeque61B() {
        sentinel = new Node(null);
        size = 0;
    }


    @Override
    public void addFirst(T x) {
        Node node = new Node(x);
        node._next = sentinel._next;
        node._prev = sentinel;
        sentinel._next._prev = node;
        sentinel._next = node;
        size += 1;
    }

    public T getFirst() {
        if (size == 0) {
            return null;
        }
        return sentinel._next._item;
    }

    @Override
    public void addLast(T x) {
        Node node = new Node(x);
        sentinel._prev._next = node;
        node._prev = sentinel._prev;
        node._next = sentinel;
        sentinel._prev = node;
        size += 1;

    }

    public T getLast() {
        return sentinel._prev._item;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = 1; i <=size; i++) {
            result.add(get(i));
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node res = sentinel._next;
        res._next._prev = sentinel;
        sentinel._next = res._next;
        res._next = null;
        res._prev = null;
        size--;
        return res._item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node res = sentinel._prev;
        sentinel._prev = res._prev;
        res._prev._next = sentinel;
        res._next = null;
        res._prev = null;
        size--;
        return res._item;
    }


    @Override
    public T get(int index) {
        if (index > size) {
            return null;
        }
        Node current = sentinel;
        for (int i = 0; i < index; i++) {
            current = current._next;
        }

        return current._item;
    }
//    @Override
    public T getRecursive(int index) {
        return getRec(sentinel._next, index);
    }

    private T getRec(Node head, int index) {
        if (head == null || index == 0) {
            return head == null ? null : head._item;
        }
        return getRec(head._next, index-1);
    }

}
