package backend.academy.egfedo.alg.gen;

import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.util.RandomProvider;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DFSAlgorithm implements GenAlgorithm {

    private final List<Integer> pathDistribution = List.of(
        1, 1, 3, 3, 2, 2, 2, 2, 2, 2
    );

    @SuppressFBWarnings("SUI_CONTAINS_BEFORE_ADD")
    public Maze generateMaze(int width, int height) {
        var builder = new Maze.Builder(height, width);
        Random random = RandomProvider.getInstance();
        var startPos = new Vector(random.nextInt(width), random.nextInt(height));
        Set<Vector> passed = new HashSet<>();
        passed.add(startPos);

        ArrayDeque<Vector> queue = new ArrayDeque<>();

        queue.addFirst(startPos);

        while (!queue.isEmpty()) {
            var curr = queue.peek();

            List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
            Collections.shuffle(directions, random);
            boolean flag = false;
            for (var dir : directions) {
                var neighbor = curr.add(dir.vec);
                if (builder.inBounds(neighbor) && !passed.contains(neighbor)) {
                    int diff = pathDistribution.get(random.nextInt(pathDistribution.size()));
                    builder.connect(curr, dir, diff);
                    passed.add(neighbor);
                    queue.addFirst(neighbor);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }

            queue.pop();
        }

        return builder.build();
    }
}
