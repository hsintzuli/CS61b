package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    public TETile[][] world;
    private ArrayList<Room> roomList;


    public World(int width, int height) {
        world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        roomList = new ArrayList<>();
    }

    public boolean checkOverlap(Room r) {
        int pointX = r.position.getX();
        int pointY = r.position.getY();
        for (int i = -1; i <= r.width + 1; i++) {
            for (int j = -1; j <= r.height + 1; j++) {
                if(world[pointX +i ][pointY+j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addRoom(Room r) {
        int pointX = r.position.getX();
        int pointY = r.position.getY();
        for (int i = 0; i < r.width; i++) {
            for (int j = 0; j < r.height; j++) {
                world[pointX+i][pointY+j] = Tileset.FLOOR;
            }
        }
        roomList.add(r);
    }

    public List<Room> getRoomList() {
        Collections.sort(roomList);
        return roomList;
    }


    public void buildWall (Room r) {
        int pointX = r.position.getX();
        int pointY = r.position.getY();
        for (int i = -1; i < r.width + 1; i++) {
            for (int j = -1; j < r.height + 1; j++) {
                if(world[pointX+i][pointY+j] != Tileset.FLOOR && world[pointX+i][pointY+j] != Tileset.WATER) {
                    world[pointX+i][pointY+j] = Tileset.WALL;
                }
            }
        }
    }

    public boolean buildDoor (Position startPoint, Position moveDirection, int wBound, int hBound) {
        int x = startPoint.getX();
        int y = startPoint.getY();
        if (moveDirection.getX() == 0) {
            while ( y >= 0 && y < hBound) {
                if (world[x][y] == Tileset.WALL) {
                    return buildDoor (x, y, wBound, hBound);
                }
                y += moveDirection.getY();
            }
        } else {
            while ( x >= 0 && x < wBound) {
                if (world[x][y] == Tileset.WALL) {
                    return buildDoor (x, y, wBound, hBound);
                }
                x += moveDirection.getX();
            }
        }
        return false;
    }

    private boolean buildDoor (int x, int y, int  wBound, int hBound) {
        int floorCount = 0;
        for (int i = x - 1; i < x + 1; i++) {
            for (int j = y - 1; j < y + 1; j++) {
                if (i < 0 || i >= wBound || j < 0 || j >= hBound) {
                    continue;
                }
                if (world[i][j] == Tileset.FLOOR) {
                    floorCount++;
                }
            }
        }
        if (floorCount >= 2) {
            world[x][y] = Tileset.LOCKED_DOOR;
            return true;
        }

        return  false;
    }
}
