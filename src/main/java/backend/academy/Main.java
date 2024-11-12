package backend.academy;

import backend.academy.egfedo.manager.ProgramManager;
import java.io.IOException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {

        try {
            var program = new ProgramManager();
            program.run();
        } catch (IOException e) {
            System.err.println("> Произошла ошибка при инициализации терминала.");
        } catch (Exception e) {
            System.err.println("> Произошла непредвиденная ошибка.");
        }


    }
}
