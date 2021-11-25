package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;


public class MapGenerator {
    private static int WIDTH;
    private static int HEIGHT;
    private static int roomSize;
    private static int roomNumbers;
    private static Random RANDOM;

    private World myWorld;

    public MapGenerator(long seed, MapParameters mp) {
        RANDOM = new Random(seed);
        WIDTH = mp.width;
        HEIGHT = mp.height;
        roomSize = mp.roomSize;
        roomNumbers = mp.roomNumbers;
    }

    public TETile[][] initializeWorld () {
        myWorld = new World(WIDTH, HEIGHT);
        return myWorld.world;
    }

    public TETile[][] generateRandomWorld () {
        initializeWorld();
        fillRandomRoom(roomNumbers);
        connectRooms();
        buildWall();
        buildDoor();

        return myWorld.world;
    }

    public void fillRandomRoom(int roomCounts) {
        Room newRoom;
        for (int i = 0; i < roomCounts; i++) {
            do {
                newRoom = genRandomRoom();
            } while(!addRoom(newRoom));
        }
    }

    public boolean addRoom (Room room) {
        if(!myWorld.checkOverlap(room)){
            return false;
        }
        myWorld.addRoom(room);
        return true;
    }

    public Room genRandomRoom() {
        int x, y;
        int width = 2 + RANDOM.nextInt(roomSize - 1);
        int height = 2 + RANDOM.nextInt(roomSize - 1);

        do {
            x = 1 + RANDOM.nextInt(WIDTH - 2);
            y = 1 + RANDOM.nextInt(HEIGHT - 2);
        } while ( (x + width) >= WIDTH - 3 | (y + height) >= HEIGHT - 3);

        return new Room(new Position(x, y), width, height);
    }

    public void connectRooms() {
        List<Room> roomList = new ArrayList<>(myWorld.getRoomList());
        for (int i = 0; i < roomList.size() - 1; i++) {
            Room thisRoom = roomList.get(i);
            Room nextRoom = roomList.get(i+1);
            connectRoom(thisRoom, nextRoom);
        }
    }

    public void connectRoom(Room a, Room b) {
        Position start, end;
        Position.PositionPair positionPair = a.findCandidateDoor(RANDOM, b);

        start = positionPair.pointA;
        end = positionPair.pointB;

        if (start.getX() == end.getX()) {
            Position vStart = start.getY() > end.getY() ? end : start;
            addHallWay(vStart, 1, Math.abs(start.getY() - end.getY()) + 1);
        } else if (start.getY() == end.getY()) {
            addHallWay(start, Math.abs(end.getX() - start.getX()) + 1, 1);
        } else {
            if (start.getX() >= (a.position.getX() + a.width)) {
                Position corner = new Position(end.getX(), start.getY());
                Room horizon = new Room(start, corner.getX() - start.getX() + 1 , 1);
                Position vStart = corner.getY() > end.getY() ? end : corner;
                Room vertical = new Room(vStart, 1, Math.abs(corner.getY() - end.getY()) + 1);
                addLHallWay(horizon, vertical);
            } else {
                Position corner = new Position(start.getX(), end.getY());
                Room horizon = new Room(corner, end.getX() - corner.getX() + 1  , 1);
                Position vStart = corner.getY() > start.getY() ? start : corner;
                Room vertical = new Room(vStart, 1, Math.abs(corner.getY() - start.getY()) + 1);
                addLHallWay(horizon, vertical);

            }
        }
    }

    public void addLHallWay (Room a, Room b) {
        myWorld.addRoom(a);
        myWorld.addRoom(b);
    }

    public void addHallWay (Position pos, int width, int height) {
        Room hallWay = new Room(pos, width, height);
        myWorld.addRoom(hallWay);
    }

    public void buildWall() {
        for (Room room: myWorld.getRoomList()) {
            myWorld.buildWall(room);
        }
    }

    public void buildDoor() {
        Position startPoint = new Position(0, 0);
        Position moveDirection = new Position(0, 0);

        while (true) {
            int randomSide = RANDOM.nextInt(4);
            switch (randomSide) {
                case 0:
                    startPoint = new Position(RANDOM.nextInt(WIDTH), 0);
                    moveDirection = new Position(0, 1);
                    break;
                case 1:
                    startPoint = new Position(RANDOM.nextInt(WIDTH), HEIGHT - 1);
                    moveDirection = new Position(0, -1);
                    break;
                case 2:
                    startPoint = new Position(0, RANDOM.nextInt(HEIGHT));
                    moveDirection = new Position(1, 0);
                    break;
                case 3:
                    startPoint = new Position(WIDTH - 1, RANDOM.nextInt(HEIGHT));
                    moveDirection = new Position(-1, 0);
            }

            if (myWorld.buildDoor(startPoint, moveDirection, WIDTH, HEIGHT)) {
                break;
            }
        }
    }
}
