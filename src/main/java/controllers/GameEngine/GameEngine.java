package main.java.controllers.GameEngine;

import main.java.controllers.MapEditor.MapEditor;
import main.java.models.Country.Country;
import main.java.models.Map.Map;


import static main.java.utils.Feedback.*;
import static adapters.File.FileAdapter.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GameEngine {
    Scanner sc = new Scanner(System.in);
    Map gameMap;
    MapEditor mapEditor;
    String command;

    public void startGame() {
        displayWelcomeMessage();
        command = sc.nextLine();
        if (command.equals("showcommands")) {
            displayAllCommands();
        }
        if (command.startsWith("loadmap")) {
            String l_fileName = command.split(" ")[1];
            File l_file= isFileExists(l_fileName);
            if (l_file!=null) {
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
        }
        System.out.println(gameMap.getContinents().toString());
        for(Country country:gameMap.getCountries()){
            System.out.println(country.toString());
        }

    }
}
