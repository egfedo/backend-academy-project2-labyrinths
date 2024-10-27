package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import java.util.Arrays;
import java.util.Objects;

public class ChooseEnumValue<T extends Enum<T>> {

    private final MenuInput input;
    private final MenuOutput output;
    private final String title;
    private final Class<T> enumClass;


    public ChooseEnumValue(MenuInput input, MenuOutput output, String title, Class<T> enumClass) {
        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
        this.title = Objects.requireNonNull(title);
        this.enumClass = Objects.requireNonNull(enumClass);
    }

    public T choose() {
        var constants = enumClass.getEnumConstants();

        output.displayOptions(
            title,
            Arrays.stream(constants).map(Objects::toString).toList()
        );

        int option = input.getInputOption();
        output.displayChosen(constants[option].toString());

        return constants[option];
    }
}
