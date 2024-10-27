package backend.academy.egfedo.data;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProgramOptions(GenAlgorithmName gen, SolveAlgorithmName solve,
                             Vector mazeSize, Vector start, Vector end) {


}
