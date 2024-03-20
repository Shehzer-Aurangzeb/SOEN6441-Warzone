package models.Phase.MapEditing;

import controllers.GameEngine.GameEngine;
import log.LogEntryBuffer;

import models.Enums.GamePhase;
import models.Map.Map;
import models.Phase.Phase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static adapters.FileAdapter.FileAdapter.isFileExists;
import static views.MapView.MapView.displayMapInformation;

/**
 * The {@code MapEditing} abstract class extends {@code Phase} and provides common functionalities
 * for different stages of the map editing process. It allows for loading, editing, saving maps, and displaying map information.
 */
public abstract class MapEditing extends Phase {

    public static boolean isMapLoaded = false;

    /**
     * Constructs a new MapEditing phase with a reference to the game engine and sets the phase name.
     *
     * @param new_ge The game engine this phase is part of.
     * @param new_phaseName The name of the new phase.
     */
    public MapEditing(GameEngine new_ge, GamePhase new_phaseName) {
        super(new_ge, new_phaseName);
    }

    /**
     * Attempts to load a map from the specified filename.
     * It checks for the file's existence and loads it into the game context.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename) {
        File l_file = isFileExists(p_filename);
        if (l_file != null) {
            try {
                d_ctx.getMapService().loadMap(l_file);
                d_ctx.getMapService().setMapInRegistry(p_filename, d_ctx.getMap());
                d_ctx.getMapService().setCurrentEditingFilename(p_filename);
                isMapLoaded = true;
                System.out.println("\nMap loaded successfully. Type 'proceed' to move to the next phase of the game.");
                d_ctx.updateLog("Map '" + p_filename + "' has been loaded.");

            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println("IO exception.");
                System.exit(0);
            }
        } else {
            System.out.println("\nThe specified map file does not exist." +
                    " Please make sure the file name is correct or create a new map.");
            d_ctx.updateLog("Map '" + p_filename + "' does not exist.");
        }
    }

    /**
     * Edits an existing map or creates a new map if the specified filename does not exist in the registry.
     *
     * @param p_filename The filename of the map to edit or the name for a new map.
     */
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
                d_ctx.updateLog("\nMap '" + p_filename + "' is selected for edit.");
            } else {
                //creating a new map and linking it to filename.
                d_ctx.getMapService().setMapInRegistry(p_filename, new Map());
                d_ctx.getMapService().setCurrentEditingFilename(p_filename);
                System.out.println("\nNew map '" + p_filename + "' created. Ready to edit.");
                d_ctx.updateLog("Map " + p_filename + " has been created.");
            }
            isMapLoaded = true;
        }
    }

    /**
     * Gets the name of the current game phase.
     * @return The name of the current game phase.
     */
    public abstract GamePhase getPhaseName();

    /**
     * Modifies map components based on the given command.
     * @param p_command The command specifying the modification action.
     */
    public abstract void modifyMapComponents(String p_command);

    /**
     * Saves the current map to a file.
     * @param p_command The command or filename under which to save the map.
     */
    public abstract void saveMap(String p_command);


    /**
     * Displays the current state of the map.
     */
    public void showMap(){
        displayMapInformation();
    }

    /**
     * Handles attempts to add or remove a player, which is not allowed in this phase, by displaying an invalid command message.
     *
     * @param p_command The command attempted, expected to be related to adding or removing a player.
     */
    public void addOrRemovePlayer(String p_command) {
        printInvalidCommandMessage(p_command);
    }

    /**
     * Issues a message indicating that issuing orders is not a valid action during the map editing phase.
     */
    public void issueOrders() {
        printInvalidCommandMessage("order");
    }

    /**
     * Placeholder for the executeOrders method, not used in the map editing phase.
     */
    public void executeOrders() {
    }


    /**
     * Transitions the game to the next phase.
     */
    public abstract void next();
}
