package utils;
import java.security.SecureRandom;
public class Helpers {
    private static final SecureRandom d_random = new SecureRandom();
    /**
     * Generates a random ID within the range [100, 999].
     *
     * @return A random ID.
     */
    public static int generateUniqueID(){
        return d_random.nextInt(900)+100;
    }
}
