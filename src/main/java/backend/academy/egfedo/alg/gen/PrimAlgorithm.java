package backend.academy.egfedo.alg.gen;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.util.RandomProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PrimAlgorithm implements GenAlgorithm  {

    private final List<Integer> pathDistribution = List.of(
        1, 1, 3, 3, 2, 2, 2, 2, 2, 2
    );

    public Maze generateMaze(int width, int height) {

        var builder = new Maze.Builder(height, width);
        Set<Vector> mst = new HashSet<>(width * height);
        List<Vector> neighbours = new ArrayList<>();
        Map<Vector, List<Direction>> neighbourDir = new HashMap<>();

        var startPos = new Vector(RandomProvider.nextInt(width), RandomProvider.nextInt(height));
        mst.add(startPos);

        for (var dir : Direction.values()) {
            var directional = startPos.add(dir.vec);
            if (builder.inBounds(directional)) {
                if (!neighbourDir.containsKey(directional)) {
                    neighbours.add(directional);
                    neighbourDir.put(directional, new ArrayList<>());
                }
                neighbourDir.get(directional).add(dir.inverse());
            }
        }
        neighbourDir.put(startPos, new ArrayList<>());

        while (mst.size() < width * height) {
            var randIdx = RandomProvider.nextInt(neighbours.size());
            var curr = neighbours.get(randIdx);
            neighbours.remove(randIdx);

            var paths = neighbourDir.get(curr);
            var pathDir = paths.get(RandomProvider.nextInt(paths.size()));

            if (paths.size() > 1) {
                int i = 0;
                Vector conn;
                do {
                    pathDir = paths.get(RandomProvider.nextInt(paths.size()));
                    conn = curr.add(pathDir.vec);
                    i++;
                } while (i < Config.MAX_PRIM_RANDOM_RETRIES && builder.getCell(conn).hasDirection(pathDir));
            }

            int diff = pathDistribution.get(RandomProvider.nextInt(pathDistribution.size()));
            builder.connect(curr, pathDir, diff);
            mst.add(curr);

            for (var dir : Direction.values()) {
                var directional = curr.add(dir.vec);
                if (builder.inBounds(directional)) {
                    if (!neighbourDir.containsKey(directional)) {
                        neighbours.add(directional);
                        neighbourDir.put(directional, new ArrayList<>());
                    }
                    neighbourDir.get(directional).add(dir.inverse());
                }
            }

        }

        return builder.build();
    }

}
