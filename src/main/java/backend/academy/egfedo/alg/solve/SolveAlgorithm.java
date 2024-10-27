package backend.academy.egfedo.alg.solve;

import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.SolveAlgorithmName;
import backend.academy.egfedo.data.Vector;
import java.util.List;

public interface SolveAlgorithm {

    List<Vector> solve(Maze maze, Vector start, Vector end);

    static SolveAlgorithm getByEnum(SolveAlgorithmName alg) {
        return switch (alg) {
            case AStar -> new AStarAlgorithm();
            case Dijkstra -> new DijkstraAlgorithm();
        };
    }

}
