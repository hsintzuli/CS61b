package lab11.graphs;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        Queue<Vertex> fringe = new PriorityQueue<>(new PQ_comparator());
        fringe.offer(new Vertex(s, 0));
        int lastV = s;
        marked[s] = true;
        announce();

        while (!fringe.isEmpty()) {
            Vertex firstVertex = fringe.remove();
            int firstV = firstVertex.value;
            edgeTo[firstV] = lastV;
            marked[firstV] = true;
            distTo[firstV] = distTo[lastV] + 1;
            announce();

            if (firstV == t) {
                targetFound = true;
                return;
            }
            for (int w: maze.adj(firstV)) {
                if (!marked[w]) {
                    Vertex newVertex = new Vertex(w, h(w));
                    fringe.offer(newVertex);
                }
            }
            lastV = firstV;
        }
    }

    private static class Vertex {
        int value;
        int dist;
        public Vertex(int v, int d) {
            value = v;
            dist = d;
        }
    }

    private static class PQ_comparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex a, Vertex b) {
            return a.dist - b.dist;
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

