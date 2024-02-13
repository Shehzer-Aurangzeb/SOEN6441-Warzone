package main.java.models.Commands;

/**
 * The Commands class provides predefined commands for the game.
 */
public class Commands {

    /** Array of predefined command names. */
    private static final String[] commandNames = {"loadmap", "showcommands", "exit"};

    /** Array of descriptions corresponding to predefined commands. */
    private static final String[] commandDescriptions = {
            "Load a map file to start the game.",
            "Display all available commands.",
            "Exit the game."
    };

    /**
     * Get a formatted string containing all available commands and their descriptions.
     *
     * @return A string containing all available commands and their descriptions.
     */
    public static String getAllCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commandNames.length; i++) {
            stringBuilder.append("- ").append(commandNames[i]).append(": ").append(commandDescriptions[i]).append("\n");
        }
        return stringBuilder.toString();
    }

}
