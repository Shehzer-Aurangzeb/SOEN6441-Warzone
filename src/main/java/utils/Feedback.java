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
        printWelcomeMessageWithBanner("Welcome to the Risk Game!");
        System.out.println("Before we begin, here are a few commands you can use\n");
        System.out.println("\t-loadmap <filename>: Load a map file to start the game.");
        System.out.println("\t-showcommands: Display all available commands.");
        System.out.println("\t-exit: Exit the game.");
    }
    public static void printWelcomeMessageWithBanner(String p_message){
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
        ArrayList<Command> l_commands;
        switch (p_currentPhase){
            case MAP_EDITING:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.MAP_EDITING);
                for(Command command:l_commands){
                    System.out.println("\t"+command.toString());
                }
                break;
            case MAP_EDITING_PRELOAD:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.MAP_EDITING_PRELOAD);
                for(Command command:l_commands){
                    System.out.println("\t"+command.toString());
                }
                break;
            case MAP_EDITING_POSTLOAD:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.MAP_EDITING_POSTLOAD);
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
     * @param p_command The name of the command.
     * @param p_gamePhase The current game phase.
     */
    public static void displayCommandUnavailableMessage(String p_command, GamePhase p_gamePhase) {
        String l_commandName=p_command.split(" ")[0];
        System.out.println("\nThe '" + l_commandName + "' command is not available in the " + p_gamePhase.getName()+ " phase.");
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
