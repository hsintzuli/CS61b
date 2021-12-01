package hw2;
import org.junit.Assert;
import org.junit.Test;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int N;
    private int numberOfOpenSite;
    private WeightedQuickUnionUF unionUF;

    private int xyTo1D(int r, int c) {
        return r * N+ c;
    }

    private boolean checkIfExceedBound(int r, int c) {
        if (r < 0 || r>= N || c < 0 || c >= N) {
            return true;
        }
        return false;
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
        if (N <= 0) {
            throw new IllegalArgumentException("N must be larger than or equal to 0");
        }
        this.N = N;
        grid = new boolean[N][N];
        numberOfOpenSite = 0;
        unionUF = new WeightedQuickUnionUF(N * N);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (checkIfExceedBound(row, col)) {
            throw new IndexOutOfBoundsException("Beyond the prescribed range");
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        numberOfOpenSite++;

        // connect neighbor grids
        int[][] next = new int[][] { {0, 1}, {0, -1}, {1, 0}, {-1, 0} };
        for (int i = 0; i < next.length; i++) {
            int r = row + next[i][0];
            int c = col + next[i][1];

            if (checkIfExceedBound(r, c) || !isOpen(r,c)) {
                continue;
            }
            unionUF.union(xyTo1D(row, col), xyTo1D(r, c));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (checkIfExceedBound(row, col)) {
            throw new IndexOutOfBoundsException("Exceed the prescribed range");
        }

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (checkIfExceedBound(row, col)) {
            throw new IndexOutOfBoundsException("Exceed the prescribed range");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        if (row == 0) {
            return true;
        }
        int thisIndex = xyTo1D(row, col);
        for (int i = 0; i < N; i++) {
            if (unionUF.connected(i, thisIndex)) {
                return true;
            }
        }
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < N; i++) {
            if (isFull(N - 1, i)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSite;
    }

    @Test
    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation perc = new Percolation(5);
        perc.open(3, 4);
        perc.open(2, 4);
        Assert.assertEquals(perc.isOpen(2, 4), true);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(0, 2);
        Assert.assertEquals(perc.isFull(0, 2), true);
        Assert.assertEquals(perc.isFull(2, 4), false);
        perc.open(1, 2);
        Assert.assertEquals(perc.isFull(2, 4), true);
    }


}
