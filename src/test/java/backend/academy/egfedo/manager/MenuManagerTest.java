package backend.academy.egfedo.manager;

import backend.academy.egfedo.data.ProgramOptions;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.MenuOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MenuManagerTest {

    @Mock
    MenuOutput output;

    MenuBlockManager manager = options -> options.start(new Vector(1, 1));

    @Test
    void run_createsCorrectOptions() {

        var menuManager = new MenuManager(
            List.of(manager),
            output
        );

        var options = menuManager.run();

        verify(output).init();

        assertThat(options).isEqualTo(
            new ProgramOptions(null, null, null,
                new Vector(1, 1), null)
        );

    }

    @Test
    void run_updatesOptions() {
        var menuManager = new MenuManager(
            List.of(manager),
            output
        );

        var options = menuManager.run(
            new ProgramOptions(null, null, new Vector(16, 8),
                new Vector(2, 1), null)
        );

        verify(output).init();

        assertThat(options).isEqualTo(
            new ProgramOptions(null, null, new Vector(16, 8),
                new Vector(1, 1), null)
        );
    }

}
