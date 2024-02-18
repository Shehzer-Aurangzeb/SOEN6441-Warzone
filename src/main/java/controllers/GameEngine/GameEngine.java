/**
 * The GameEngine class manages the game phases and controls the flow of the game.
 * It handles commands from the user, transitions between different phases, and executes game logic.
 */
package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Map.MapValidator;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;


import java.util.ArrayList;
import java.util.Scanner;

import static controllers.CommandHandler.CommandHandler.*;
import static utils.Feedback.*;
import static utils.Command.*;
import static views.MapView.MapView.displayMapInformation;
import static views.MapView.PlayerView.*;

public class GameEngine {
    private Scanner d_sc;
    private GamePhase d_currentPhase;
    MapEditor d_mapEditor;
    String d_command;
    int MIN_ARMIES_PER_PLAYER=3;
    ArrayList<Player> d_players = new ArrayList<>();

    /**
     * Initializes the GameEngine with default settings.
     */

    public GameEngine(){
        MapHolder.setMap(new Map());
        this.d_mapEditor= new MapEditor();
        this.d_currentPhase= GamePhase.MAP_EDITING;
        this.d_sc = new Scanner(System.in);
    }
    /**
     * Starts the game and handles command processing based on the current phase.
     */
    public void startGame() {
        displayWelcomeMessage();
        while (true) {
            if (d_currentPhase == GamePhase.ISSUE_ORDERS) break;
            System.out.print("\nEnter your command: ");
            d_command = d_sc.nextLine().trim();
            String l_commandName = d_command.split(" ")[0];
            if (!isCommandValidForPhase(l_commandName, d_currentPhase)) {
                displayCommandUnavailableMessage(l_commandName, d_currentPhase);
                continue;



//            if (command.equals("validatemap")) {
//                try {
//                    boolean isValidMap = MapValidator.validateMap(gameMap);
//
//                    if (isValidMap) {
//                        System.out.println("The map is valid.");
//                    } else {
//                        System.out.println("The map is not valid.");
//                    }
//                } catch (Exception e) {
//                    System.out.println("Error!!");
//                    continue;
//                }
//            }
            }
            handleCommand();
        }
        startMainGameLoop();
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
                processIssueOrdersCommand();
                break;
            default:
                String l_commandName= d_command.split(" ")[0];
                displayCommandUnavailableMessage(l_commandName, d_currentPhase);
                break;
        }
    }
    /**
     * Processes commands during the map editing phase.
     */
    private void processMapEditingPhaseCommand(){
        String l_commandName= d_command.split(" ")[0];
        switch(l_commandName){
            case "loadmap":
                handleLoadMapCommand(d_command,d_mapEditor);
                break;
            case "showcommands":
                handleDisplayCommands(d_currentPhase);
                break;
            case "showmap":
                displayMapInformation();
                break;
            case "proceed":
                //check if map is valid if true change phase
                d_currentPhase= GamePhase.STARTUP;
                System.out.println("\nYou have entered the Startup Phase. Please create players to proceed further.");
                System.out.println("Use 'showcommands' to see to see how you can add players");
                break;
            case "exit":
                handleExitCommand();
                break;
        }
    }
    /**
     * Processes commands during the startup phase.
     */
    private void processStartupPhaseCommand(){
        String l_commandName= d_command.split(" ")[0];

        switch(l_commandName){
            case "gameplayer":
                handleGamePlayerCommand(d_command,d_players);
                checkStartGamePrompt();
                break;
            case "startgame":
                if(d_players.size()<2){
                    System.out.println("\nMinimum two players required to start the game.");
                }else{
                    assignCountries();
                    assignReinforcements();
                    PlayerHolder.setPlayers(d_players);
                    d_currentPhase=GamePhase.ISSUE_ORDERS;
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
    private void processIssueOrdersCommand(){
        String l_commandName= d_command.split(" ")[0];
        switch(l_commandName){
            case "showcommands":
                handleDisplayCommands(d_currentPhase);
            case "exit":
                handleExitCommand();
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
     * Assigns reinforcements to players based on the number of countries owned.
     */
    private void assignReinforcements() {
        for(Player player:d_players) {
            int l_armyCount = player.getOwnedCountries().size()/3;
            if(l_armyCount<MIN_ARMIES_PER_PLAYER) l_armyCount=MIN_ARMIES_PER_PLAYER;
            player.setNoOfArmies(l_armyCount);
        }
        System.out.println("\nReinforcements have been assigned to players.");
    }
    /**
     * Checks if there are enough players to start the game and prompts the user accordingly.
     */
    private void checkStartGamePrompt(){
        if (d_players.size() >= 2) {
            System.out.println("\nYou have sufficient players to start the game. Type 'startgame' command to begin.");
        }
    }
    /**
     * Starts the main game loop where players issue orders and orders are executed.
     */
    private void startMainGameLoop(){
        while (true){
            handleIssueOrder(d_players);
            handleExecuteOrder();
        }
    }
}
