import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testAddFirst() {
        int numberOfTests = 10;

        StudentArrayDeque<Integer> stud = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sol = new ArrayDequeSolution<>();
        double randDouble;
        String failureMessage = "\n";

        for (int i = 0; i < numberOfTests; i += 1) {

            randDouble = StdRandom.uniform();
            int randInt = StdRandom.uniform(-100, 100);

            if (randDouble < 0.5) {
                stud.addFirst(randInt);
                sol.addFirst(randInt);
                failureMessage += ("addFirst(" + randInt + ")\n");
            } else {
                stud.addLast(randInt);
                sol.addLast(randInt);
                failureMessage += ("addLast(" + randInt + ")\n");
            }
        }
        while (stud.size() > 0 && sol.size() > 0) {
            randDouble = StdRandom.uniform();
            if (randDouble < 0.5) {
                Integer actual = stud.removeFirst();
                Integer expected = sol.removeFirst();
                failureMessage += ("removeFirst()\n");
                assertEquals(failureMessage, actual, expected);

            } else {
                if (stud.size() == 0 || sol.size() == 0){
                    continue;
                }
                Integer actual = stud.removeLast();
                Integer expected = sol.removeLast();
                failureMessage += ("removeLast()\n");
                assertEquals(failureMessage, actual, expected);
            }
        }
    }

}
