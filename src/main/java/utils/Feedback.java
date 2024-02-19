/**
 * Utility class for displaying feedback messages to the user.
 */
package utils;

import java.util.ArrayList;
import models.Command.Command;
import constants.Commands;
import models.Enums.GamePhase;
import models.Player.Player;


public class Feedback {
    /**
     * Displays a welcome message to the user, providing information about available commands.
     */
    public static void displayWelcomeMessage() {
        System.out.println("Welcome to the Risk Game!");
        System.out.println("Before we begin, here are a few commands you can use\n");
        System.out.println("\t-loadmap <filename>: Load a map file to start the game.");
        System.out.println("\t-showcommands: Display all available commands.");
        System.out.println("\t-exit: Exit the game.");
    }

    /**
     * Displays all available commands to the user.
     */
    public static void displayPhaseInstructions(GamePhase p_currentPhase) {
        ArrayList<Command> l_commands;
        switch (p_currentPhase){
            case MAP_EDITING:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.MAP_EDITING);
                for(Command command:l_commands){
                    System.out.println("\t"+command.toString());
                }
                break;
            case STARTUP:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.STARTUP);
                for(Command command:l_commands){
                    System.out.println("\t"+command.toString());
                }
                break;
            case ISSUE_ORDERS:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.ISSUE_ORDERS);
                for(Command command:l_commands){
                    System.out.println("\t"+command.toString());
                }
                break;
        }
    }

    /**
     * Displays a message indicating that a command does not exist or cannot be executed in the current phase.
     *
     * @param commandName The name of the command.
     * @param gamePhase The current game phase.
     */
    public static void displayCommandUnavailableMessage(String commandName, GamePhase gamePhase) {
        System.out.println("\nThe '" + commandName + "' command is not available in the " + gamePhase.toString().toUpperCase().replace("_"," ") + " phase.");
        System.out.println("Please type 'showcommands' to see the commands you can run.");
    }


    /**
     * Displays the list of players.
     *
     * @param p_players The list of players to display.
     */
    public static void displayPlayers(ArrayList<Player> p_players){
        if (p_players.isEmpty()) {
            System.out.println("\nNo players available.");
            return;
        }
        System.out.println("\nPlayers:");
        for (int i = 0; i < p_players.size(); i++) {
            System.out.println((i + 1) + ". " + p_players.get(i).getName());
        }
    }
}
