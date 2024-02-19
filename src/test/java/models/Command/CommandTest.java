package models.Command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @Test
    void testToString_NoArguments() {
        // Create a command without arguments
        Command command = new Command("loadmap", "Load a map file to start the game.");


        // Check if toString returns the correct string representation
        assertEquals("-loadmap: Load a map file to start the game.", command.toString());
    }
}