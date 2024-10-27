package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChooseVectorTest {

    @Mock
    private MenuInput input;

    @Mock
    private MenuOutput output;

    @ParameterizedTest
    @CsvSource({"1, 2, 3, 0, 9, 0, 9", "11, 11, 3, 1, 100, 1, 100"})
    void choose_returnsCorrectValue(int x, int y, int maxLen, int firstMin,
        int firstMax, int secondMin, int secondMax) {

        when(input.getInputNumber(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
            .thenReturn(x, y);

        var chooser = new ChooseVector(input, output, "First value", "Second value");

        Vector chosen = chooser.choose(maxLen, firstMin, firstMax, secondMin, secondMax);

        InOrder inOrder = Mockito.inOrder(input, output);

        inOrder.verify(output).displayEnter("First value");
        inOrder.verify(input).getInputNumber(maxLen, firstMin, firstMax);
        inOrder.verify(output).displayEnter("Second value");
        inOrder.verify(input).getInputNumber(maxLen, secondMin, secondMax);

        assertThat(chosen).isEqualTo(new Vector(x, y));

    }
}
