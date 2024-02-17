package constants;

import models.Command.Command;
import models.Enums.GamePhase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Constants class that stores commands for each game phase.
 */
public class Commands {
    /**
     * Mapping of game phases to commands.
     */
    public static final HashMap<GamePhase, ArrayList<Command>> PHASE_COMMANDS_MAP= new HashMap<>();
    static {
        // Commands for the startup phase
        ArrayList<Command> l_startupCommands = new ArrayList<>();
        l_startupCommands.add(new Command("loadmap","Load a map file to start the game.", new String[]{"filename"}));
        l_startupCommands.add(new Command("showcommands","Display all available commands."));
        l_startupCommands.add(new Command("showmap","Display the current state of the game map."));
        l_startupCommands.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.STARTUP,l_startupCommands);

        // Commands for the add players phase
        ArrayList<Command> l_addPlayers = new ArrayList<>();
        l_addPlayers.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.ADD_PLAYERS,l_addPlayers);

        // Commands for the issue order phase
        ArrayList<Command> l_issueOrders = new ArrayList<>();
        l_issueOrders.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.ISSUE_ORDERS,l_issueOrders);
    }
}
