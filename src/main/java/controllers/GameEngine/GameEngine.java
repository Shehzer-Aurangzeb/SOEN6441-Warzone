package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.Map.MapValidator;
import models.Player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static adapters.FileAdapter.FileAdapter.*;
import static utils.Feedback.*;
import static views.MapView.MapView.displayMapInformation;
import static views.MapView.PlayerView.*;

public class GameEngine {
    private Scanner sc;
    private GamePhase d_currentPhase;
    Map gameMap;
    MapEditor mapEditor;
    String command;
    ArrayList<Player> players = new ArrayList<>();

    public GameEngine(){
        this.d_currentPhase= GamePhase.STARTUP;
        this.sc = new Scanner(System.in);
    }
    public void startGame() {
        displayWelcomeMessage();
        while (true) {
            command = sc.nextLine();
            if (command.equals("showcommands")) {
//                displayAllCommands();
            }
            if (command.startsWith("loadmap")) {
                String l_fileName = command.split(" ")[1];
                File l_file = isFileExists(l_fileName);
                if (l_file != null) {
                    gameMap = new Map();
                    mapEditor = new MapEditor(gameMap);
                    try {
                        mapEditor.loadMap(l_file);
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                        System.exit(0);
                    } catch (IOException e) {
                        System.out.println("IO exception.");
                        System.exit(0);
                    }
                }
                System.out.println(gameMap.getCountries().toString());
            }
            if (command.equals("showmap")) {
                try {
                    if(players.isEmpty())
                        displayMapInformation(gameMap);
                    else
                        displayPlayerList(players, gameMap);
                } catch (Exception e) {
                    System.out.println("Map is not loaded. Please load the map first.");
                    continue;
                }
            }
            if (command.equals("validatemap")) {
                try {
                    boolean isValidMap = MapValidator.validateMap(gameMap);

                    if (isValidMap) {
                        System.out.println("The map is valid.");
                    } else {
                        System.out.println("The map is not valid.");
                    }
                } catch (Exception e) {
                    System.out.println("Error!!");
                    continue;
                }
            }
            if (command.startsWith("gameplayer -add")) {
                String[] l_playerNames = command.substring(command.indexOf("-add") + 5).trim().split(",+");
                for (String l_playerName : l_playerNames) {
                    Player player = new Player(l_playerName.trim());
                    players.add(player);
                }
                System.out.print("Player List: ");
                for (Player p : players) {
                    System.out.print( p.getName());
                    if (players.indexOf(p) < players.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
            if (command.startsWith("gameplayer -remove")) {
                String[] l_playerNames = command.substring(command.indexOf("-remove") + 8).trim().split(",");
                for (String l_playerName : l_playerNames) {
                    players.removeIf(p -> p.getName().equals(l_playerName));
                }
                System.out.print("Player List: ");
                for (Player p : players) {
                    System.out.print( p.getName());
                    if (players.indexOf(p) < players.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
            if (command.equals("assigncountries")) {
                try {
                    assignCountries();;
                } catch (Exception e) {
                    System.out.println("Invalid command. Please try again.");
                    continue;
                }
            }


            if (command.equals("exit")) {
                break;
            }

        }
    }
    /**
     * Randomly assigns countries to players.
     */
    private void assignCountries() {
        Collections.shuffle(gameMap.getCountries()); // Assuming Map class has a method getCountries()
        int l_numPlayers = players.size();
        for (int i = 0; i < gameMap.getCountries().size(); i++) {
            players.get(i % l_numPlayers).addOwnedCountry(gameMap.getCountries().get(i));
        }
        System.out.println("Countries have been assigned to players.");
    }
}
