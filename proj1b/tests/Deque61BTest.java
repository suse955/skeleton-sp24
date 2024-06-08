import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class Deque61BTest {

    @Test
    public void testAddFirstAndRemoveFirst() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertTrue(deque.isEmpty());
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(3, deque.size());

        assertEquals((Integer) 3, deque.removeFirst());
        assertEquals(2, deque.size());
        assertEquals((Integer) 2, deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals((Integer) 1, deque.removeFirst());
        assertTrue(deque.isEmpty());
        assertNull(deque.removeFirst());
        assertEquals(0, deque.size());
    }

    @Test
    public void testAddLastAndRemoveLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertTrue(deque.isEmpty());
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(3, deque.size());

        assertEquals((Integer) 3, deque.removeLast());
        assertEquals(2, deque.size());
        assertEquals((Integer) 2, deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals((Integer) 1, deque.removeLast());
        assertTrue(deque.isEmpty());
        assertNull(deque.removeLast());
        assertEquals(0, deque.size());
    }

    @Test
    public void testToList() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertTrue(deque.isEmpty());
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(3, deque.size());

        List<Integer> list = deque.toList();
        assertEquals(3, list.size());
        assertEquals((Integer) 1, list.get(0));
        assertEquals((Integer) 2, list.get(1));
        assertEquals((Integer) 3, list.get(2));
    }

    @Test
    public void testGet() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertNull(deque.get(0));
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals((Integer) 1, deque.get(1));
        assertEquals((Integer) 2, deque.get(2));
        assertEquals((Integer) 3, deque.get(3));
        assertNull(deque.get(4));
    }

    @Test
    public void testGetRecursive() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertNull(deque.getRecursive(0));
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals((Integer) 1, deque.getRecursive(0));
        assertEquals((Integer) 2, deque.getRecursive(1));
        assertEquals((Integer) 3, deque.getRecursive(2));
        assertNull(deque.getRecursive(3));
    }

    // Add more test cases for other methods and boundary conditions as needed
}
