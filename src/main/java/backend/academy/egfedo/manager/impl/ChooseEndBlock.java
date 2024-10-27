package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.data.Config;
import backend.academy.egfedo.data.ProgramOptions;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import backend.academy.egfedo.manager.MenuBlockManager;
import java.util.Objects;

public class ChooseEndBlock implements MenuBlockManager  {

    private final ChooseVector chooser;

    public ChooseEndBlock(MenuInput input, MenuOutput output) {
        this.chooser = new ChooseVector(
            Objects.requireNonNull(input),
            Objects.requireNonNull(output),
            "Введите координату x конца",
            "Введите координату y конца"
        );
    }

    @Override
    public void proceed(ProgramOptions.ProgramOptionsBuilder options) {
        var currOptions = options.build();
        var chosen = chooser.choose(Config.MAX_INPUT_NUMBER_LEN, 0,
            currOptions.mazeSize().x() - 1, 0,
            currOptions.mazeSize().y() - 1);
        options.end(chosen);
    }

}
