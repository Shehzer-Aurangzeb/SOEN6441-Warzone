package main.java.utils;

import main.java.models.Command.Command;
import main.java.constants.Commands;
import main.java.models.Enums.GamePhase;

import java.util.ArrayList;

/**
 * Utility class for displaying feedback messages to the user.
 */
public class Feedback {

    /**
     * Displays a welcome message to the user, providing information about available commands.
     */
    public static void displayWelcomeMessage() {
        System.out.println("Welcome to the Risk Game!");
        System.out.println("Before we begin, here are a few commands you can use\n");
        ArrayList<Command> l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.STARTUP);
        for(Command command:l_commands){
            System.out.println("\t"+command.toString());
        }
    }

    /**
     * Displays all available commands to the user.
     */
    public static void displayAllCommands(GamePhase p_currentPhase) {
        ArrayList<Command> l_commands;
        switch (p_currentPhase){
            case STARTUP:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.STARTUP);
                for(Command command:l_commands){
                    System.out.println("\t"+command.toString());
                }
                break;
            case ADD_PLAYERS:
                l_commands= Commands.PHASE_COMMANDS_MAP.get(GamePhase.ADD_PLAYERS);
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

}
