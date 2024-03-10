package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Order.Advance.AdvanceOrder;
import models.Phase.MapEditing.Preload.Preload;
import models.Phase.Phase;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;
import log.LogEntryBuffer;

import java.util.ArrayList;
import java.util.Scanner;

import static utils.Feedback.*;

public class GameEngine {
    private final Scanner d_sc;
    private final MapEditor d_mapEditor;
    private String d_command;
    private final ArrayList<Player> d_players = new ArrayList<>();
    private static LogEntryBuffer d_logger;
    private Phase d_currentGamePhase;


    /**
     * Initializes the GameEngine with default settings.
     */
    public GameEngine() {
        MapHolder.setMap(new Map());
        this.d_mapEditor = new MapEditor();
        d_logger = LogEntryBuffer.getInstance();
        d_logger.clear();
        this.d_sc = new Scanner(System.in);
    }

    public void setPhase(Phase new_state) {
        d_currentGamePhase = new_state;
    }

    public Phase getPhase() {
        return d_currentGamePhase;
    }

    /**
     * Starts the game and handles command processing based on the current phase.
     */
    public void startGame() {
        //initially preload phase
        this.d_currentGamePhase = new Preload(this);
        displayWelcomeMessage();
        d_logger.log("Welcome to Warzone Game!");
        d_logger.log("\n============== Map Editing Phase ==============\n");
        while (d_currentGamePhase.getPhaseName() != GamePhase.ISSUE_ORDERS) {
            System.out.print("\nEnter your command: ");
            d_command = d_sc.nextLine().trim();
            handleCommand();
        }
        startMainGameLoop();
    }

    /**
     * Starts the main game loop where players issue orders and orders are executed.
     */
    private void startMainGameLoop() {
        d_logger.log("\n============== Issue Order Phase ==============\n");
        while (true) {
            switch (d_currentGamePhase.getPhaseName()) {
                case ISSUE_ORDERS:
                    d_currentGamePhase.issueOrders();
                    break;
                case EXECUTE_ORDERS:
                    d_currentGamePhase.executeOrders();
                    break;
            }
        }
    }

    /**
     * Handles the given command processing.
     */
    private void handleCommand() {
        String l_commandName = d_command.split(" ")[0];
        String l_filename;
        switch (l_commandName) {
            case "loadmap":
                l_filename = d_command.split(" ")[1];
                d_currentGamePhase.loadMap(l_filename, d_mapEditor);
                break;
            case "editmap":
                l_filename = d_command.split(" ")[1];
                d_currentGamePhase.editMap(l_filename, d_mapEditor);
                break;
            case "editcontinent", "editcountry", "editneighbor":
                d_currentGamePhase.modifyMapComponents(d_command, d_mapEditor);
                break;
            case "savemap":
                l_filename = d_command.split(" ")[1];
                d_currentGamePhase.saveMap(l_filename, d_mapEditor);
                break;
            case "showcommands":
                d_currentGamePhase.showCommands();
                break;
            case "showmap":
                d_currentGamePhase.showMap();
                d_logger.log("Entered showmap command.");
                break;
            case "gameplayer":
                d_currentGamePhase.addOrRemovePlayer(d_command, d_players);
                checkStartGamePrompt();
                break;
            case "startgame":
                if (d_players.size() < 2) {
                    System.out.println("\nMinimum two players required to start the game.");
                } else {
                    PlayerHolder.setPlayers(d_players);
                    assignCountries();
                    assignReinforcements();
                    System.out.println("\nReinforcements have been assigned to players.");
                    d_currentGamePhase.next();
                }
                break;
            case "proceed":
                d_logger.log("\n============== Startup Phase ==============\n");
                d_currentGamePhase.next();
                break;
            case "exit":
                d_currentGamePhase.exit();
                break;
            default:
                d_currentGamePhase.printInvalidCommandMessage(d_command, d_currentGamePhase.getPhaseName());
                d_logger.log("Invalid command entered.");
                break;
        }
    }
    /**
     * Randomly assigns countries to players.
     */
    private void assignCountries() {
        int l_numPlayers = d_players.size();
        for (int i = 0; i < MapHolder.getMap().getCountries().size(); i++) {
            d_players.get(i % l_numPlayers).addOwnedCountry(MapHolder.getMap().getCountries().get(i));
        }
        System.out.println("\nCountries have been assigned to players.");
        d_logger.log("Countries assigned to players.");
    }

    /**
     * Checks if there are enough players to start the game and prompts the user accordingly.
     */
    private void checkStartGamePrompt() {
        if (d_players.size() >= 2) {
            System.out.println("\nYou have sufficient players to start the game. Type 'startgame' command to begin.");
        }
    }
    /**
     * Assigns reinforcements to players based on the number of countries owned.
     */
    private static void assignReinforcements() {
        ArrayList<Player> l_existingPlayer = PlayerHolder.getPlayers();
        for (Player player : l_existingPlayer) {
            int l_armyCount = player.getOwnedCountries().size() / 3;
            if (l_armyCount < 3) l_armyCount = 3;
            player.setNoOfArmies(l_armyCount);
        }
    }

}
