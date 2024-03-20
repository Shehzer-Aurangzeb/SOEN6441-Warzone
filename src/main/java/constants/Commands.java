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
        ArrayList<Command> l_mapEditingPreloadCommands = new ArrayList<>();

        l_mapEditingPreloadCommands.add(new Command("loadmap",
                "Load a map file to start the game.",
                new String[]{"filename"}));
        l_mapEditingPreloadCommands.add(new Command("editmap",
                "Load an existing 'domination' map file or create a new map.",
                new String[]{"filename"}));
        l_mapEditingPreloadCommands.add(new Command("validatemap","Verify the correctness of map."));

        l_mapEditingPreloadCommands.add(new Command("showcommands","Display all available commands."));
        l_mapEditingPreloadCommands.add(new Command("showmap","Display the current state of the game map."));
        l_mapEditingPreloadCommands.add(new Command("proceed","Proceed to the next phase of the game."));
        l_mapEditingPreloadCommands.add(new Command("exit","Exit the game."));

        PHASE_COMMANDS_MAP.put(GamePhase.MAP_EDITING_PRELOAD,l_mapEditingPreloadCommands);

        //add preload commands in postload as well
        ArrayList<Command> l_mapEditingPostloadCommands = new ArrayList<>(l_mapEditingPreloadCommands);

        l_mapEditingPostloadCommands.add(new Command("editcontinent -add",
                "Add a new continent to the map.",
                new String[]{"continentID","continentvalue"}));
        l_mapEditingPostloadCommands.add(new Command("editcontinent -remove",
                "Remove a continent from the map.",
                new String[]{"continentID"}));
        l_mapEditingPostloadCommands.add(new Command("editcountry -add",
                "Add a new country to the map.",
                new String[]{"countryID","continentID"}));
        l_mapEditingPostloadCommands.add(new Command("editcountry -remove",
                "Remove a country from the map.",
                new String[]{"countryID"}));
        l_mapEditingPostloadCommands.add(new Command("editneighbor -add",
                "Add a neighboring connection between countries.",
                new String[]{"countryID","neighborcountryID"}));
        l_mapEditingPostloadCommands.add(new Command("editneighbor -remove",
                "Remove a neighboring connection between countries.",
                new String[]{"countryID","neighborcountryID"}));
        l_mapEditingPostloadCommands.add(new Command("savemap","Save a map to a text file exactly as edited.",new String[]{"filename"}));

        PHASE_COMMANDS_MAP.put(GamePhase.MAP_EDITING_POSTLOAD,l_mapEditingPostloadCommands);
        //temporary
        PHASE_COMMANDS_MAP.put(GamePhase.MAP_EDITING,l_mapEditingPostloadCommands);


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
        l_issueOrders.add(new Command("advance", "Move armies between territories.", new String[]{"sourceCountryID", "targetCountryID", "num"}));
        l_issueOrders.add(new Command("bomb", "Destroy half of opponent's armies on an adjacent territory.", new String[]{"opponentCountryID"}));
        l_issueOrders.add(new Command("blockade", "Triple armies on a territory and make it neutral.", new String[]{"countryID"}));
        l_issueOrders.add(new Command("airlift", "Move armies between any two territories.", new String[]{"sourceCountryID", "targetCountryID", "num"}));
        l_issueOrders.add(new Command("negotiate", "Prevent attacks between players until the end of the turn.", new String[]{"opponentPlayerID"}));
        l_issueOrders.add(new Command("showcommands","Display all available commands."));
        l_issueOrders.add(new Command("showarmies","Displays the number of armies owned by the current player."));
        l_issueOrders.add(new Command("showmap","Display the current state of the game map."));
        l_issueOrders.add(new Command("endturn","End your turn without issuing any more orders for this round."));
        l_issueOrders.add(new Command("mymap","Display the current state of the game map for the current player."));
        l_issueOrders.add(new Command("exit","Exit the game."));
        PHASE_COMMANDS_MAP.put(GamePhase.ISSUE_ORDERS,l_issueOrders);
    }
}
