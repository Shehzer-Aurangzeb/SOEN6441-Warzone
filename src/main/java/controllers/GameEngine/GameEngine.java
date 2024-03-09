/**
 * The GameEngine class manages the game phases and controls the flow of the game.
 * It handles commands from the user, transitions between different phases, and executes game logic.
 */
package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Phase.MapEditing.Preload.Preload;
import models.Phase.Phase;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;


import java.util.ArrayList;
import java.util.Scanner;

import static utils.Feedback.*;

public class GameEngine {
    private final Scanner d_sc;
    private Phase d_currentGamePhase;
    MapEditor d_mapEditor;
    String d_command;
    ArrayList<Player> d_players = new ArrayList<>();

    /**
     * Initializes the GameEngine with default settings.
     */
    public GameEngine() {
        MapHolder.setMap(new Map());
        this.d_mapEditor = new MapEditor();
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
        while (true) {
            switch (d_currentGamePhase.getPhaseName()){
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
                d_currentGamePhase.next();
                break;
            case "exit":
                d_currentGamePhase.exit();
                break;
            default:
                d_currentGamePhase.printInvalidCommandMessage(d_command, d_currentGamePhase.getPhaseName());
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
