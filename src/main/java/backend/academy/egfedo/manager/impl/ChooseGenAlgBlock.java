package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.data.GenAlgorithmName;
import backend.academy.egfedo.data.ProgramOptions;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import backend.academy.egfedo.manager.MenuBlockManager;

public class ChooseGenAlgBlock implements MenuBlockManager {

    private final ChooseEnumValue<GenAlgorithmName> chooser;

    public ChooseGenAlgBlock(MenuInput input, MenuOutput output) {
        chooser = new ChooseEnumValue<>(input, output,
            "Выберите алгоритм генерации:", GenAlgorithmName.class);
    }

    @Override
    public void proceed(ProgramOptions.ProgramOptionsBuilder options) {
        options.gen(chooser.choose());
    }
}
