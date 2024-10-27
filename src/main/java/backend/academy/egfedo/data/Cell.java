package backend.academy.egfedo.data;

import lombok.Builder;

@Builder(toBuilder = true)
public record Cell(int up, int down, int left, int right) {

    public Cell() {
        this(-1, -1, -1, -1);
    }

    public Cell updateDirection(Direction dir, int state) {
        return switch (dir) {
            case UP -> this.toBuilder().up(state).build();
            case DOWN -> this.toBuilder().down(state).build();
            case LEFT -> this.toBuilder().left(state).build();
            case RIGHT -> this.toBuilder().right(state).build();
        };
    }

    public boolean hasDirection(Direction dir) {
        return switch (dir) {
            case UP -> this.up() != -1;
            case DOWN -> this.down() != -1;
            case LEFT -> this.left() != -1;
            case RIGHT -> this.right() != -1;
        };
    }

    public int getDirection(Direction dir) {
        return switch (dir) {
            case UP -> this.up();
            case DOWN -> this.down();
            case LEFT -> this.left();
            case RIGHT -> this.right();
        };
    }
}
