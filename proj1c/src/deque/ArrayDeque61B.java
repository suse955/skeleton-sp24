package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] _value;
    private int _size;


    public ArrayDeque61B() {
        _value = (T[]) new Object[100];
        _size = 0;
    }

    private void resize() {
        T[] temp = (T[]) new Object[(int) (_size * 2)];
        System.arraycopy(_value, 0, temp, 0, _size);
        _value = temp;
    }
    @Override
    public void addFirst(T x) {
        if (_size == _value.length) {
            resize();
        }
        for (int i = _size; i > 0; i--) {
            _value[i] = _value[i - 1];
        }
        _value[0] = x;
        _size++;
    }

    @Override
    public void addLast(T x) {
        if (_size == _value.length) {
            resize();
        }
        _value[_size++] = x;
    }

    private boolean isNeedResize() {
        return (_value.length * 1.0 / _size) > 0.25;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = 1; i <= _size; i++) {
            result.add(get(i));
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public T removeFirst() {
        if (_size == 0) {
            return null;
        }
        if (isNeedResize()) resize();
        T first = _value[0];
        for (int i = 0; i < _size; i++) {
            _value[i] = _value[i + 1];
        }
        _size--;
        return first;
    }

    @Override
    public T removeLast() {
        if (_size == 0) return null;
        if (isNeedResize()) resize();
        T resutlt = _value[_size - 1];
        _size--;
        return resutlt;
    }

    @Override
    public T get(int index) {

        return index < 0 || index >= _size ? null : _value[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException
                ("No need to implement getRecursive for proj 1b");    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int pos = 0;

            @Override
            public boolean hasNext() {
                return pos < _size;
            }

            @Override
            public T next() {
                return _value[pos++];
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
