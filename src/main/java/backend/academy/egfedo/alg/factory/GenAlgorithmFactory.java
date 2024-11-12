package backend.academy.egfedo.alg.factory;

import backend.academy.egfedo.alg.gen.DFSAlgorithm;
import backend.academy.egfedo.alg.gen.GenAlgorithm;
import backend.academy.egfedo.alg.gen.ImperfectWrapper;
import backend.academy.egfedo.alg.gen.PrimAlgorithm;
import backend.academy.egfedo.data.GenAlgorithmName;

public class GenAlgorithmFactory {

    public GenAlgorithm getByEnum(GenAlgorithmName alg) {
        return switch (alg) {
            case DFS -> new DFSAlgorithm();
            case Prim -> new PrimAlgorithm();
            case ImperfectDFS -> new ImperfectWrapper(new DFSAlgorithm());
            case ImperfectPrim -> new ImperfectWrapper(new PrimAlgorithm());
        };
    }
}
