package backend.academy.egfedo.manager;

import backend.academy.FileToMaze;
import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.InputCommand;
import backend.academy.egfedo.io.MazeTile;
import backend.academy.egfedo.io.MazeViewInput;
import backend.academy.egfedo.io.MazeViewOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MazeViewManagerTest {

    @Mock
    MazeViewOutput output;

    @Mock
    MazeViewInput input;

    Maze bigMaze = FileToMaze.fromFile("./src/test/java/backend/academy/big_maze");
    Maze maze = FileToMaze.fromFile("./src/test/java/backend/academy/maze");

    final List<List<MazeTile>> fragmentA = List.of(
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.MUD, MazeTile.FLOOR,
            MazeTile.ICE, MazeTile.FLOOR, MazeTile.FLOOR),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL)

    );

    final List<List<MazeTile>> fragmentB = List.of(
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.MUD, MazeTile.FLOOR, MazeTile.ICE, MazeTile.FLOOR,
            MazeTile.FLOOR, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL)

    );

    final List<List<MazeTile>> fragmentC = List.of(
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.START, MazeTile.MUD_PATH, MazeTile.PATH,
            MazeTile.ICE_PATH, MazeTile.PATH, MazeTile.FLOOR),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.PATH, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.END, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL, MazeTile.FLOOR,
            MazeTile.WALL, MazeTile.FLOOR, MazeTile.WALL),
        List.of(MazeTile.WALL, MazeTile.WALL, MazeTile.WALL, MazeTile.WALL,
            MazeTile.WALL, MazeTile.WALL, MazeTile.WALL)

    );

    List<Vector> path = List.of(
        new Vector(0, 0),
        new Vector(1, 0),
        new Vector(2, 0),
        new Vector(2, 1)
    );

    @Test
    @Timeout(5)
    void run_finishesCorrectly() {
        when(input.next()).thenReturn(
            InputCommand.EXIT
        );

        when(output.getDimensions()).thenReturn(new Vector(3, 3));

        var manager = new MazeViewManager(output, input);
        manager.displayMaze(bigMaze, null);

        InOrder inOrder = Mockito.inOrder(output);

        inOrder.verify(output).displayMaze(
            fragmentA, new Vector(0, 0),
            Map.of(
                Direction.LEFT, false,
                Direction.RIGHT, true,
                Direction.UP, false,
                Direction.DOWN, true
            )
        );

    }

    @Test
    @Timeout(5)
    void run_movingWorks() {
        when(input.next()).thenReturn(
            InputCommand.RIGHT,
            InputCommand.DOWN,
            InputCommand.EXIT
        );

        when(output.getDimensions()).thenReturn(new Vector(3, 3));

        var manager = new MazeViewManager(output, input);
        manager.displayMaze(bigMaze, null);

        InOrder inOrder = Mockito.inOrder(output);

        inOrder.verify(output).displayMaze(
            fragmentA, new Vector(0, 0),
            Map.of(
                Direction.LEFT, false,
                Direction.RIGHT, true,
                Direction.UP, false,
                Direction.DOWN, true
            )
        );
        inOrder.verify(output).displayMaze(
            fragmentB, new Vector(1, 0),
            Map.of(
                Direction.LEFT, true,
                Direction.RIGHT, true,
                Direction.UP, false,
                Direction.DOWN, true
            )
        );
        inOrder.verify(output).displayMaze(
            any(), eq(new Vector(1, 1)),
            eq(Map.of(
                Direction.LEFT, true,
                Direction.RIGHT, true,
                Direction.UP, true,
                Direction.DOWN, true
            ))
        );

    }

    @Test
    @Timeout(5)
    void run_noOutOfBoundsMovement() {
        when(input.next()).thenReturn(
            InputCommand.UP,
            InputCommand.RIGHT,
            InputCommand.DOWN,
            InputCommand.LEFT,
            InputCommand.EXIT
        );

        when(output.getDimensions()).thenReturn(new Vector(16, 8));

        var manager = new MazeViewManager(output, input);
        manager.displayMaze(maze, null);

        InOrder inOrder = Mockito.inOrder(output);

        var expectedMap = Map.of(
            Direction.LEFT, false,
            Direction.RIGHT, false,
            Direction.UP, false,
            Direction.DOWN, false
        );

        var expectedVector = new Vector(0, 0);

        inOrder.verify(output).displayMaze(
            anyList(), eq(expectedVector),
            eq(expectedMap)
        );

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @Timeout(5)
    void run_dispaysPath() {
        when(input.next()).thenReturn(
            InputCommand.EXIT
        );

        when(output.getDimensions()).thenReturn(new Vector(3, 3));

        var manager = new MazeViewManager(output, input);
        manager.displayMaze(bigMaze, path);

        InOrder inOrder = Mockito.inOrder(output);


        inOrder.verify(output).displayMaze(
            eq(fragmentC), any(Vector.class),
            anyMap()
        );

        inOrder.verifyNoMoreInteractions();
    }

}
