package main.java.controllers.GameEngine;

import main.java.controllers.MapEditor.MapEditor;
import main.java.models.Map.Map;
import main.java.models.Player.Player;

import static main.java.views.MapView.MapView.*;


import static main.java.utils.Feedback.*;
import static adapters.File.FileAdapter.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {
    Scanner sc = new Scanner(System.in);
    Map gameMap;
    MapEditor mapEditor;
    String command;
    ArrayList<Player> players = new ArrayList<>();

    public void startGame() {
        displayWelcomeMessage();
        while (true) {
            command = sc.nextLine();
            if (command.equals("showcommands")) {
                displayAllCommands();
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
                    displayMapInformation(gameMap);
                } catch (Exception e) {
                    System.out.println("Map is not loaded. Please load the map first.");
                    continue;
                }
            }
            if (command.startsWith("gameplayer -add")) {
                Player player = new Player();
                String l_playerName = command.split(" ")[2];
                String all_playerName = "";
                player.setName(l_playerName);
                players.add(player);
                System.out.println("Player " + l_playerName + " added.");
                for (Player p : players) {
                    all_playerName = all_playerName.concat(p.getName());
                    all_playerName = all_playerName.concat(", ");
                }
                all_playerName = all_playerName.substring(0, all_playerName.length() - 2);
                System.out.println("Player List: " + all_playerName);
            }
            if (command.startsWith("gameplayer -remove")) {
                String l_playerName = command.split(" ")[2];
                String all_playerName = "";
                players.removeIf(p -> p.getName().equals(l_playerName));
                System.out.println("Player " + l_playerName + " removed.");
                for (Player p : players) {
                    all_playerName = all_playerName.concat(p.getName());
                    all_playerName = all_playerName.concat(", ");
                }
                all_playerName = all_playerName.substring(0, all_playerName.length() - 2);
                System.out.println("Player List: " + all_playerName);
            }
                if (command.equals("exit")) {
                    break;
                }

        }
    }
}
