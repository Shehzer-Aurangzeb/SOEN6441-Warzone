package controllers.CommandHandler;


import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static adapters.FileAdapter.FileAdapter.isFileExists;
import static utils.Feedback.displayPhaseInstructions;

public class CommandHandler {

    public static void handleLoadMapCommand(String p_command,MapEditor p_mapEditor){
        String l_fileName = p_command.split(" ")[1];
        File l_file = isFileExists(l_fileName);
        if (l_file != null) {
            try {
                p_mapEditor.loadMap(l_file);
                System.out.println("Map loaded successfully.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("IO exception.");
                System.exit(0);
            }
        }
    }
    public static void handleExitCommand(){
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }
    public static void handleDisplayCommands(GamePhase p_gamePhase){
        displayPhaseInstructions(p_gamePhase);

    }
    public static void handleDeployOrderCommand(){

    }


}
