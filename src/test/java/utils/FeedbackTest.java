package utils;

import constants.Commands;
import models.Command.Command;
import models.Enums.GamePhase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

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

        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Welcome to the Risk Game!\n");
        expectedOutputBuilder.append("Before we begin, here are a few commands you can use\n\n");

        ArrayList<Command>  l_commands = Commands.PHASE_COMMANDS_MAP.get(GamePhase.STARTUP);

        for (Command command : l_commands) {
            expectedOutputBuilder.append("\t").append(command.toString()).append("\n");
        }

        String expectedOutput = expectedOutputBuilder.toString();

        assertEquals(expectedOutput, outputStreamCaptor.toString());

    }

    @org.junit.jupiter.api.Test
    void displayPhaseInstructions() {
    }
}