package lab11.graphs;
import java.util.Queue;
import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int v) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> fringe = new LinkedList<>();
        fringe.add(v);
        marked[v] = true;
        announce();

        while (!fringe.isEmpty()) {
            int nextV = fringe.remove();
            for (int w: maze.adj(nextV)) {
                if (!marked[w]) {
                    fringe.add(w);
                    edgeTo[w] = nextV;
                    marked[w] = true;
                    distTo[w] = distTo[nextV] + 1;
                    announce();
                }
                if (w == t) {
                    targetFound = true;
                    return;
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs(s);
    }
}

