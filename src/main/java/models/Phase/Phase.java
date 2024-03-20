package models.Phase;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.GameContext.GameContext;
import models.Player.Player;


import java.util.ArrayList;

import static utils.Feedback.*;

public abstract class Phase {
    protected final GameContext d_ctx= GameContext.getInstance();
    protected GameEngine d_ge;
    private static GamePhase PHASE_NAME;
    public Phase(GameEngine new_ge,GamePhase new_phaseName){
        d_ge=new_ge;
        PHASE_NAME=new_phaseName;
    }
    public abstract GamePhase getPhaseName();
    public abstract void loadMap(String p_filename);
    public abstract void editMap(String p_filename);
    public abstract void validateMap();
    public abstract void modifyMapComponents(String p_command);
    public abstract void saveMap(String p_command);
    public abstract void showMap();
    public abstract void addOrRemovePlayer(String p_command);
    public abstract void issueOrders();
    public abstract void executeOrders();
    public void assignReinforcements() {}
    public abstract void next();
    public void showCommands() {
        d_ctx.updateLog("Showcommands entered for " + d_ctx.getPhase().getPhaseName() + " phase.");
        displayPhaseInstructions(PHASE_NAME);
    }
    public void printInvalidCommandMessage(String p_command) {
        displayCommandUnavailableMessage(p_command,d_ctx.getPhase().getPhaseName());
    }
    public void exit(){
        d_ctx.updateLog("Exiting the game....");
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }


}
