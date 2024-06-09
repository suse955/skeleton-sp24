package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private final Comparator<T> _c;

    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        _c = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        T max = this.get(0);
        for (int i = 1; i < size(); i++) {
            if (_c.compare(max, get(i)) < 0) {
                max = get(i);
            }
        }
        return max;
    }

    public T max(Comparator<T> comparator) {
        if (size() == 0) {
            return null;
        }
        T max = this.get(0);
        for (int i = 1; i < size(); i++) {
            if (comparator.compare(max, get(i)) < 0) {
                max = get(i);
            }
        }
        return max;
    }
}