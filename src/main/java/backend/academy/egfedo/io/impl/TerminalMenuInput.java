package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.io.MenuInput;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

public class TerminalMenuInput implements MenuInput {

    private final Terminal terminal;
    private final Reader reader;

    private final static String UNABLE_TO_READ_ERROR_MSG = "Unable to read input option";

    public TerminalMenuInput(Terminal terminal) {
        this.terminal = terminal;
        this.reader = terminal.reader();
    }

    @Override
    public int getInputOption() {
        try {
            while (true) {
                int option = reader.read();

                char c = (char) option;
                if (Character.isDigit(c)) {
                    if (Character.getNumericValue(c) != 0) {
                        return Character.getNumericValue(c) - 1;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(UNABLE_TO_READ_ERROR_MSG, e);
        }
    }

    // Base 10 is not a magic number
    @SuppressWarnings("MagicNumber")
    private int digitsToNumber(List<Integer> digits) {
        int res = 0;
        int mult = 1;
        for (Integer digit : digits.reversed()) {
            res += digit * mult;
            mult *= 10;
        }
        return res;
    }

    // It repeats only 2 times
    @SuppressWarnings("MultipleStringLiterals")
    @Override
    public int getInputNumber(int maxLen, int minValue, int maxValue) {
        List<Integer> digits = new ArrayList<>();

        var cursorPos = terminal.getCursorPosition((v) -> {});

        terminal.puts(InfoCmp.Capability.cursor_invisible);

        terminal.puts(InfoCmp.Capability.cursor_address, cursorPos.getY(), cursorPos.getX() + maxLen + 1);
        terminal.writer().print("(между " + minValue + " и " + maxValue + ")");
        terminal.writer().flush();

        try {
            while (true) {
                int option = reader.read();

                if (option == Config.BACKSPACE_CODE) {
                    if (!digits.isEmpty()) {
                        digits.removeLast();
                    }
                }

                if (option == Config.ENTER_CODE) {
                    if (digits.isEmpty()) {
                        continue;
                    }
                    if (digitsToNumber(digits) < minValue) {
                        continue;
                    }
                    if (digitsToNumber(digits) > maxValue) {
                        continue;
                    }

                    terminal.writer().println();
                    break;
                }

                char c = (char) option;
                if (Character.isDigit(c)) {
                    if (digits.size() < maxLen) {
                        int digit = Character.getNumericValue(c);
                        digits.add(digit);
                    }
                }
                terminal.puts(InfoCmp.Capability.cursor_address, cursorPos.getY(), cursorPos.getX());
                terminal.writer().print(
                    String.join("",
                        digits.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining()) + " ".repeat(maxLen)
                    )
                );
                terminal.puts(InfoCmp.Capability.cursor_address, cursorPos.getY(), cursorPos.getX() + maxLen + 1);
                terminal.writer().print("(между " + minValue + " и " + maxValue + ")");
                terminal.writer().flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(UNABLE_TO_READ_ERROR_MSG, e);
        }


        return digitsToNumber(digits);
    }
}
