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
        fringe.add(new Vertex(s, 0 + h(s)));
        marked[s] = true;
        distTo[s] = 0;
        announce();

        while (!fringe.isEmpty()) {
            Vertex firstVertex = fringe.remove();
            int firstV = firstVertex.value;
            marked[firstV] = true;
            announce();

            if (firstV == t) {
                return;
            }

            for (int w: maze.adj(firstV)) {
                int dist = distTo[firstV] + 1;
                if (dist < distTo[w]) {
                    distTo[w] = dist;
                    edgeTo[w] = firstV;
                }
                if (!marked[w]) {
                    Vertex newVertex = new Vertex(w, dist + h(w));
                    fringe.add(newVertex);
                }
            }
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

