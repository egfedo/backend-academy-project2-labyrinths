package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import java.util.Objects;

public class ChooseVector {

    private final MenuInput input;
    private final MenuOutput output;

    private final String desc1;
    private final String desc2;


    public ChooseVector(MenuInput input, MenuOutput output, String desc1, String desc2) {
        this.input = Objects.requireNonNull(input);
        this.output = Objects.requireNonNull(output);
        this.desc1 = Objects.requireNonNull(desc1);
        this.desc2 = Objects.requireNonNull(desc2);
    }

    public Vector choose(int maxLen, int firstMin, int firstMax, int secondMin, int secondMax) {

        output.displayEnter(desc1);
        int first = input.getInputNumber(maxLen, firstMin, firstMax);
        output.displayEnter(desc2);
        int second = input.getInputNumber(maxLen, secondMin, secondMax);

        return new Vector(first, second);
    }

}
