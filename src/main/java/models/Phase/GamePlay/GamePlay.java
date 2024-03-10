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
    public void loadMap(String p_filename, MapEditor p_mapEditor){
        printInvalidCommandMessage("loadmap",d_ge.getPhase().getPhaseName());
    }
    public void editMap(String p_filename, MapEditor p_mapEditor){
        printInvalidCommandMessage("editmap",d_ge.getPhase().getPhaseName());
    }
    public void modifyMapComponents(String p_command,MapEditor p_mapEditor){
        printInvalidCommandMessage(p_command,d_ge.getPhase().getPhaseName());
    }
    public void saveMap(String p_command, MapEditor p_mapEditor){
        printInvalidCommandMessage(p_command,d_ge.getPhase().getPhaseName());
    }
    public abstract void addOrRemovePlayer(String p_command, ArrayList<Player> p_existingPlayers);
    public abstract void issueOrders();
    public abstract void executeOrders();
    public void showMap(){
        displayMapInformation();
    }
    public abstract void next();
}
