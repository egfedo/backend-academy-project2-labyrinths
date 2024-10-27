package backend.academy.egfedo.alg.solve;

import backend.academy.egfedo.data.Vector;

public class AStarAlgorithm extends BaseSolveAlgorithm {

    @Override
    int heuristic(Vector curr, Vector end) {
        return (int) Math.floor(
            Math.sqrt(Math.pow(curr.x() - end.x(), 2)
                + Math.pow(curr.y() - end.y(), 2))
        );
    }

}
