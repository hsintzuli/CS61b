package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] myBoard;
    private int estimatedDistance = -1;
    private int size;

    /** Constructs a board from an N-by-N array of tiles where
      * tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        size = tiles.length;
        myBoard = new int[size][size];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                myBoard[i][j] = tiles[i][j];
            }
        }

    }

    /** Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (i >= size || i < 0 || j >= size || j < 0) {
            throw new java.lang.IndexOutOfBoundsException("index must be in the range: 0 ~ N");
        }
        return myBoard[i][j];
    }

    /** Returns the board size N
     */
    public int size() {
        return size;
    }

    /** Returns the neighbors of the current board
     * @source: http://joshh.ug/neighbors.html
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int rightNumber = 0;
        int wrongTiles = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rightNumber += 1;
                if (myBoard[i][j] == 0 | myBoard[i][j] == rightNumber) {
                    continue;
                }
                wrongTiles += 1;
            }
        }
        return wrongTiles;
    }

    private int[] truePosition(int number) {
        int[] truePosition = new int[2];
        int i, j;
        i = (number - 1) / size;
        j = number % size ;
        truePosition[0] = i;
        truePosition[1] = (j == 0 ? size - 1 : j - 1);

        return truePosition;
    }

    public int manhattan() {
        int rightNumber = 0;
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rightNumber += 1;
                if (myBoard[i][j] == 0 | myBoard[i][j] == rightNumber ) {
                    continue;
                }
                int[] rightPosition = truePosition(myBoard[i][j]);
                distance += Math.abs(rightPosition[0] - i);
                distance += Math.abs(rightPosition[1] - j);
            }
        }
        return distance;
    }

    /** Estimated distance to goal.
     */
    public int estimatedDistanceToGoal() {
        if (estimatedDistance == -1) {
            estimatedDistance = manhattan();
        }
        return estimatedDistance;
    }

    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass() || ((Board) y).size != size ) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (myBoard[i][j] != ((Board) y).myBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
