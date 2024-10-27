package backend.academy.egfedo.manager;

import backend.academy.egfedo.data.ProgramOptions;
import backend.academy.egfedo.io.MenuOutput;
import java.util.List;
import java.util.Objects;

public class MenuManager {

    private final List<MenuBlockManager> blocks;
    private final MenuOutput output;

    public MenuManager(List<MenuBlockManager> blocks, MenuOutput output) {
        this.blocks = Objects.requireNonNull(blocks);
        this.output = Objects.requireNonNull(output);
    }

    public ProgramOptions run() {
        return run(null);
    }

    public ProgramOptions run(ProgramOptions options) {
        ProgramOptions.ProgramOptionsBuilder builder;
        if (Objects.isNull(options)) {
            builder = ProgramOptions.builder();
        } else {
            builder = options.toBuilder();
        }
        output.init();

        for (var block : blocks) {
            block.proceed(builder);
        }

        return builder.build();
    }
}
