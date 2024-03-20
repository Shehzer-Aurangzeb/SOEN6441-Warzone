package models.Phase.GamePlay;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Phase.Phase;
import models.Player.Player;

import java.util.ArrayList;

import static views.MapView.MapView.displayMapInformation;


public abstract class GamePlay extends Phase {

    public GamePlay(GameEngine new_ge,GamePhase new_phaseName){
        super(new_ge,new_phaseName);
    }

    public abstract GamePhase getPhaseName();
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
