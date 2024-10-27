package backend.academy.egfedo.data;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Config {

    public final static int BACKSPACE_CODE = 127;

    public final static int ENTER_CODE = 13;

    public final static int UP_KEY_CODE = 119;
    public final static int DOWN_KEY_CODE = 115;
    public final static int LEFT_KEY_CODE = 97;
    public final static int RIGHT_KEY_CODE = 100;

    public final static int ESC_KEY_CODE = 27;

    public final static int MAX_INPUT_NUMBER_LEN = 3;
    public final static int MIN_MAZE_SIDE_SIZE = 1;
    public final static int MAX_MAZE_SIDE_SIZE = 100;

    public final static int MUD_PASSAGE_WEIGHT = 3;
    public final static int NORMAL_PASSAGE_WEIGHT = 2;
    public final static int ICE_PASSAGE_WEIGHT = 1;

    public final static int MAX_PRIM_RANDOM_RETRIES = 10;

    public final static int IMPERFECT_GEN_DELETE_COEF = 8;
}
