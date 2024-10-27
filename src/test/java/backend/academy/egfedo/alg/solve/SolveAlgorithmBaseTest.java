package backend.academy.egfedo.alg.solve;

import backend.academy.FileToMaze;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class SolveAlgorithmBaseTest {

    SolveAlgorithm algorithm;

    @BeforeEach
    abstract void setUp();

    @ParameterizedTest
    @CsvSource({"./src/test/java/backend/academy/maze, 3, 3",
        "./src/test/java/backend/academy/maze, 5, 5"})
    void solve_providesACorrectPathIfStartEqualsEnd(String filePath,
        int startX, int startY) {
        Maze maze = FileToMaze.fromFile(filePath);

        Vector start = new Vector(startX, startY);

        var path = algorithm.solve(maze, start, start);

        assertThat(path.size()).isEqualTo(2);
        assertThat(path.getFirst()).isEqualTo(start);
        assertThat(path.getLast()).isEqualTo(start);

    }


    private static Stream<Arguments> checkCorrectPathProvider() {
        return Stream.of(
            Arguments.of(FileToMaze.fromFile("./src/test/java/backend/academy/maze"),
                new Vector(0, 0),
                new Vector(4, 0),
                Arrays.asList(
                    new Vector(0, 0),
                    new Vector(0, 1),
                    new Vector(0, 2),
                    new Vector(0, 3),
                    new Vector(1, 3),
                    new Vector(1, 4),
                    new Vector(2, 4),
                    new Vector(2, 3),
                    new Vector(3, 3),
                    new Vector(3, 2),
                    new Vector(3, 1),
                    new Vector(4, 1),
                    new Vector(4, 0))
                ),
            Arguments.of(FileToMaze.fromFile("./src/test/java/backend/academy/maze"),
                new Vector(0, 0),
                new Vector(0, 4),
                Arrays.asList(
                    new Vector(0, 0),
                    new Vector(0, 1),
                    new Vector(0, 2),
                    new Vector(0, 3),
                    new Vector(0, 4)
                )
            ),
            Arguments.of(FileToMaze.fromFile("./src/test/java/backend/academy/maze"),
                new Vector(0, 0),
                new Vector(4, 4),
                Arrays.asList(
                    new Vector(0, 0),
                    new Vector(0, 1),
                    new Vector(0, 2),
                    new Vector(0, 3),
                    new Vector(1, 3),
                    new Vector(1, 4),
                    new Vector(2, 4),
                    new Vector(3, 4),
                    new Vector(4, 4)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("checkCorrectPathProvider")
    void solve_providesACorrectPath(Maze maze, Vector start, Vector end,
        List<Vector> correctPath) {

        var path = algorithm.solve(maze, start, end);

        assertThat(path).hasSameSizeAs(correctPath);
        assertThat(path.getFirst()).isEqualTo(start);
        assertThat(path.getLast()).isEqualTo(end);
        assertThat(path).isEqualTo(correctPath);

    }

    private static Stream<Arguments> shortestPathTestProvider() {
        return Stream.of(
            Arguments.of(FileToMaze.fromFile("./src/test/java/backend/academy/mud_maze"),
                new Vector(0, 1),
                new Vector(4, 1),
                Arrays.asList(
                    new Vector(0, 1),
                    new Vector(1, 1),
                    new Vector(1, 2),
                    new Vector(2, 2),
                    new Vector(3, 2),
                    new Vector(3, 1),
                    new Vector(4, 1)
                )
            ),
            Arguments.of(FileToMaze.fromFile("./src/test/java/backend/academy/ice_maze"),
                new Vector(0, 1),
                new Vector(4, 1),
                Arrays.asList(
                    new Vector(0, 1),
                    new Vector(1, 1),
                    new Vector(1, 0),
                    new Vector(2, 0),
                    new Vector(3, 0),
                    new Vector(3, 1),
                    new Vector(4, 1)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("shortestPathTestProvider")
    void solveProvidesShortestPath(Maze maze, Vector start, Vector end,
        List<Vector> correctPath) {
        var path = algorithm.solve(maze, start, end);

        assertThat(path).hasSameSizeAs(correctPath);
        assertThat(path.getFirst()).isEqualTo(start);
        assertThat(path.getLast()).isEqualTo(end);
        assertThat(path).isEqualTo(correctPath);

    }
}
