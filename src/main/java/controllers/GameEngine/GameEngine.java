package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.Map.MapValidator;
import models.MapHolder.MapHolder;
import models.Order.Deploy.AdvanceOrder;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;

import java.util.ArrayList;
import java.util.Scanner;

import static controllers.CommandHandler.CommandHandler.*;
import static models.Player.Player.getCurrentPlayer;
import static utils.Feedback.*;
import static utils.Command.*;
import static views.MapView.MapView.displayMapInformation;

public class GameEngine {
    private Scanner d_sc;
    private GamePhase d_currentPhase;
    private MapEditor d_mapEditor;
    private String d_command;
    private ArrayList<Player> d_players = new ArrayList<>();

    /**
     * Initializes the GameEngine with default settings.
     */
    public GameEngine() {
        MapHolder.setMap(new Map());
        this.d_mapEditor = new MapEditor();
        this.d_currentPhase = GamePhase.MAP_EDITING;
        this.d_sc = new Scanner(System.in);
    }

    /**
     * Starts the game and handles command processing based on the current phase.
     */
    public void startGame() {
        displayWelcomeMessage();
        while (d_currentPhase != GamePhase.ISSUE_ORDERS) {
            System.out.print("\nEnter your command: ");
            d_command = d_sc.nextLine().trim();
            String l_commandName = d_command.split(" ")[0];
            if (!isCommandValidForPhase(l_commandName, d_currentPhase)) {
                displayCommandUnavailableMessage(l_commandName, d_currentPhase);
                continue;
            }
            handleCommand();
        }
        startMainGameLoop();
    }

    /**
     * Starts the main game loop where players issue orders and orders are executed.
     */
    private void startMainGameLoop() {
        while (true) {
            handleIssueOrder();
            handleExecuteOrder();
        }
    }

    /**
     * Handles the command processing based on the current phase of the game.
     */
    private void handleCommand() {
        switch (d_currentPhase) {
            case MAP_EDITING:
                processMapEditingPhaseCommand();
                break;
            case STARTUP:
                processStartupPhaseCommand();
                break;
            case ISSUE_ORDERS:
                processIssueOrdersPhaseCommand();
                break;
            default:
                String l_commandName = d_command.split(" ")[0];
                displayCommandUnavailableMessage(l_commandName, d_currentPhase);
                break;
        }
    }

    /**
     * Processes commands during the map editing phase.
     */
    private void processMapEditingPhaseCommand() {
        Map gameMap = MapHolder.getMap();
        String l_commandName = d_command.split(" ")[0];
        switch (l_commandName) {
            case "loadmap":
                handleLoadMapCommand(d_command, d_mapEditor);
                break;
            case "editmap":
                handleEditMapCommand(d_command, d_mapEditor);
                break;
            case "editcontinent", "editcountry", "editneighbor":
                handleEditMapElementsCommand(d_command, d_mapEditor);
                break;
            case "savemap":
                handleSaveMapCommand(d_command, d_mapEditor);
                break;
            case "showcommands":
                handleDisplayCommands(d_currentPhase);
                break;
            case "showmap":
                displayMapInformation();
                break;
            case "proceed":
                if (MapValidator.validateMap(gameMap)) {
                    d_currentPhase = GamePhase.STARTUP;
                    System.out.println("\nYou have entered the Startup Phase. Please create players to proceed further.");
                    System.out.println("Use 'showcommands' to see to see how you can add players");
                } else {
                    System.out.println("The map is not valid. Please load a valid map.\n");
                }
                break;
            case "exit":
                handleExitCommand();
                break;
        }
    }

    /**
     * Processes commands during the startup phase.
     */
    private void processStartupPhaseCommand() {
        String l_commandName = d_command.split(" ")[0];

        switch (l_commandName) {
            case "gameplayer":
                handleGamePlayerCommand(d_command, d_players);
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
                    d_currentPhase = GamePhase.ISSUE_ORDERS;
                    System.out.println("\nThe game has started! It's time to issue your orders.");
                }
                break;
            case "showcommands":
                handleDisplayCommands(d_currentPhase);
                break;
            case "exit":
                handleExitCommand();
                break;

        }
    }

    /**
     * Processes commands during the issue orders phase.
     */
    private void processIssueOrdersPhaseCommand() {
        String l_commandName = d_command.split(" ")[0];
        switch (l_commandName) {
            case "deploy":
                handleDeployOrder(d_command);
                break;
            case "advance":
                handleAdvanceOrder(d_command);
                break;
            case "bomb":
                handleBombOrder(d_command);
                break;
            case "blockade":
                handleBlockadeOrder(d_command);
                break;
            default:
                displayCommandUnavailableMessage(l_commandName, d_currentPhase);
                break;
        }
    }

    // Add methods to handle specific order commands here

    /**
     * Handles the deploy order command.
     *
     * @param p_command The deploy order command.
     */
    private void handleDeployOrder(String p_command) {
        // Delegate to the CommandHandler
        handleDeployOrder(p_command);
    }

    /**
     * Handles the advance order command.
     *
     * @param p_command The advance order command.
     */
    private void handleAdvanceOrder(String p_command) {
        String[] commandParts = p_command.split(" ");
        if (commandParts.length != 4) {
            System.out.println("Invalid advance order command format. Usage: advance countryFrom countryTo numArmies");
            return;
        }
        try {
            String countryFrom = commandParts[1];
            String countryTo = commandParts[2];
            int numArmies = Integer.parseInt(commandParts[3]);
            Player currentPlayer = getCurrentPlayer();
            if (currentPlayer != null) {
                AdvanceOrder advanceOrder = new AdvanceOrder(countryFrom, countryTo, numArmies);
                currentPlayer.issueOrder(advanceOrder); // Implement issueOrder method in Player class
                System.out.println("Advance order issued successfully.");
            } else {
                System.out.println("No active player found to issue the advance order.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies. Please provide an integer.");
        }
    }

    /**
     * Handles the bomb order command.
     *
     * @param p_command The bomb order command.
     */
    private void handleBombOrder(String p_command) {
        // Delegate to the CommandHandler or implement the logic here
    }

    /**
     * Handles the blockade order command.
     *
     * @param p_command The blockade order command.
     */
    private void handleBlockadeOrder(String p_command) {
        // Delegate to the CommandHandler or implement the logic here
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
    }

    /**
     * Checks if there are enough players to start the game and prompts the user accordingly.
     */
    private void checkStartGamePrompt() {
        if (d_players.size() >= 2) {
            System.out.println("\nYou have sufficient players to start the game. Type 'startgame' command to begin.");
        }
    }
}
