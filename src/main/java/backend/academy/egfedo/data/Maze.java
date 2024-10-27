package backend.academy.egfedo.data;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Maze {

    @Getter
    private final int rows;
    @Getter
    private final int columns;

    private final List<List<Cell>> arr;

    private final Vector lowerBound = new Vector(0, 0);
    private final Vector upperBound;

    private Maze(int rows, int columns, List<List<Cell>> arr) {
        this.rows = rows;
        this.columns = columns;
        this.arr = arr;
        this.upperBound = new Vector(columns - 1, rows - 1);
    }

    public Cell getCell(int x, int y) {
        return arr.get(y).get(x);
    }

    public Cell getCell(Vector pos) {
        return arr.get(pos.y()).get(pos.x());
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public boolean inBounds(Vector pos) {
        return pos.inBounds(lowerBound, upperBound);
    }

    public static class Builder {
        private final int rows;
        private final int columns;

        private final Vector lowerBound = new Vector(0, 0);
        private final Vector upperBound;

        private final List<List<Cell>> arr;

        public Builder(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;
            arr = new ArrayList<>(rows);

            for (int i = 0; i < rows; i++) {
                arr.add(new ArrayList<>());
                for (int j = 0; j < columns; j++) {
                    arr.get(i).add(new Cell());
                }
            }
            upperBound = new Vector(columns - 1, rows - 1);
        }

        private Builder(Maze maze) {
            this.rows = maze.rows;
            this.columns = maze.columns;
            arr = new ArrayList<>(rows);

            for (int i = 0; i < rows; i++) {
                arr.add(new ArrayList<>(maze.arr.get(i)));
            }
            upperBound = new Vector(maze.columns - 1, maze.rows - 1);
        }

        public boolean inBounds(Vector pos) {
            return pos.inBounds(lowerBound, upperBound);
        }

        private void updateCellDirection(Vector pos, Direction dir, int state) {
            var cell = arr.get(pos.y()).get(pos.x());
            arr.get(pos.y()).set(pos.x(), cell.updateDirection(dir, state));
        }

        public void connect(Vector pos, Direction dir, int diff) {
            var neighbor = pos.add(dir.vec);
            if (!inBounds(pos) || !inBounds(neighbor)) {
                throw new IllegalArgumentException("Invalid coordinates or direction");
            }
            updateCellDirection(pos, dir, diff);
            updateCellDirection(neighbor, dir.inverse(), diff);
        }

        public Maze build() {
            return new Maze(rows, columns, arr);
        }

        public Cell getCell(int x, int y) {
            return arr.get(y).get(x);
        }

        public Cell getCell(Vector pos) {
            return arr.get(pos.y()).get(pos.x());
        }
    }


}
