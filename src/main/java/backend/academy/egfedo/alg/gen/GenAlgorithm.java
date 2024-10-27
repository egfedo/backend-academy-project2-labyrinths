package backend.academy.egfedo.alg.gen;

import backend.academy.egfedo.data.GenAlgorithmName;
import backend.academy.egfedo.data.Maze;

public interface GenAlgorithm {

    Maze generateMaze(int width, int height);

    static GenAlgorithm getByEnum(GenAlgorithmName alg) {
        return switch (alg) {
            case DFS -> new DFSAlgorithm();
            case Prim -> new PrimAlgorithm();
            case ImperfectDFS -> new ImperfectWrapper(new DFSAlgorithm());
            case ImperfectPrim -> new ImperfectWrapper(new PrimAlgorithm());
        };
    }
}
