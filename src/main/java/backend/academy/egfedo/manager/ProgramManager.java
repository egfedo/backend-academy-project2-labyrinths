package backend.academy.egfedo.manager;

import backend.academy.egfedo.alg.factory.GenAlgorithmFactory;
import backend.academy.egfedo.alg.factory.SolveAlgorithmFactory;
import backend.academy.egfedo.alg.gen.GenAlgorithm;
import backend.academy.egfedo.data.Vector;
import backend.academy.egfedo.io.MazeViewInput;
import backend.academy.egfedo.io.MazeViewOutput;
import backend.academy.egfedo.io.MenuInput;
import backend.academy.egfedo.io.MenuOutput;
import backend.academy.egfedo.io.impl.TerminalMazeViewInput;
import backend.academy.egfedo.io.impl.TerminalMazeViewOutput;
import backend.academy.egfedo.io.impl.TerminalMenuInput;
import backend.academy.egfedo.io.impl.TerminalMenuOutput;
import backend.academy.egfedo.manager.impl.ChooseDimensionsBlock;
import backend.academy.egfedo.manager.impl.ChooseEndBlock;
import backend.academy.egfedo.manager.impl.ChooseGenAlgBlock;
import backend.academy.egfedo.manager.impl.ChooseSolveAlgBlock;
import backend.academy.egfedo.manager.impl.ChooseStartBlock;
import java.io.IOException;
import java.util.List;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class ProgramManager {

    @SuppressWarnings({"MagicNumber", "RegexpSinglelineJava"})
    public void run() {

        try (
            Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .jna(true)
                .build()
        ) {
            terminal.enterRawMode();
            MenuOutput menuOutput = new TerminalMenuOutput(terminal);
            MenuInput menuInput = new TerminalMenuInput(terminal);

            List<MenuBlockManager> chooseGenBlocks = List.of(
                new ChooseGenAlgBlock(menuInput, menuOutput),
                new ChooseDimensionsBlock(menuInput, menuOutput)
            );

            var chooseGenMenu = new MenuManager(chooseGenBlocks, menuOutput);

            List<MenuBlockManager> chooseSolveBlocks = List.of(
                new ChooseSolveAlgBlock(menuInput, menuOutput),
                new ChooseStartBlock(menuInput, menuOutput),
                new ChooseEndBlock(menuInput, menuOutput)
            );
            MazeViewInput input = new TerminalMazeViewInput(terminal.reader());

            MazeViewOutput output = new TerminalMazeViewOutput(
                terminal,
                new Vector(((terminal.getWidth() - 12) / 2 - 1) / 2, (terminal.getHeight() - 8) / 2)
            );

            var manager = new MazeViewManager(output, input);
            var chooseSolveMenu = new MenuManager(chooseSolveBlocks, menuOutput);

            var options = chooseGenMenu.run();

            var genFactory = new GenAlgorithmFactory();
            GenAlgorithm algorithm = genFactory.getByEnum(options.gen());

            var maze = algorithm.generateMaze(options.mazeSize().x(), options.mazeSize().y());

            manager.displayMaze(maze, null);

            options = chooseSolveMenu.run(options);

            var solveFactory = new SolveAlgorithmFactory();
            var solver = solveFactory.getByEnum(options.solve());
            var path = solver.solve(maze, options.start(), options.end());

            manager.displayMaze(maze, path);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
