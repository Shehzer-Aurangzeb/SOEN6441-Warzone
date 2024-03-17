/**
 * Utility class for displaying feedback messages to the user.
 */
package utils;

import java.util.ArrayList;

import models.Command.Command;
import constants.Commands;
import models.Enums.GamePhase;
import models.GameContext.GameContext;


public class Feedback {
    private static final GameContext d_ctx = GameContext.getInstance();

    /**
     * Displays a welcome message to the user, providing information about available commands.
     */
    public static void displayWelcomeMessage() {
        printWelcomeMessageWithBanner("Welcome to the Risk Game!");
        System.out.println("Before we begin, here are a few commands you can use\n");
        System.out.println("\t-loadmap <filename>: Load a map file to start the game.");
        System.out.println("\t-showcommands: Display all available commands.");
        System.out.println("\t-exit: Exit the game.");
    }

    public static void printWelcomeMessageWithBanner(String p_message) {
        int totalWidth = 60;
        int padding = (totalWidth - p_message.length() - 4) / 2;
        System.out.println("*".repeat(totalWidth));
        System.out.println("\n*" + " ".repeat(padding) + " " + p_message + " " + " ".repeat(padding) + "*\n");
        System.out.println("*".repeat(totalWidth));
    }

    /**
     * Displays all available commands to the user.
     */
    public static void displayPhaseInstructions(GamePhase p_currentPhase) {
        System.out.println(""); //to skip one line.
        ArrayList<Command> l_commands = Commands.PHASE_COMMANDS_MAP.get(p_currentPhase);
        if (l_commands != null) {
            for (Command command : l_commands) {
                System.out.println("\t" + command.toString());
            }
        } else {
            System.out.println("No commands available for phase: " + p_currentPhase);
        }
    }

    /**
     * Displays a message indicating that a command does not exist or cannot be executed in the current phase.
     *
     * @param p_command   The name of the command.
     * @param p_gamePhase The current game phase.
     */
    public static void displayCommandUnavailableMessage(String p_command, GamePhase p_gamePhase) {
        String l_commandName = p_command.split(" ")[0];

        System.out.println("\nThe '" + l_commandName + "' command is not available in the " + p_gamePhase.getName() + " phase.");
        System.out.println("Please type 'showcommands' to see the commands you can run.");
    }

    /**
     * Displays the list of players.
     */
    public static void displayPlayers() {
        if (d_ctx.getGamePlayers().isEmpty()) {
            System.out.println("\nNo players available.");
            return;
        }
        System.out.println("\nPlayers:");
        for (int i = 0; i < d_ctx.getGamePlayers().size(); i++) {
            System.out.println((i + 1) + ". " + d_ctx.getGamePlayers().get(i).getName());
        }
    }
}
