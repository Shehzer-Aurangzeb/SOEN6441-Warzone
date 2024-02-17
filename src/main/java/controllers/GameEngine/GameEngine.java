package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Player.Player;


import java.util.ArrayList;
import java.util.Scanner;

import static controllers.CommandHandler.CommandHandler.*;
import static utils.Feedback.*;
import static utils.Command.*;
import static views.MapView.MapView.displayMapInformation;

public class GameEngine {
    private Scanner d_sc;
    private GamePhase d_currentPhase;
    MapEditor d_mapEditor;
    String d_command;

    ArrayList<Player> d_players = new ArrayList<>();

    public GameEngine(){
        MapHolder.setMap(new Map());
        this.d_mapEditor= new MapEditor();
        this.d_currentPhase= GamePhase.STARTUP;
        this.d_sc = new Scanner(System.in);
    }
    public void startGame() {
        displayWelcomeMessage();
        while (true) {
            d_command = d_sc.nextLine().trim();
            String l_commandName= d_command.split(" ")[0];
            if(!isCommandValidForPhase(l_commandName,d_currentPhase)){
                displayCommandUnavailableMessage(l_commandName,d_currentPhase);
                continue;
            }
            handleCommand();
        }
//        while (true) {
//            command = sc.nextLine();
//            if (command.equals("showcommands")) {
////                displayAllCommands();
//            }
//            if (command.startsWith("loadmap")) {
//                String l_fileName = command.split(" ")[1];
//                File l_file = isFileExists(l_fileName);
//                if (l_file != null) {
//                    gameMap = new Map();
//                    mapEditor = new MapEditor(gameMap);
//                    try {
//                        mapEditor.loadMap(l_file);
//                    } catch (FileNotFoundException e) {
//                        System.out.println("File not found.");
//                        System.exit(0);
//                    } catch (IOException e) {
//                        System.out.println("IO exception.");
//                        System.exit(0);
//                    }
//                }
//                System.out.println(gameMap.getCountries().toString());
//            }
//            if (command.equals("showmap")) {
//                try {
//                    displayMapInformation(gameMap);
//                } catch (Exception e) {
//                    System.out.println("Map is not loaded. Please load the map first.");
//                    continue;
//                }
//            }
//            if (command.startsWith("gameplayer -add")) {
//                String l_playerName = command.split(" ")[2];
//                String all_playerName = "";
//                Player player = new Player(l_playerName);
//                players.add(player);
//                System.out.println("Player " + l_playerName + " added.");
//                for (Player p : players) {
//                    all_playerName = all_playerName.concat(p.getName());
//                    all_playerName = all_playerName.concat(", ");
//                }
//                all_playerName = all_playerName.substring(0, all_playerName.length() - 2);
//                System.out.println("Player List: " + all_playerName);
//            }
//            if (command.startsWith("gameplayer -remove")) {
//                String l_playerName = command.split(" ")[2];
//                String all_playerName = "";
//                players.removeIf(p -> p.getName().equals(l_playerName));
//                System.out.println("Player " + l_playerName + " removed.");
//                for (Player p : players) {
//                    all_playerName = all_playerName.concat(p.getName());
//                    all_playerName = all_playerName.concat(", ");
//                }
//                all_playerName = all_playerName.substring(0, all_playerName.length() - 2);
//                System.out.println("Player List: " + all_playerName);
//            }
//            if (command.equals("assigncountries")) {
//                try {
//                    assignCountries();;
//                } catch (Exception e) {
//                    System.out.println("Invalid command. Please try again.");
//                    continue;
//                }
//            }
//
//
//            if (command.equals("exit")) {
//                break;
//            }
//
//        }
    }

    private void handleCommand() {
        switch (d_currentPhase) {
            case STARTUP:
                processStartupPhaseCommand();
                break;
            case ADD_PLAYERS:
                processAddPlayersCommands();
                break;
            case ISSUE_ORDERS:
                processIssueOrdersCommands();
                break;
            default:
                String l_commandName= d_command.split(" ")[0];
                displayCommandUnavailableMessage(l_commandName, d_currentPhase);
                break;
        }
    }
    private void processStartupPhaseCommand(){
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
            case "exit":
                handleExitCommand();
                break;
        }
    }
    private void processAddPlayersCommands(){
        String l_commandName= d_command.split(" ")[0];
        switch(l_commandName){
            case "showcommands":
                handleDisplayCommands(d_currentPhase);
            case "exit":
                handleExitCommand();
                break;

        }
    }
    private void processIssueOrdersCommands(){
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
        System.out.println("Countries have been assigned to players.");
    }


}
