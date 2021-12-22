package lab11.graphs;
import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s = 0;
    private boolean terminate = false;
    private Stack<Integer> visitedV;

    public MazeCycles(Maze m) {
        super(m);
        distTo[s] = 0;
        edgeTo[s] = s;
        visitedV = new Stack<>();

    }

    @Override
    public void solve() {
        // TODO: Your code here!
        detect_cycle(s);

    }

    // Helper methods go here
    private void detect_cycle(int v) {
        marked[v] = true;
        announce();

        if (terminate) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (marked[w] && w != visitedV.peek()) {
                draw_cycle(v, w);
                terminate = true;
                return;
            }
            if (!marked[w]) {
                visitedV.push(v);
                distTo[w] = distTo[v] + 1;
                detect_cycle(w);
                if (terminate) {
                    return;
                }
            }
        }
    }

    private void draw_cycle(int endV, int startV) {
        int thisV = endV;
        do {
            edgeTo[thisV] = visitedV.peek();
            thisV = visitedV.pop();
        } while (thisV != startV);
        edgeTo[startV] = endV;

        announce();
    }
}

