package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.data.Direction;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.MazeTile;
import backend.academy.egfedo.io.MazeViewOutput;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

public class TerminalMazeViewOutput implements MazeViewOutput  {

    private final Terminal terminal;
    private final PrintWriter writer;
    private final Vector dimensions;

    private final static String HEADING = "[ MAZE VIEW ]";
    private final Map<MazeTile, String> tileToString = Map.of(
        MazeTile.FLOOR, "  ",
        MazeTile.WALL, "⬜",
        MazeTile.EMPTY, "  ",
        MazeTile.PATH, "\uD83D\uDFE1",
        MazeTile.START, "\uD83D\uDD34",
        MazeTile.END, "\uD83D\uDFE2",
        MazeTile.MUD, "\uD83D\uDFEB",
        MazeTile.MUD_PATH, "\uD83D\uDFE4",
        MazeTile.ICE, "\uD83D\uDFE6",
        MazeTile.ICE_PATH, "\uD83D\uDD35"
    );

    public TerminalMazeViewOutput(Terminal terminal, Vector dimensions) {
        this.terminal = Objects.requireNonNull(terminal);
        this.writer = terminal.writer();
        this.dimensions = Objects.requireNonNull(dimensions);
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.puts(InfoCmp.Capability.cursor_invisible);
    }

    @SuppressFBWarnings(value = "IM_BAD_CHECK_FOR_ODD", justification = "Counter cannot be negative")
    // Display settings are not editable
    @SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
    @Override
    public void displayMaze(List<List<MazeTile>> fragment, Vector offset, Map<Direction, Boolean> fogFlags) {
        terminal.puts(InfoCmp.Capability.cursor_address, 0, 0);
        writer.println(" ".repeat(terminal.getWidth() / 2 - Math.ceilDiv(HEADING.length(), 2)) + HEADING);
        printNumbers(offset);
        printDirection(fogFlags, Direction.UP, "^");
        writer.println();

        int y = offset.y();
        int counter = 0;
        for (var row : fragment) {
            if (counter % 2 == 1) {
                writer.print(" ");
                if (y < 10) {
                    writer.print(' ');
                }
                writer.print(y);
                writer.print(" ");
            } else {
                writer.print("    ");
            }

            if (fogFlags.get(Direction.LEFT)) {
                writer.print("< ");
            } else {
                writer.print("  ");
            }

            for (var elem : row) {
                writer.print(tileToString.get(elem));
            }

            if (fogFlags.get(Direction.RIGHT)) {
                writer.print(" >");
            } else {
                writer.print("  ");
            }

            if (counter % 2 == 1) {
                writer.print(" ");
                if (y < 10) {
                    writer.print(' ');
                }
                writer.print(y);
                writer.print(" ");
                y++;
            }

            counter++;

            writer.println();
        }

        printDirection(fogFlags, Direction.DOWN, "V");
        writer.println();
        printNumbers(offset);
        writer.println();
        writer.print(" [W, A, S, D] - Move  [esc] - Exit");
        writer.flush();
    }

    @SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
    private void printNumbers(Vector offset) {
        writer.print("        ");

        for (int j = offset.x(); j < offset.x() + dimensions.x(); j++) {
            if (j < 10) {
                writer.print(' ');
            }
            writer.print(j);
            writer.print("  ");
        }
        writer.println();
    }

    @SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
    private void printDirection(Map<Direction, Boolean> fogFlags, Direction dir, String sym) {
        if (fogFlags.get(dir)) {
            writer.print("      ");
            writer.print(sym.repeat(dimensions.x() * 4 + 2));
        } else {
            writer.print("      ");
            writer.print(" ".repeat(dimensions.x() * 4 + 2));
        }
    }

    @Override
    public Vector getDimensions() {
        return dimensions;
    }
}
