package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.data.ProgramOptions;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import backend.academy.egfedo.manager.MenuBlockManager;
import java.util.Objects;

public class ChooseDimensionsBlock implements MenuBlockManager {

    private final ChooseVector chooser;

    public ChooseDimensionsBlock(MenuInput input, MenuOutput output) {
        this.chooser = new ChooseVector(
            Objects.requireNonNull(input),
            Objects.requireNonNull(output),
            "Введите ширину лабиринта",
            "Введите высоту лабиринта"
        );
    }

    @Override
    public void proceed(ProgramOptions.ProgramOptionsBuilder options) {
        options.mazeSize(chooser.choose(Config.MAX_INPUT_NUMBER_LEN,
            Config.MIN_MAZE_SIDE_SIZE, Config.MAX_MAZE_SIDE_SIZE,
            Config.MIN_MAZE_SIDE_SIZE, Config.MAX_MAZE_SIDE_SIZE));
    }
}
