package backend.academy.egfedo.alg.gen;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.util.RandomProvider;
import java.util.Objects;
import java.util.Random;

public class ImperfectWrapper implements GenAlgorithm {

    private final GenAlgorithm genAlgorithm;

    public ImperfectWrapper(GenAlgorithm genAlgorithm) {
        this.genAlgorithm = Objects.requireNonNull(genAlgorithm);
    }

    @Override
    public Maze generateMaze(int width, int height) {

        var maze = genAlgorithm.generateMaze(width, height);
        var builder = maze.toBuilder();

        int count = 0;
        while (count < (width * height) / Config.IMPERFECT_GEN_DELETE_COEF) {
            Vector randomVec = new Vector(RandomProvider.nextInt(width), RandomProvider.nextInt(height));
            Direction randomDir = Direction.values()[RandomProvider.nextInt(Direction.values().length)];

            var neighbor = randomVec.add(randomDir.vec);
            if (builder.inBounds(randomVec) && builder.inBounds(neighbor)
                && !builder.getCell(randomVec).hasDirection(randomDir)) {
                count++;
                builder.connect(randomVec, randomDir, RandomProvider.nextInt(1, Config.MUD_PASSAGE_WEIGHT));
            }
        }
        return builder.build();
    }
}
