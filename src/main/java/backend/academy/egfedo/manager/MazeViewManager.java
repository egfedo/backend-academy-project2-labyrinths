package backend.academy.egfedo.manager;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.InputCommand;
import backend.academy.egfedo.io.MazeTile;
import backend.academy.egfedo.io.MazeViewInput;
import backend.academy.egfedo.io.MazeViewOutput;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MazeViewManager {


    private final MazeViewOutput output;
    private final MazeViewInput input;
    private final Vector dimensions;

    public MazeViewManager(MazeViewOutput output, MazeViewInput input) {
        this.output = Objects.requireNonNull(output);
        this.input = Objects.requireNonNull(input);
        this.dimensions = output.getDimensions();
    }

    public void displayMaze(Maze maze, List<Vector> path) {
        Vector displayOffset = new Vector(0, 0);

        final int maxOffsetX = Math.max(maze.columns() - dimensions.x(), 0);
        final int maxOffsetY = Math.max(maze.rows() - dimensions.y(), 0);
        final Vector maxOffset = new Vector(maxOffsetX, maxOffsetY);
        final Vector zeroOffset = new Vector(0, 0);

        output.displayMaze(
            getMazeFragment(maze, displayOffset, path),
            displayOffset,
            getFogSides(displayOffset, maxOffset));

        while (true) {
            var cmd = input.next();
            var dir = cmdToDir(cmd);

            if (dir != null) {
                var newOffset = displayOffset.add(dir.vec);
                if (newOffset.inBounds(zeroOffset, maxOffset)) {
                    displayOffset = newOffset;
                    var fragment = getMazeFragment(maze, displayOffset, path);
                    output.displayMaze(
                        getMazeFragment(maze, displayOffset, path),
                        displayOffset,
                        getFogSides(displayOffset, maxOffset));
                }
            }
            if (cmd == InputCommand.EXIT) {
                break;
            }
        }
    }

    private Direction cmdToDir(InputCommand cmd) {
        return switch (cmd) {
            case UP -> Direction.UP;
            case DOWN -> Direction.DOWN;
            case LEFT -> Direction.LEFT;
            case RIGHT -> Direction.RIGHT;
            default -> null;
        };
    }

    private Map<Direction, Boolean> getFogSides(Vector displayOffset, Vector maxOffset) {
        EnumMap<Direction, Boolean> result = new EnumMap<>(Direction.class);

        for (var dir : Direction.values()) {
            result.put(dir, Boolean.FALSE);
        }
        if (displayOffset.x() != 0) {
            result.put(Direction.LEFT, Boolean.TRUE);
        }
        if (displayOffset.y() != 0) {
            result.put(Direction.UP, Boolean.TRUE);
        }
        if (displayOffset.y() != maxOffset.y()) {
            result.put(Direction.DOWN, Boolean.TRUE);
        }
        if (displayOffset.x() != maxOffset.x()) {
            result.put(Direction.RIGHT, Boolean.TRUE);
        }
        return result;
    }

    private List<List<MazeTile>> getMazeFragment(Maze maze, Vector offset, List<Vector> path) {

        final int xSize = Math.min(offset.x() + dimensions.x(), maze.columns());
        final int ySize = Math.min(offset.y() + dimensions.y(), maze.rows());

        final Vector corner2 = new Vector(xSize - 1, ySize - 1);

        var fragment = new Fragment(dimensions.x(), dimensions.y(),
            Math.min(dimensions.x(), maze.columns()));


        fragment.setNext(MazeTile.WALL);

        for (int j = offset.x(); j < xSize; j++) {
            fragment.setCellOnPath(maze.getCell(j, offset.y()).up());
            fragment.setNext(MazeTile.WALL);
        }

        for (int i = offset.y(); i < ySize; i++) {

            fragment.setCellOnPath(maze.getCell(offset.x(), i).left());

            for (int j = offset.x(); j < xSize; j++) {
                fragment.setNext(MazeTile.FLOOR);
                fragment.setCellOnPath(maze.getCell(j, i).right());
            }
            fragment.setNext(MazeTile.WALL);
            for (int j = offset.x(); j < xSize; j++) {
                fragment.setCellOnPath(maze.getCell(j, i).down());
                fragment.setNext(MazeTile.WALL);
            }
        }

        if (path != null) {
            fragment.drawPath(path, offset, corner2);
        }

        return fragment.returnFragment();
    }

    private static class Fragment {

        final List<List<MazeTile>> array;

        int x = 0;
        int y = 0;

        int mazeFragX;

        Fragment(int width, int height, int mazeFragX) {
            int fragmentHeight = height * 2 + 1;
            int fragmentWidth = width * 2 + 1;

            this.mazeFragX = mazeFragX * 2 + 1;

            array = new ArrayList<>(fragmentHeight);

            for (int row = 0; row < fragmentHeight; row++) {
                array.add(new ArrayList<>());
                for (int col = 0; col < fragmentWidth; col++) {
                    array.get(row).add(MazeTile.EMPTY);
                }
            }
        }

        public void setNext(MazeTile tile) {
            array.get(y).set(x, tile);
            x++;
            if (x >= mazeFragX) {
                x = 0;
                y++;
            }
        }

        public void setCellOnPath(int path) {
            switch (path) {
                case Config.MUD_PASSAGE_WEIGHT -> array.get(y).set(x, MazeTile.MUD);
                case Config.NORMAL_PASSAGE_WEIGHT -> array.get(y).set(x, MazeTile.FLOOR);
                case Config.ICE_PASSAGE_WEIGHT -> array.get(y).set(x, MazeTile.ICE);
                default -> array.get(y).set(x, MazeTile.WALL);
            }
            x++;
            if (x >= mazeFragX) {
                x = 0;
                y++;
            }
        }

        private void drawPathTile(int first, int second) {
            switch (array.get(second).get(first)) {
                case FLOOR -> array.get(second).set(first, MazeTile.PATH);
                case MUD -> array.get(second).set(first, MazeTile.MUD_PATH);
                case ICE -> array.get(second).set(first, MazeTile.ICE_PATH);
                case PATH, MUD_PATH, ICE_PATH -> { }
                default -> throw new IllegalStateException(
                    "Path can only go through floor or mud "
                        + array.get(second).get(first) + " " + first + " " + second);
            }
        }

        public void drawPath(List<Vector> path, Vector offset, Vector offset2) {

            for (int i = 0; i < path.size(); i++) {
                var node = path.get(i);
                if (node.inBounds(offset, offset2)) {
                    int first = (node.x() - offset.x()) * 2 + 1;
                    int second = (node.y() - offset.y()) * 2 + 1;
                    drawPathTile(first, second);

                    if (i < path.size() - 1) {
                        int xOffset = path.get(i + 1).x() - node.x();
                        int yOffset = path.get(i + 1).y() - node.y();
                        first += xOffset;
                        second += yOffset;
                        drawPathTile(first, second);
                    }
                    if (i != 0) {
                        int xOffset = path.get(i - 1).x() - node.x();
                        int yOffset = path.get(i - 1).y() - node.y();
                        first = (node.x() - offset.x()) * 2 + 1 + xOffset;
                        second = (node.y() - offset.y()) * 2 + 1 + yOffset;
                        drawPathTile(first, second);
                    }

                }

            }
            var node = path.getFirst();
            if (node.inBounds(offset, offset2)) {
                int first = (node.x() - offset.x()) * 2 + 1;
                int second = (node.y() - offset.y()) * 2 + 1;
                array.get(second).set(first, MazeTile.START);
            }
            node = path.getLast();
            if (node.inBounds(offset, offset2)) {
                int first = (node.x() - offset.x()) * 2 + 1;
                int second = (node.y() - offset.y()) * 2 + 1;
                array.get(second).set(first, MazeTile.END);
            }
        }

        List<List<MazeTile>> returnFragment() {
            return List.copyOf(array);
        }
    }

}
