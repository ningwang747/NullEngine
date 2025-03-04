package nullengine.util;

public enum Facing {
    NORTH(0, 1, 0, 0, -1),
    SOUTH(1, 0, 0, 0, 1),
    EAST(2, 3, 1, 0, 0),
    WEST(3, 2, -1, 0, 0),
    UP(4, 5, 0, 1, 0),
    DOWN(5, 4, 0, -1, 0);

    public final int index;
    public final int opposite;
    public final int offsetX, offsetY, offsetZ;

    Facing(int index, int opposite, int offsetX, int offsetY, int offsetZ) {
        this.index = index;
        this.opposite = opposite;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public Facing opposite() {
        return values()[opposite];
    }

    public static Facing valueOf(int index) {
        switch (index) {
            case 0:
                return NORTH;
            case 1:
                return SOUTH;
            case 2:
                return EAST;
            case 3:
                return WEST;
            case 4:
                return UP;
            case 5:
                return DOWN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
