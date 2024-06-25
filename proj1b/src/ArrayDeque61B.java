import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    /**元素数组 */
    private T[] _value;
    /**头指针，头元素所在的素组下标 */
    private int _front;
    /**尾指针，尾元素所在下标的后羿位置 */
    private int _rear;
    /**队列大小 */
    private int _size;
    /**构造函数，队列从数组的1/3处开始存储*/
    public ArrayDeque61B() {
        _value = (T[]) new Object[100];
        _front = _value.length / 3;
        _rear = _front;
        _size = 0;
    }

    /**对数组大小进行重置，一般由三种情况：front到0时；rear到数组最大下标时；size/value.length<1/4时。
     * 将现有的元素移动至新数组的1/3处，front和rear也要更新。
     * 新数组的大小不能设置为 size * 2，当size等于1时，会出现bug。  */
    private void resize() {
        T[] temp = (T[]) new Object[(int) (size() * 3)];
        System.arraycopy(_value, _front, temp, temp.length / 3, size());
        _front = temp.length / 3;
        _rear = _front + size();
        _value = temp;
    }

    /** 在头添加元素，直接利用front指针添加，复杂度 O(1),特殊情况是，当front到头时，需要重新设置value数组
     * 之前是判断 front == 0，后来把其放在isNeedResize() return (size() * 1.0 / _value.length) < 0.25 || _rear == _value.length || _front == 0 函数中，出现bug
     * 当 size 等于 0 时（初始情况），value被置为一个 0 长度的数组，也就是只是一个变量，T[] temp = (T[]) new Object[(int) (_size * 3)];
     * 解决办法: 在isNeedResize()函数中加上 size！=0 的特殊情况 */
    @Override
    public void addFirst(T x) {
        if (isNeedResize()) {
            resize();
        }
        _size++;
        _value[--_front] = x;
    }

    @Override
    public void addLast(T x) {
        if (isNeedResize()) {
            resize();
        }
        _size++;
        _value[_rear++] = x;
    }

    private boolean isNeedResize() {
        System.out.println(size() * 1.0 / _value.length);
        return ((size() * 1.0 / _value.length) < 0.25 || _rear == _value.length || _front == 0) && size() != 0;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            result.add(get(i));
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (isNeedResize()) resize();
        _size--;
        return _value[_front++];
    }

    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        if (isNeedResize()) resize();
        _size--;
        return _value[--_rear];
    }

    @Override
    public T get(int index) {

        return index < 0 || index > size()-1 ? null : _value[_front + index ];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException
                ("No need to implement getRecursive for proj 1b");
    }
}
