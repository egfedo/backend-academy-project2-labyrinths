package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.io.MenuOutput;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

public class TerminalMenuOutput implements MenuOutput {

    private final Terminal terminal;
    private final PrintWriter writer;

    public TerminalMenuOutput(Terminal terminal) {
        this.terminal = Objects.requireNonNull(terminal);
        this.writer = terminal.writer();
    }

    @Override
    public void init() {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.puts(InfoCmp.Capability.cursor_address, 0, 0);
    }

    @Override
    public void displayOptions(String title, List<String> options) {
        writer.println("> " + title);
        writer.println();
        for (int i = 0; i < options.size(); i++) {
            writer.println((i + 1) + ". " + options.get(i));
        }
        writer.println();
        writer.flush();
    }

    @Override
    public void displayEnter(String title) {
        writer.print("> " + title + ": ");
        writer.flush();
    }

    @Override
    public void displayChosen(String option) {
        writer.println("> Выбран вариант " + option);
        writer.println();
        writer.flush();
    }
}
