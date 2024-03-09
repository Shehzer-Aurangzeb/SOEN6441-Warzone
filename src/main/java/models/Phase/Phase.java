package models.Phase;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Player.Player;

import java.util.ArrayList;

import static utils.Feedback.*;

public abstract class Phase {
    protected GameEngine d_ge;
    private static GamePhase PHASE_NAME;
    public Phase(GameEngine new_ge,GamePhase new_phaseName){
        d_ge=new_ge;
        PHASE_NAME=new_phaseName;
    }
    public abstract GamePhase getPhaseName();
    public abstract void loadMap(String p_filename, MapEditor p_mapEditor);
    public abstract void editMap(String p_filename, MapEditor p_mapEditor);
    public abstract void modifyMapComponents(String p_command,MapEditor p_mapEditor);
    public abstract void saveMap(String p_command, MapEditor p_mapEditor);
    public abstract void showMap();
    public abstract void addOrRemovePlayer(String p_command, ArrayList<Player> p_existingPlayers);
    public abstract void issueOrders();
    public abstract void executeOrders();
    public abstract void next();
    public void showCommands() {
        displayPhaseInstructions(PHASE_NAME);
    }
    public void printInvalidCommandMessage(String p_command, GamePhase p_phase) {
        displayCommandUnavailableMessage(p_command,p_phase);
    }
    public void exit(){
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }


}
