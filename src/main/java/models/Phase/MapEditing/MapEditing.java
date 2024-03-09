package models.Phase.MapEditing;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Phase.Phase;
import models.Player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static adapters.FileAdapter.FileAdapter.isFileExists;

public abstract class MapEditing extends Phase {
    public static boolean isMapLoaded=false;
    public MapEditing(GameEngine new_ge,GamePhase new_phaseName){
        super(new_ge,new_phaseName);
    }

    public void loadMap(String p_filename, MapEditor p_mapEditor){
        File l_file = isFileExists(p_filename);
        if (l_file != null) {
            try {
                p_mapEditor.loadMap(l_file);
                p_mapEditor.setMapInRegistry(p_filename, MapHolder.getMap());
                p_mapEditor.setCurrentEditingFilename(p_filename);
                isMapLoaded=true;
                System.out.println("\nMap loaded successfully. Type 'proceed' to move to the next phase of the game.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println("IO exception.");
                System.exit(0);
            }
        } else {
            System.out.println("\nThe specified map file does not exist." +
                    " Please make sure the file name is correct or create a new map.");
        }
    }
    public void editMap(String p_filename, MapEditor p_mapEditor){
        File l_file = isFileExists(p_filename);
        //load map if file exists.
        if (l_file != null) {
            //it will load the map that is in MapHolder.
            loadMap(p_filename,p_mapEditor);
        } else {
            //check if map is not in registry
            if (p_mapEditor.getMapRegistry().containsKey(p_filename)) {
                p_mapEditor.setCurrentEditingFilename(p_filename);
                System.out.println("\nMap '" + p_filename + "' is selected for edit.");
            } else {
                //creating a new map and linking it to filename.
                p_mapEditor.setMapInRegistry(p_filename, new Map());
                p_mapEditor.setCurrentEditingFilename(p_filename);
                System.out.println("\nNew map '" + p_filename + "' created. Ready to edit.");
            }
            isMapLoaded=true;
        }
    }

    public abstract GamePhase getPhaseName();
    public abstract void modifyMapComponents(String p_command,MapEditor p_mapEditor);
    public abstract void saveMap(String p_command, MapEditor p_mapEditor);
    public abstract void showMap();
    public void addOrRemovePlayer(String p_command, ArrayList<Player> p_existingPlayers){
        printInvalidCommandMessage(p_command,d_ge.getPhase().getPhaseName());
    }
    public void issueOrders(){
        printInvalidCommandMessage("order",d_ge.getPhase().getPhaseName());
    }
    public void executeOrders(){};
    public abstract void next();
}
