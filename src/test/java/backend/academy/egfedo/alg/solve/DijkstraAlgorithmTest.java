package backend.academy.egfedo.alg.solve;

import org.junit.jupiter.api.BeforeEach;

public class DijkstraAlgorithmTest extends SolveAlgorithmBaseTest {

    @Override
    @BeforeEach
    void setUp() {
        algorithm = new DijkstraAlgorithm();
    }
}
