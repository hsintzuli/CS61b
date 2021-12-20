package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class Solver {
    MinPQ<searchNode> moveSeq;
    HashSet<WorldState> closedSet = new HashSet<>();
    searchNode solutionNode;

    private static class searchNode {
        private WorldState state;
        private int startToThis;
        private int thisToEnd;
        private searchNode previousSearchNode;

        public searchNode(WorldState state, int costPath, searchNode prev) {
            this.state = state;
            startToThis = costPath;
            thisToEnd = state.estimatedDistanceToGoal();
            previousSearchNode = prev;
        }

        private int path() {
            return startToThis + thisToEnd;
        }

    }

    private static class pqComparator implements Comparator<searchNode>{
        @Override
        public int compare(searchNode a, searchNode b){
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
        moveSeq = new MinPQ<>(new pqComparator());
        moveSeq.insert(new searchNode(initial, 0, null));

        while (!moveSeq.isEmpty()) {
            searchNode s = moveSeq.min();
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
                moveSeq.insert(new searchNode(state, s.startToThis + 1, s));
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
        searchNode prevNode = solutionNode;
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
