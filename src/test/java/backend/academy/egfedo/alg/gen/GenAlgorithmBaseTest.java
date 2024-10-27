package backend.academy.egfedo.alg.gen;

import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

public abstract class GenAlgorithmBaseTest {


    long BFSCounter(Maze maze) {

        Queue<Vector> queue = new ArrayDeque<>();
        Vector start = new Vector(0, 0);
        queue.add(start);
        Set<Vector> visited = new HashSet<>();
        visited.add(start);

        long counter = 0;

        while (!queue.isEmpty()) {
            var curr = queue.remove();
            counter++;
            for (var dir : Direction.values()) {
                if (maze.getCell(curr).hasDirection(dir)) {
                    var neighbor = curr.add(dir.vec);
                    if (visited.contains(neighbor)) {
                        continue;
                    }
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return counter;
    }

    GenAlgorithm algorithm;

    @BeforeEach
    abstract void setUp();

    @ParameterizedTest
    @CsvSource({"5, 5", "10, 5", "5, 10", "20, 30", "50, 50"})
    void generateMaze_providesMazeWithCorrectDimensions(int width, int height) {

        Maze maze = algorithm.generateMaze(width, height);

        assertThat(maze.rows()).isEqualTo(height);
        assertThat(maze.columns()).isEqualTo(width);

    }

    @ParameterizedTest
    @CsvSource({"5, 5", "10, 5", "20, 20", "50, 50"})
    void generateMaze_mazeDoesntHaveOutOfBounds(int width, int height) {

        Maze maze = algorithm.generateMaze(width, height);

        for (var dir : Direction.values()) {

            Vector inc;
            Vector curr;

            if (dir.vec.x() == 0) {
                inc = new Vector(1, 0);
                curr = dir == Direction.UP ? new Vector(0, 0) : new Vector(0, height-1);
            } else {
                inc = new Vector(0, 1);
                curr = dir == Direction.LEFT ? new Vector(0, 0) : new Vector(width-1, 0);
            }

            while (maze.inBounds(curr)) {
                assertThat(maze.getCell(curr).hasDirection(dir)).isFalse();
                curr = curr.add(inc);
            }

        }

    }

    @ParameterizedTest
    @CsvSource({"5, 5", "10, 5", "20, 20", "50, 50"})
    void generateMaze_shouldAlwaysGeneratePassableMaze(int width, int height) {

        Maze maze = algorithm.generateMaze(width, height);

        // BFS checks that all locations are reachable
        assertThat(BFSCounter(maze)).isEqualTo((long) width * height);

    }
}
