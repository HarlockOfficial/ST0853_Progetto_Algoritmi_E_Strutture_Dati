package it.unicam.cs.asdl2021.totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class BinaryHeapMinPriorityQueueTest {
    @Test
    final void testBinaryHeapMinPriorityQueue() {
        //constructor cannot fail
        new BinaryHeapMinPriorityQueue();
    }

    @Test
    final void testInsert() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        assertThrows(NullPointerException.class, () -> heap.insert(null));
        int finalSize = 4000;
        for (int i = 0; i < finalSize; ++i) {
            heap.insert(new Element(-1, finalSize - i, i));
            assertEquals(finalSize - i, heap.minimum().getPriority());
            assertEquals(new Element(-1, -1, i), heap.minimum());
        }
        heap.insert(new Element(-1, 0, -123));
        assertEquals(0, heap.minimum().getPriority());
        assertEquals(new Element(-1, -1, -123), heap.minimum());
    }

    @Test
    final void testMinimum() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        //noinspection Convert2MethodRef
        assertThrows(NoSuchElementException.class, () -> heap.minimum());
        Random generator = new Random(123456);
        int finalSize = 4000;
        for (int i = 0; i < finalSize; ++i) {
            heap.insert(new Element(-1, finalSize - i, generator.nextInt()));
            assertEquals(finalSize - i, heap.minimum().getPriority());
        }
        heap.insert(new Element(-1, 0, -123));
        assertEquals(0, heap.minimum().getPriority());
        assertEquals(new Element(-1, -1, -123), heap.minimum());
    }

    @Test
    final void testExtractMinimum() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        //noinspection Convert2MethodRef
        assertThrows(NoSuchElementException.class, () -> heap.extractMinimum());
        Random generator = new Random(123456);
        ArrayList<PriorityQueueElement> values = new ArrayList<>();
        int finalSize = 4000;
        for (int i = 0; i < finalSize; ++i) {
            PriorityQueueElement elem = new Element(-1, generator.nextDouble(), generator.nextInt());
            heap.insert(elem);
            values.add(elem);
        }
        values.sort((o1, o2) -> (o1.getPriority() - o2.getPriority()) > 0 ? 1 : (o1.getPriority() - o2.getPriority()) == 0 ? 0 : -1);
        for (PriorityQueueElement value : values) {
            assertEquals(value, heap.extractMinimum());
        }
    }

    @Test
    final void testDecreasePriority() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        assertThrows(NoSuchElementException.class, () -> heap.decreasePriority(null, 0));
        heap.insert(new Element(-1, 1, 23));
        assertThrows(IllegalArgumentException.class, () -> heap.decreasePriority(new Element(0, -1, 23), 2));
        heap.clear();
        Random generator = new Random(123456);
        ArrayList<PriorityQueueElement> values = new ArrayList<>();
        int finalSize = 4000;
        for (int i = 0; i < finalSize; ++i) {
            PriorityQueueElement elem = new Element(-1, generator.nextDouble(), generator.nextInt());
            heap.insert(elem);
            values.add(elem);
        }
        assertThrows(NoSuchElementException.class, () -> heap.decreasePriority(new Element(-1, 12, 23), Double.NEGATIVE_INFINITY));
        assertThrows(NoSuchElementException.class, () -> heap.decreasePriority(new Element(values.size(), 12, 23), Double.NEGATIVE_INFINITY));
        values.sort((o1, o2) -> (o1.getPriority() - o2.getPriority()) > 0 ? 1 : (o1.getPriority() - o2.getPriority()) == 0 ? 0 : -1);
        for (int i = values.size() - 1; i > 0; --i) {
            assertNotEquals(values.get(i), heap.minimum());
            int finalI = i;
            assertThrows(IllegalArgumentException.class, () -> heap.decreasePriority(values.get(finalI), Double.POSITIVE_INFINITY));
            heap.decreasePriority(values.get(i), Double.NEGATIVE_INFINITY);
            assertEquals(values.get(i), heap.extractMinimum());
        }
    }

    @Test
    final void testIsEmpty() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        assertTrue(heap.isEmpty());
        heap.insert(new Element(0, 0.0, new Random(123456).nextInt()));
        assertFalse(heap.isEmpty());
    }

    @Test
    final void testSize() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        Random generator = new Random(123456);
        int finalSize = 4000;
        for (int i = 0; i < finalSize; ++i) {
            assertEquals(i, heap.size());
            heap.insert(new Element(-1, generator.nextDouble(), generator.nextInt()));
        }
        assertEquals(finalSize, heap.size());
    }

    @Test
    final void testClear() {
        BinaryHeapMinPriorityQueue heap = new BinaryHeapMinPriorityQueue();
        Random generator = new Random(123456);
        int finalSize = 4000;
        for (int i = 0; i < finalSize; ++i) {
            heap.insert(new Element(-1, generator.nextDouble(), generator.nextInt()));
        }
        assertEquals(finalSize, heap.size());
        heap.clear();
        assertEquals(0, heap.size());
    }

    private static class Element implements PriorityQueueElement {
        private final int value;
        private int handle;
        private double priority;

        Element(int handle, double priority, int value) {
            this.handle = handle;
            this.priority = priority;
            this.value = value;
        }

        @Override
        public double getPriority() {
            return priority;
        }

        @Override
        public void setPriority(double newPriority) {
            priority = newPriority;
        }

        @Override
        public int getHandle() {
            return handle;
        }

        @Override
        public void setHandle(int newHandle) {
            handle = newHandle;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Element)) return false;
            Element element = (Element) o;
            return value == element.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
