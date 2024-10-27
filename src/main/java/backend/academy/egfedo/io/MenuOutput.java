package backend.academy.egfedo.io;

import java.util.List;

public interface MenuOutput {

    void init();

    void displayOptions(String title, List<String> options);

    void displayEnter(String title);

    void displayChosen(String option);

}
