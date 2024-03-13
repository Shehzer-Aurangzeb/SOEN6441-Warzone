package models.Phase.MapEditing.Preload;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import log.LogEntryBuffer;
import models.Enums.GamePhase;
import models.Phase.MapEditing.MapEditing;
import models.Phase.MapEditing.Postload.Postload;

import static utils.Feedback.*;
import static views.MapView.MapView.displayMapInformation;

public class Preload extends MapEditing {
    private static final GamePhase PHASE_NAME= GamePhase.MAP_EDITING_PRELOAD;
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    public Preload(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }
    public GamePhase getPhaseName(){
        return PHASE_NAME;
    }

    public void modifyMapComponents(String p_command,MapEditor p_mapEditor){
        printInvalidCommandMessage(p_command, PHASE_NAME);
    }
    public void saveMap(String p_filename, MapEditor p_mapEditor){
        printInvalidCommandMessage(p_filename, PHASE_NAME);
    }
    public void next(){
        if(!isMapLoaded) System.out.println("\nCannot proceed to next phase." +
                " Please load the map first using the 'loadmap' command.");
        else {
            d_ge.setPhase(new Postload(d_ge));
            printWelcomeMessageWithBanner("You have entered Map Editing - Post Load phase.");
            System.out.println("Use 'showcommands' to see how you can edit the map.");
        }
    }
    public void showMap(){
        if(!isMapLoaded) System.out.println("\nCannot proceed to next phase." +
                " Please load the map first using the 'loadmap' command.");
        else displayMapInformation();
    }
}
