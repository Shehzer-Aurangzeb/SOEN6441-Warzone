package constants;

import models.Command.Command;
import models.Enums.GamePhase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Constants class stores constants related to game phases, including commands for each phase.
 * It provides access to predefined commands for different stages of the game.
 */
public class Commands {
    /**
     * Mapping of game phases to commands.
     */
    public static final HashMap<GamePhase, ArrayList<Command>> PHASE_COMMANDS_MAP= new HashMap<>();
    static {
        // Commands for the startup phase
        ArrayList<Command> l_mapEditingCommands = new ArrayList<>();
        l_mapEditingCommands.add(new Command("loadmap",
                "Load a map file to start the game.",
                new String[]{"filename"}));
        l_mapEditingCommands.add(new Command("editmap",
                "Load an existing 'domination' map file or create a new map.",
                new String[]{"filename"}));
        l_mapEditingCommands.add(new Command("editcontinent -add",
                "Add a new continent to the map.",
                new String[]{"continentID","continentvalue"}));
        l_mapEditingCommands.add(new Command("editcontinent -remove",
                "Remove a continent from the map.",
                new String[]{"continentID"}));
        l_mapEditingCommands.add(new Command("editcountry -add",
                "Add a new country to the map.",
                new String[]{"countryID","continentID"}));
        l_mapEditingCommands.add(new Command("editcountry -remove",
                "Remove a country from the map.",
                new String[]{"countryID"}));
        l_mapEditingCommands.add(new Command("editneighbor -add",
                "Add a neighboring connection between countries.",
                new String[]{"countryID","neighborcountryID"}));
        l_mapEditingCommands.add(new Command("editneighbor -remove",
                "Remove a neighboring connection between countries.",
                new String[]{"countryID","neighborcountryID"}));
        l_mapEditingCommands.add(new Command("savemap","Save a map to a text file exactly as edited.",new String[]{"filename"}));
        l_mapEditingCommands.add(new Command("showcommands","Display all available commands."));
        l_mapEditingCommands.add(new Command("showmap","Display the current state of the game map."));
        l_mapEditingCommands.add(new Command("proceed","Proceed to the next phase of the game."));
        l_mapEditingCommands.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.MAP_EDITING,l_mapEditingCommands);

        // Commands for the add players phase
        ArrayList<Command> l_startupCommands = new ArrayList<>();
        l_startupCommands.add(new Command("gameplayer -add","Add a player with the specified name to the game.",new String[]{"playername"}));
        l_startupCommands.add(new Command("gameplayer -remove","Remove the player with the specified name from the game",new String[]{"playername"}));
        l_startupCommands.add(new Command("showcommands","Display all available commands."));
        l_startupCommands.add(new Command("startgame","Transition to the next phase and begin the game."));
        l_startupCommands.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.STARTUP,l_startupCommands);

        // Commands for the issue order phase
        ArrayList<Command> l_issueOrders = new ArrayList<>();
        l_issueOrders.add(new Command("deploy","Deploy a specified number of armies to a country.",new String[]{"countryID","num"}));
        l_issueOrders.add(new Command("showcommands","Display all available commands."));
        l_issueOrders.add(new Command("showarmies","Displays the number of armies owned by the current player."));
        l_issueOrders.add(new Command("showmap","Display the current state of the game map."));
        l_issueOrders.add(new Command("endturn","End your turn without issuing any more orders for this round."));
        l_issueOrders.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.ISSUE_ORDERS,l_issueOrders);
    }
}
