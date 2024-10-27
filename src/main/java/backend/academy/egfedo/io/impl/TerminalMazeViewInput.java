package backend.academy.egfedo.io.impl;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.io.InputCommand;
import backend.academy.egfedo.io.MazeViewInput;
import java.io.IOException;
import java.io.Reader;

public class TerminalMazeViewInput implements MazeViewInput {

    private final Reader reader;

    public TerminalMazeViewInput(Reader reader) {
        this.reader = reader;
    }

    @Override
    public InputCommand next() {
        try {
            return switch (reader.read()) {
                case Config.UP_KEY_CODE -> InputCommand.UP;
                case Config.DOWN_KEY_CODE -> InputCommand.DOWN;
                case Config.LEFT_KEY_CODE -> InputCommand.LEFT;
                case Config.RIGHT_KEY_CODE -> InputCommand.RIGHT;
                case Config.ESC_KEY_CODE -> InputCommand.EXIT;
                default -> InputCommand.NULL;
            };
        } catch (IOException e) {
            return InputCommand.EXIT;
        }

    }
}
