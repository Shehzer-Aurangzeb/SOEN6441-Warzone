package utils;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class Helpers {
    private static final SecureRandom d_random = new SecureRandom();
    private static final Set<Integer> generatedIDs=new HashSet<>();
    /**
     * Generates a random ID within the range [100, 999].
     *
     * @return A random ID.
     */
    public static int generateUniqueID(){
        int generatedID;
        do {
            generatedID = d_random.nextInt(900) + 100;
        } while (!generatedIDs.add(generatedID));
        return generatedID;
    }
}
