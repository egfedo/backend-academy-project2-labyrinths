package backend.academy.egfedo.manager.impl;

import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChooseEnumValueTest {

    @Mock
    private MenuInput input;

    @Mock
    private MenuOutput output;

    enum TestEnum {
        FIRST, SECOND, THIRD
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2"})
    void choose_returnsCorrectEnumValue(int index) {
        ChooseEnumValue<TestEnum> chooser = new ChooseEnumValue<>(input, output,
            "Choose value", TestEnum.class);

        when(input.getInputOption()).thenReturn(index);

        TestEnum en = chooser.choose();

        InOrder order = inOrder(output);
        order.verify(output).displayOptions(
            "Choose value",
            Arrays.stream(TestEnum.values()).map(Objects::toString).toList()
            );
        order.verify(output).displayChosen(TestEnum.values()[index].toString());
        order.verifyNoMoreInteractions();

        assertThat(en).isEqualTo(TestEnum.values()[index]);
    }
}
