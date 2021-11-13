package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(8);

    private static int startPosX = 0;
    private static int hexagonSize;

    public static void initialize(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    public static void addHexagon(TETile[][] world, int startPosX, int startPosY, int size) {
        TETile style = randomTile();
        int[][] hexagon = getHexagonMatrix(size);
        for (int x = 0; x < hexagon.length; x += 1) {
            for (int y = hexagon[x][0]; y <= hexagon[x][1]; y += 1) {
                world[startPosX+ y][startPosY + x] = style;
            }
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.NOTHING;
            default: return Tileset.NOTHING;
        }
    }

    public static int[][] getHexagonMatrix(int n) {
        int width = calWidth(n);
        int[][] quantity = new int[n*2][2];
        for (int i = 0; i < n; i++) {
            quantity[i][0] = width - 2*n - i + 1;
            quantity[i][1] = width - (n - i);

            quantity[2*n - i - 1][0] = width - 2*n - i + 1;
            quantity[2*n - i - 1][1] = width - (n - i);
        }
        return quantity;
    }

    public static int calWidth(int n) {
        int width = n;
        for (int i = 1; i < n; i++) {
            width += 2;
        }
        return width;
    }

    @Test
    public void test() {
        HexWorld world = new HexWorld();
        int expected2 = 4;
        assertEquals( expected2, world.calWidth(2));

        int expected3 = 7;
        assertEquals( expected3, world.calWidth(3));

        int expected4 = 10;
        assertEquals( expected4, world.calWidth(4));

    }

    @Test
    public void testQuantity() {
        HexWorld world = new HexWorld();
        int[][] expected2 = {{1, 2}, {0, 3}, {0, 3}, {1, 2}};
        assertArrayEquals(expected2, world.getHexagonMatrix(2));

        int[][] expected4 = {{3, 6}, {2, 7}, {1, 8}, {0, 9}, {0, 9}, {1, 8}, {2, 7}, {3, 6}};
        assertArrayEquals(expected4, world.getHexagonMatrix(4));

    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        hexagonSize = 3;
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initialize(world);

        addHexagon(world, 20, 20, 3);

        ter.renderFrame(world);
    }


}
