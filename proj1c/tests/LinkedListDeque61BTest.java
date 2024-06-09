
import deque.LinkedListDeque61B;

import java.util.Iterator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
public class LinkedListDeque61BTest {

    @Test
    public void testIsEmpty() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        assertTrue(deque.isEmpty(), "Deque should be empty initially");

        deque.addFirst(1);
        assertFalse(deque.isEmpty(), "Deque should not be empty after adding an element");

        deque.removeFirst();
        assertTrue(deque.isEmpty(), "Deque should be empty after removing the only element");
    }

    @Test
    public void testSize() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        assertEquals(0, deque.size(), "Initial size should be 0");

        deque.addFirst(1);
        assertEquals(1, deque.size(), "Size should be 1 after adding an element");

        deque.addLast(2);
        assertEquals(2, deque.size(), "Size should be 2 after adding another element");

        deque.removeFirst();
        assertEquals(1, deque.size(), "Size should be 1 after removing an element");

        deque.removeLast();
        assertEquals(0, deque.size(), "Size should be 0 after removing all elements");
    }

    @Test
    public void testAddFirstAndRemoveFirst() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);

        assertEquals(3, deque.removeFirst(), "First element should be 3");
        assertEquals(2, deque.removeFirst(), "First element should be 2");
        assertEquals(1, deque.removeFirst(), "First element should be 1");
        assertNull(deque.removeFirst(), "Should return null when removing from an empty deque");
    }

    @Test
    public void testAddLastAndRemoveLast() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertEquals(3, deque.removeLast(), "Last element should be 3");
        assertEquals(2, deque.removeLast(), "Last element should be 2");
        assertEquals(1, deque.removeLast(), "Last element should be 1");
        assertNull(deque.removeLast(), "Should return null when removing from an empty deque");
    }

    @Test
    public void testAddFirstAndRemoveLast() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);

        assertEquals(1, deque.removeLast(), "Last element should be 1");
        assertEquals(2, deque.removeLast(), "Last element should be 2");
        assertEquals(3, deque.removeLast(), "Last element should be 3");
        assertNull(deque.removeLast(), "Should return null when removing from an empty deque");
    }

    @Test
    public void testAddLastAndRemoveFirst() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertEquals(1, deque.removeFirst(), "First element should be 1");
        assertEquals(2, deque.removeFirst(), "First element should be 2");
        assertEquals(3, deque.removeFirst(), "First element should be 3");
        assertNull(deque.removeFirst(), "Should return null when removing from an empty deque");
    }

    @Test
    public void testToList() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        List<Integer> list = deque.toList();
        assertEquals(3, list.size(), "List size should be 3");
        assertEquals(1, list.get(0), "First element should be 1");
        assertEquals(2, list.get(1), "Second element should be 2");
        assertEquals(3, list.get(2), "Third element should be 3");
    }

    @Test
    public void testGet() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        assertEquals(1, deque.get(0), "Element at index 0 should be 1");
        assertEquals(2, deque.get(1), "Element at index 1 should be 2");
        assertEquals(3, deque.get(2), "Element at index 2 should be 3");
        assertNull(deque.get(3), "Should return null for out of bounds index");
        assertNull(deque.get(-1), "Should return null for negative index");
    }

    @Test
    public void testIterator() {
        LinkedListDeque61B<Integer> deque = new LinkedListDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        Iterator<Integer> it = deque.iterator();
        assertTrue(it.hasNext(), "Iterator should have next element");
        assertEquals(1, it.next(), "First element should be 1");
        assertTrue(it.hasNext(), "Iterator should have next element");
        assertEquals(2, it.next(), "Second element should be 2");
        assertTrue(it.hasNext(), "Iterator should have next element");
        assertEquals(3, it.next(), "Third element should be 3");
        assertFalse(it.hasNext(), "Iterator should not have more elements");
    }
}
