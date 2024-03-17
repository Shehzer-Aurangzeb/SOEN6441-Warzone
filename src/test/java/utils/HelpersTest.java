package utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HelpersTest {

    @Test
    void generateUniqueID() {
        //the range of IDs
        int minID = 100;
        int maxID = 999;

        // Number of IDs to generate for testing
        int numIDs = 100;

        // Set to store generated IDs
        Set<Integer> generatedIDs = new HashSet<>();

        // Generate IDs and add them to the set
        for (int i = 0; i < numIDs; i++) {
            int id = Helpers.generateUniqueID();
            // Check if the generated ID is within the range
            assertTrue(id >= minID && id <= maxID, "Generated ID is out of range: " + id);
            // Check if the generated ID is unique
            assertTrue(generatedIDs.add(id), "Generated ID is unique "+id);
        }

        // Ensure that the number of generated IDs matches the expected number
        assertEquals(numIDs, generatedIDs.size(), "Number of generated IDs does not match");
    }

}