package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class Solver {
    private MinPQ<Search_Node> moveSeq;
    private HashSet<WorldState> closedSet = new HashSet<>();
    private Search_Node solutionNode;

    private static class Search_Node {
        private WorldState state;
        private int startToThis;
        private int thisToEnd;
        private Search_Node previousSearchNode;

        public Search_Node(WorldState state, int costPath, Search_Node prev) {
            this.state = state;
            startToThis = costPath;
            thisToEnd = state.estimatedDistanceToGoal();
            previousSearchNode = prev;
        }

        private int path() {
            return startToThis + thisToEnd;
        }

    }

    private static class Node_Comparator implements Comparator<Search_Node> {
        @Override
        public int compare(Search_Node a, Search_Node b) {
            int value = a.path() - b.path();
            if (value > 0) {
                return 1;
            } else if (value < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /** Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists. */
    public Solver(WorldState initial) {
        int count = 0;
        moveSeq = new MinPQ<>(new Node_Comparator());
        moveSeq.insert(new Search_Node(initial, 0, null));

        while (!moveSeq.isEmpty()) {
            Search_Node s = moveSeq.min();
            if (s.state.isGoal()) {
                solutionNode = s;
                return;
            }
            moveSeq.delMin();
            closedSet.add(s.state);
            for (WorldState state : s.state.neighbors()) {
                if (s.previousSearchNode != null && state.equals(s.previousSearchNode.state)) {
                    continue;
                }
                moveSeq.insert(new Search_Node(state, s.startToThis + 1, s));
            }
        }
    }


    /** Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState. */
    public int moves() {
        return solutionNode.startToThis ;
    }
    /** Returns a sequence of WorldStates from the initial WorldState
     to the solution. */
    public Iterable<WorldState> solution() {
        Stack<WorldState> solutionSet = new Stack<>();
        Search_Node prevNode = solutionNode;
        while (prevNode != null) {
            solutionSet.add(prevNode.state);
            prevNode = prevNode.previousSearchNode;
        }
        LinkedList<WorldState> solutionList = new LinkedList<>();
        while (!solutionSet.isEmpty()) {
            solutionList.add(solutionSet.pop());
        }
        return solutionList;
    }
}
