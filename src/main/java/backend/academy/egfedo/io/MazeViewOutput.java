package backend.academy.egfedo.io;

import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Vector;
import java.util.List;
import java.util.Map;

public interface MazeViewOutput {

    void displayMaze(List<List<MazeTile>> fragment, Vector offset, Map<Direction, Boolean> fogFlags);

    Vector getDimensions();
}
