package backend.academy;

import backend.academy.egfedo.manager.ProgramManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {

        var program = new ProgramManager();
        program.run();

    }
}
