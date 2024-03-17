package models.Phase.MapEditing;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Map.Map;
import models.Phase.Phase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static adapters.FileAdapter.FileAdapter.isFileExists;
import static views.MapView.MapView.displayMapInformation;

public abstract class MapEditing extends Phase {
    public static boolean isMapLoaded = false;

    public MapEditing(GameEngine new_ge, GamePhase new_phaseName) {
        super(new_ge, new_phaseName);
    }

    public void loadMap(String p_filename) {
        File l_file = isFileExists(p_filename);
        if (l_file != null) {
            try {
                d_ctx.getMapService().loadMap(l_file);
                d_ctx.getMapService().setMapInRegistry(p_filename, d_ctx.getMap());
                d_ctx.getMapService().setCurrentEditingFilename(p_filename);
                isMapLoaded = true;
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

    public void editMap(String p_filename) {
        File l_file = isFileExists(p_filename);
        //load map if file exists.
        if (l_file != null) {
            //it will load the map that is in MapHolder.
            loadMap(p_filename);
        } else {
            //check if map is not in registry
            if (d_ctx.getMapService().getMapRegistry().containsKey(p_filename)) {
                d_ctx.getMapService().setCurrentEditingFilename(p_filename);
                System.out.println("\nMap '" + p_filename + "' is selected for edit.");
            } else {
                //creating a new map and linking it to filename.
                d_ctx.getMapService().setMapInRegistry(p_filename, new Map());
                d_ctx.getMapService().setCurrentEditingFilename(p_filename);
                System.out.println("\nNew map '" + p_filename + "' created. Ready to edit.");
            }
            isMapLoaded = true;
        }
    }

    public abstract GamePhase getPhaseName();

    public abstract void modifyMapComponents(String p_command);

    public abstract void saveMap(String p_command);

    public void showMap(){
        displayMapInformation();
    }

    public void addOrRemovePlayer(String p_command) {
        printInvalidCommandMessage(p_command);
    }

    public void issueOrders() {
        printInvalidCommandMessage("order");
    }

    public void executeOrders() {
    }

    ;

    public abstract void next();
}
