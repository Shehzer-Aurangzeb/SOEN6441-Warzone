import utils.Feedback;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void displayWelcomeMessage() {
        Feedback.displayWelcomeMessage();

        String expectedOutput = "Welcome to the Risk Game!\n" +
                "Before we begin, here are a few commands you can use\n\n" +
                "\t-loadmap <filename>: Load a map file to start the game.\n" +
                "\t-showcommands: Display all available commands.\n" +
                "\t-exit: Exit the game.\n";

        // Normalize line separators
        expectedOutput = normalizeLineSeparators(expectedOutput);
        String actualOutput = normalizeLineSeparators(outputStreamCaptor.toString());

        assertEquals(expectedOutput, actualOutput);
    }

    // Utility method to normalize line separators
    private String normalizeLineSeparators(String input) {
        return input.replaceAll("\r\n", "\n");
    }


@org.junit.jupiter.api.Test
    void displayPhaseInstructions() {
    }
}