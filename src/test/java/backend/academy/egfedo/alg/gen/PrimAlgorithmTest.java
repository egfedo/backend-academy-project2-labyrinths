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

public class PrimAlgorithmTest extends GenAlgorithmBaseTest {

    @BeforeEach
    @Override
    void setUp() {
        algorithm = new PrimAlgorithm();
    }
}
