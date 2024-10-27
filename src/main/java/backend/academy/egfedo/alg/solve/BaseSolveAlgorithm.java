package backend.academy.egfedo.alg.solve;

import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import org.apache.commons.math3.util.Pair;

public abstract class BaseSolveAlgorithm implements SolveAlgorithm {

    abstract int heuristic(Vector curr, Vector end);

    @SuppressFBWarnings("SUI_CONTAINS_BEFORE_ADD")
    @Override
    public List<Vector> solve(Maze maze, Vector start, Vector end) {
        PriorityQueue<Pair<Integer, Vector>> queue = new PriorityQueue<>(
            Comparator.comparing(Pair::getFirst)
        );
        Set<Vector> visited = new HashSet<>(maze.columns() * maze.rows());

        Set<Vector> toVisit = new HashSet<>();
        toVisit.add(start);

        HashMap<Vector, Vector> prev = new HashMap<>();

        prev.put(start, start);

        queue.add(new Pair<>(0, start));

        while (!queue.isEmpty()) {
            var curr = queue.remove();
            var vec = curr.getSecond();
            if (visited.contains(vec)) {
                continue;
            }
            visited.add(vec);

            if (vec.equals(end)) {
                break;
            }

            for (var dir: Direction.values()) {
                if (maze.getCell(vec).hasDirection(dir)) {
                    var neighbor = vec.add(dir.vec);
                    if (!visited.contains(neighbor) && !toVisit.contains(neighbor)) {
                        prev.put(neighbor, vec);
                        queue.add(
                            new Pair<>(
                                curr.getFirst() + maze.getCell(vec).getDirection(dir) + heuristic(neighbor, end),
                                neighbor
                            )
                        );
                        toVisit.add(neighbor);
                    }
                }
            }
        }

        List<Vector> path = new ArrayList<>();

        Vector curr = end;

        do  {
            path.add(curr);
            curr = prev.get(curr);
        } while (!curr.equals(start));

        path.add(start);

        return path.reversed();
    }

}
