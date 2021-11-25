package byog.Core;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static class PositionPair {
        public Position pointA;
        public Position pointB;

        public PositionPair (Position a, Position b) {
            pointA = a;
            pointB = b;
        }
    }
}
