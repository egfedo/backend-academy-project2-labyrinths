package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.data.ProgramOptions;
import backend.academy.egfedo.data.SolveAlgorithmName;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import backend.academy.egfedo.manager.MenuBlockManager;

public class ChooseSolveAlgBlock implements MenuBlockManager {

    private final ChooseEnumValue<SolveAlgorithmName> chooser;

    public ChooseSolveAlgBlock(MenuInput input, MenuOutput output) {
        chooser = new ChooseEnumValue<>(input, output,
            "Выберите алгоритм поиска пути:", SolveAlgorithmName.class);
    }

    @Override
    public void proceed(ProgramOptions.ProgramOptionsBuilder options) {
        options.solve(chooser.choose());
    }
}
