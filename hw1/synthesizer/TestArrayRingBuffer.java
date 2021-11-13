package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void queueTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        for (int i = 1; i <= 10; i++) {
            arb.enqueue(i);
        }
        arb.dequeue();
        arb.dequeue();
        int expected1 = 3;
        int actual1 = arb.peek();
        assertEquals(expected1, actual1);

        arb.enqueue(12);
        arb.dequeue();
        int expected2 = 4;
        int actual2 = arb.peek();
        assertEquals(expected2, actual2);
    }

    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        for (int i = 1; i <= 10; i++) {
            arb.enqueue(i);
        }
        int expected = 1;
        for (int i : arb) {
            assertEquals(expected, i);
            expected++;
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
