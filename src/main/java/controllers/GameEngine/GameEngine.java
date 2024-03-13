package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
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
        d_logger.log("\n============== "+ d_currentGamePhase.getPhaseName()+" Phase ==============\n");
    }

    public Phase getPhase() {
        return d_currentGamePhase;
    }

    void setCommandForTesting(String command) {
        this.d_command = command;
    }
    /**
     * Starts the game and handles command processing based on the current phase.
     */
    public void startGame() {
        this.d_currentGamePhase = new Preload(this);
        displayWelcomeMessage();
        d_logger.log("Welcome to Warzone Game!\n");
        d_logger.log("\n============== "+ d_currentGamePhase.getPhaseName()+" Phase ==============\n");
        while (d_currentGamePhase.getPhaseName() != GamePhase.ISSUE_ORDERS) {
            System.out.print("\nEnter your command: ");
            this.d_command = d_sc.nextLine().trim(); // Ensure this line is correctly assigning the input to d_command
            if (d_command != null && !d_command.isEmpty()) {
                handleCommand();
            } else {
                System.out.println("No command entered, please try again.");
            }
        }
        startMainGameLoop();
    }

    /**
     * Starts the main game loop where players issue orders and orders are executed.
     */
    private void startMainGameLoop() {
      //  d_logger.log("\n============== Issue Order Phase ==============\n");
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
    /**
     * Handles the given command processing.
     */
    public void handleCommand() {
        String commandType = d_command.split(" ")[0];
        switch (commandType) {
            case "loadmap":
                handleLoadMap();
                break;
            case "editmap":
                handleEditMap();
                break;
            case "editcontinent":
            case "editcountry":
            case "editneighbor":
                handleModifyMapComponents();
                break;
            case "savemap":
                handleSaveMap();
                break;
            case "showcommands":
                d_currentGamePhase.showCommands();
                break;
            case "showmap":
                handleShowMap();
                break;
            case "gameplayer":
                handleAddOrRemovePlayer();
                break;
            case "startgame":
                handleStartGame();
                break;
            case "proceed":
                handleProceed();
                break;
            case "exit":
                d_logger.log("Exiting the game....");
                d_currentGamePhase.exit();
                break;
            default:
                d_currentGamePhase.printInvalidCommandMessage(d_command, d_currentGamePhase.getPhaseName());
                d_logger.log("Invalid command entered.");
                break;
        }
    }

    public void handleLoadMap() {
        String filename = d_command.split(" ")[1];
        d_currentGamePhase.loadMap(filename, d_mapEditor);
    }

    public void handleEditMap() {
        String filename = d_command.split(" ")[1];
        d_currentGamePhase.editMap(filename, d_mapEditor);
    }

    public void handleModifyMapComponents() {
        d_currentGamePhase.modifyMapComponents(d_command, d_mapEditor);
    }

    public void handleSaveMap() {
        String filename = d_command.split(" ")[1];
        d_currentGamePhase.saveMap(filename, d_mapEditor);
    }

    public void handleShowMap() {
        d_currentGamePhase.showMap();
    }

    public void handleAddOrRemovePlayer() {
        d_currentGamePhase.addOrRemovePlayer(d_command, d_players);
        checkStartGamePrompt();
    }

    public void handleStartGame() {
        if (d_players.size() < 2) {
            System.out.println("\nMinimum two players required to start the game.");
        } else {
            PlayerHolder.setPlayers(d_players);
            assignCountries();
            assignReinforcements();
            System.out.println("\nReinforcements have been assigned to players.");
            d_currentGamePhase.next();
        }
    }

    public void handleProceed() {
        d_currentGamePhase.next();
    }
    /**
     * Randomly assigns countries to players.
     */
    public void assignCountries() {
        int l_numPlayers = d_players.size();
        for (int i = 0; i < MapHolder.getMap().getCountries().size(); i++) {
            d_players.get(i % l_numPlayers).addOwnedCountry(MapHolder.getMap().getCountries().get(i));
        }
        System.out.println("\nCountries have been assigned to players.");
        d_logger.log("Countries have been assigned to players.");
    }

    /**
     * Checks if there are enough players to start the game and prompts the user accordingly.
     */
    public void checkStartGamePrompt() {
        if (d_players.size() >= 2) {
            System.out.println("\nYou have sufficient players to start the game. Type 'startgame' command to begin.");
        }
    }
    /**
     * Assigns reinforcements to players based on the number of countries owned.
     */
    public static void assignReinforcements() {
        ArrayList<Player> l_existingPlayer = PlayerHolder.getPlayers();
        for (Player player : l_existingPlayer) {
            int l_armyCount = player.getOwnedCountries().size() / 3;
            if (l_armyCount < 3) l_armyCount = 3;
            player.setNoOfArmies(l_armyCount);
        }
    }


}
