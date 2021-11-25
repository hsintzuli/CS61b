package byog.Core;

public class MapParameters {
    public int width;
    public int height;
    public int roomSize;
    public int roomNumbers;

    public MapParameters (int width, int height, int roomSize, int roomNumbers) {
        this.width = width;
        this.height = height;
        this.roomSize = roomSize;
        this.roomNumbers = roomNumbers;
    }

    public static MapParameters DefaultParameters () {
        return new MapParameters(60, 30, 6, 20);
    }
}
