package backend.academy;

import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Maze;
import backend.academy.egfedo.data.Vector;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileToMaze {

    public static Maze fromFile(String fileName) {

        try (var file = new FileInputStream(fileName)) {
            var scanner = new Scanner(file);
            int height = scanner.nextInt();
            int width = scanner.nextInt();
            var builder = new Maze.Builder(height, width);

            scanner.nextLine();
            scanner.nextLine();
            for (int i = 0; i < height; i++) {
                String line = scanner.nextLine();
                int pos = 2;
                for (int j = 0; j < width; j++) {
                    if (line.charAt(pos) != '#') {
                        int diff = switch(line.charAt(pos)) {
                            case 'm' -> 3;
                            case 'i' -> 1;
                            default -> 2;
                        };
                        builder.connect(new Vector(j, i), Direction.RIGHT, diff);
                    }
                    pos += 2;
                }
                line = scanner.nextLine();
                pos = 1;
                for (int j = 0; j < width; j++) {
                    if (line.charAt(pos) != '#') {
                        int diff = switch(line.charAt(pos)) {
                            case 'm' -> 3;
                            case 'i' -> 1;
                            default -> 2;
                        };
                        builder.connect(new Vector(j, i), Direction.DOWN, diff);
                    }
                    pos += 2;
                }
            }
            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
