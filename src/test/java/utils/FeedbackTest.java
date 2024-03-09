package utils;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

        assertEquals(expectedOutput, outputStreamCaptor.toString());

    }

    @org.junit.jupiter.api.Test
    void displayPhaseInstructions() {
    }
}