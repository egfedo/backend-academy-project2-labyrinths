package backend.academy.egfedo.alg.factory;

import backend.academy.egfedo.alg.solve.AStarAlgorithm;
import backend.academy.egfedo.alg.solve.DijkstraAlgorithm;
import backend.academy.egfedo.alg.solve.SolveAlgorithm;
import backend.academy.egfedo.data.SolveAlgorithmName;

public class SolveAlgorithmFactory {

    public SolveAlgorithm getByEnum(SolveAlgorithmName alg) {
        return switch (alg) {
            case AStar -> new AStarAlgorithm();
            case Dijkstra -> new DijkstraAlgorithm();
        };
    }

}
