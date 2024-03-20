package models.Phase.GamePlay;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Phase.Phase;
import models.Player.Player;

import java.util.ArrayList;

import static views.MapView.MapView.displayMapInformation;

/**
 * The {@code GamePlay} abstract class extends {@code Phase} and provides a template for the different phases of the game play.
 * It defines several abstract methods that concrete game play phases must implement, and provides common functionality for game play phases.
 */
public abstract class GamePlay extends Phase {

    /**
     * Constructs a new GamePlay phase with a reference to the game engine and sets the phase name.
     *
     * @param new_ge The game engine this phase is part of.
     * @param new_phaseName The name of the new phase.
     */
    public GamePlay(GameEngine new_ge,GamePhase new_phaseName){
        super(new_ge,new_phaseName);
    }

    public abstract GamePhase getPhaseName();

    /**
     * Attempts to load a map, which is not a valid action in the game play phase, resulting in an invalid command message.
     *
     * @param p_filename The filename of the map to load.
     */
    public void loadMap(String p_filename){
        printInvalidCommandMessage("loadmap");
    }
    public void editMap(String p_filename){
        printInvalidCommandMessage("editmap");
    }
    public void modifyMapComponents(String p_command){
        printInvalidCommandMessage(p_command);
    }
    public void validateMap(){
        printInvalidCommandMessage("validatemap");
    }
    public void saveMap(String p_command){
        printInvalidCommandMessage(p_command);
    }
    public abstract void addOrRemovePlayer(String p_command);
    public abstract void issueOrders();
    public abstract void executeOrders();
    public void showMap(){
        displayMapInformation();
    }
    public abstract void next();
}
