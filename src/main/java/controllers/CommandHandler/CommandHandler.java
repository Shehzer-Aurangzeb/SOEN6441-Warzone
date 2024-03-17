/**
 * The CommandHandler class handles different commands and operations in the game.
 * It includes methods for loading a map, exiting the game, displaying commands, managing players,
 * issuing orders, and executing orders.
 */
package controllers.CommandHandler;

import log.LogEntryBuffer;
import models.Enums.GamePhase;

import static utils.Feedback.*;

public class CommandHandler {

    /**
     * Handles the exit command by gracefully exiting the game.
     */
    public static void handleExitCommand() {
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }

    /**
     * Displays the available commands based on the current game phase.
     *
     * @param p_gamePhase The current game phase.
     */
    public static void handleDisplayCommands(GamePhase p_gamePhase) {
        displayPhaseInstructions(p_gamePhase);
    }
}
