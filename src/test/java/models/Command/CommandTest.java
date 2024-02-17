package models.Command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testToString_NoArguments() {
        // Create a command without arguments
        Command command = new Command("loadmap", "Load a map file to start the game.");

        // Check if toString returns the correct string representation
        assertEquals("-loadmap: Load a map file to start the game.", command.toString());
    }
    @Test
    void testToString_WithArguments() {
        // Create a command with arguments
        Command command = new Command("loadmap", "Load a map file to start the game.", new String[]{"filename"});

        // Check if toString returns the correct string representation
        assertEquals("-loadmap <filename>: Load a map file to start the game.", command.toString());
    }
}