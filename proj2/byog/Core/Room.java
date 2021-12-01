package byog.Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Room implements Comparable<Room>{
    public Position position;
    public int width;
    public int height;

    public Room (Position pos, int w, int h) {
        position = pos;
        width = w;
        height = h;
    }
    @Override
    public int compareTo(Room b)
    {
        if (position.getX() == b.position.getX()) {
            return position.getY() - b.position.getY();
        }

        return position.getX() - b.position.getX();
    }

    public Position.PositionPair findCandidateDoor(Random rand, Room b) {
        boolean BUpThanA = b.position.getY() > this.position.getY();
        int randNumber;
        Position start, end;
        Position leftPoint = this.position;
        Position rightPoint = b.position;

        List<Integer> candidateList = commonXAxis(this, b);
        if (!candidateList.isEmpty()) {
            randNumber = candidateList.get(rand.nextInt(candidateList.size()));
            start = new Position(randNumber, BUpThanA ? leftPoint.getY()  + this.height: leftPoint.getY() - 1);
            end = new Position(randNumber, BUpThanA ? rightPoint.getY() - 1: rightPoint.getY()  + b.height);
            return new Position.PositionPair(start, end);
        }

        candidateList = commonYAxis(this, b);
        if (!candidateList.isEmpty()) {
            randNumber = candidateList.get(rand.nextInt(candidateList.size()));
            start = new Position(leftPoint.getX() + this.width, randNumber);
            end = new Position(rightPoint.getX() - 1,  randNumber);
            return new Position.PositionPair(start, end);
        }

        if (rand.nextInt(2) == 1) {
            randNumber = rand.nextInt(this.height);
            start = new Position(leftPoint.getX() + this.width, leftPoint.getY() + randNumber);
            randNumber = rand.nextInt(b.width);
            end = new Position(rightPoint.getX() + randNumber,
                    BUpThanA ? rightPoint.getY() -1 : rightPoint.getY() + b.height);
            return new Position.PositionPair(start, end);
        }
        randNumber = rand.nextInt(this.width);
        start = new Position(leftPoint.getX() + randNumber,
                BUpThanA ? leftPoint.getY() + this.height : leftPoint.getY() - 1);
        randNumber = rand.nextInt(b.height);
        end = new Position(rightPoint.getX() - 1, rightPoint.getY() + randNumber);
        return new Position.PositionPair(start, end);

    }

    private List<Integer> commonXAxis(Room a, Room b) {
        ArrayList<Integer> commonList = new ArrayList<>();
        HashSet<Integer> setA = new HashSet<>();
        HashSet<Integer> setB = new HashSet<>();

        int posA = a.position.getX();
        int posB = b.position.getX();
        for (int i = posA; i < posA+a.width; i++) {
            setA.add(i);
        }
        for (int i = posB; i < posB+b.width; i++) {
            setB.add(i);
        }

        for (int element: setA) {
            if (setB.contains(element)) {
                commonList.add(element);
            }
        }
        return commonList;
    }

    private List<Integer> commonYAxis(Room a, Room b) {
        ArrayList<Integer> commonList = new ArrayList<>();
        HashSet<Integer> setA = new HashSet<>();
        HashSet<Integer> setB = new HashSet<>();

        int posA = a.position.getY();
        int posB = b.position.getY();
        for (int i = posA; i < posA+a.height; i++) {
            setA.add(i);
        }
        for (int i = posB; i < posB+b.height; i++) {
            setB.add(i);
        }
        for (int element: setA) {
            if (setB.contains(element)) {
                commonList.add(element);
            }
        }

        return commonList;
    }
}
