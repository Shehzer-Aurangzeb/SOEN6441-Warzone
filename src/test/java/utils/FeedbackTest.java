package utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Feedback;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void displayWelcomeMessage() {
        String l_message="Welcome to the Risk Game!";
        int totalWidth= 60;
        int padding = (totalWidth - l_message.length() - 4) / 2;

        Feedback.displayWelcomeMessage();

        String expectedOutput = "*".repeat(totalWidth) +
                "\n\n*" + " ".repeat(padding) + " " + l_message + " " + " ".repeat(padding) + "*\n\n"+
                "*".repeat(totalWidth)+
                "\nBefore we begin, here are a few commands you can use\n\n" +
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


@Test
    void displayPhaseInstructions() {
    }
}