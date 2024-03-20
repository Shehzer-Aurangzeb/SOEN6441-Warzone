package models.Phase.MapEditing.Preload;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Map.MapValidator;
import models.Phase.MapEditing.MapEditing;
import models.Phase.MapEditing.Postload.Postload;

import static utils.Feedback.*;

public class Preload extends MapEditing {
    private static final GamePhase PHASE_NAME= GamePhase.MAP_EDITING_PRELOAD;

    public Preload(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }
    public GamePhase getPhaseName(){
        return PHASE_NAME;
    }

    public void modifyMapComponents(String p_command){
        printInvalidCommandMessage(p_command);
    }
    public void saveMap(String p_filename){
        printInvalidCommandMessage(p_filename);
    }
    public void validateMap(){
        if(!isMapLoaded) System.out.println("\nPlease load the map first using the 'loadmap' command.");
        else if (!MapValidator.validateMap(d_ctx.getMap())) {
            System.out.println("The map is not valid. Please load a valid map.\n");
        }
        else {
            System.out.println("The map is valid. You can proceed to the next phase.");
        }
    }
    public void next(){
        if(!isMapLoaded) System.out.println("\nCannot proceed to next phase." +
                " Please load the map first using the 'loadmap' command.");
        else {
            d_ctx.setPhase(new Postload(d_ge));
            printWelcomeMessageWithBanner("You have entered Map Editing - Post Load phase.");
            System.out.println("Use 'showcommands' to see how you can edit the map.");
        }
    }

}
