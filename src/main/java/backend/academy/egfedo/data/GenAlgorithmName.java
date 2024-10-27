package backend.academy.egfedo.data;

public enum GenAlgorithmName {
    DFS, Prim, ImperfectDFS, ImperfectPrim;

    @Override
    public String toString() {
        return switch (this) {
            case DFS -> "Алгоритм на основе DFS без циклов";
            case Prim -> "Алгоритм Прима без циклов";
            case ImperfectDFS -> "Алгоритм на основе DFS с циклами";
            case ImperfectPrim -> "Алгоритм Прима DFS с циклами";
        };
    }
}
