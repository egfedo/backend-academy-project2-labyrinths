package backend.academy.egfedo.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.SecureRandom;
import java.util.Random;
import lombok.experimental.UtilityClass;

@SuppressFBWarnings("MS_EXPOSE_REP")
@UtilityClass
public class RandomProvider {

    static private Random instance = null;

    public static Random getInstance() {
        if (instance == null) {
            instance = new SecureRandom();
        }
        return instance;
    }

    public static int nextInt(int bound) {
        getInstance();
        return instance.nextInt(bound);
    }

    public static int nextInt(int origin, int bound) {
        getInstance();
        return instance.nextInt(origin, bound);
    }

}
