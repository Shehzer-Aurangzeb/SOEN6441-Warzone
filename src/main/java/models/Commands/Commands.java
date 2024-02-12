package main.java.models.Commands;

public class Commands {
    private static final String[] commandNames = {"loadmap", "showcommands", "exit"};
    private static final String[] commandDescriptions = {"Load a map file to start the game.", "Display all available commands.", "Exit the game."};

    public static String getAllCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commandNames.length; i++) {
            stringBuilder.append("-").append(commandNames[i]).append(": ").append(commandDescriptions[i]).append("\n");
        }
        return stringBuilder.toString();
    }

}
