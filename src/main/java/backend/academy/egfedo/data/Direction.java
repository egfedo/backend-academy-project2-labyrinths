package backend.academy.egfedo.data;

public enum Direction {
    UP(new Vector(0, -1)),
    DOWN(new Vector(0, 1)),
    LEFT(new Vector(-1, 0)),
    RIGHT(new Vector(1, 0));

    public final Vector vec;

    Direction(Vector dir) {
        this.vec = dir;
    }

    public Direction inverse() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }
}
