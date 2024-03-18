package models.Phase;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.GameContext.GameContext;
import models.Player.Player;
import java.util.ArrayList;
import static utils.Feedback.*;

/**
 * Represents a general phase in the game, providing a structured approach to managing the game's lifecycle.
 * Each phase is responsible for handling specific actions that can occur during that phase, such as loading maps, editing maps, issuing orders, and executing orders.
 */
public abstract class Phase {
    protected final GameContext d_ctx= GameContext.getInstance();
    protected GameEngine d_ge;
    private static GamePhase PHASE_NAME;

    /**
     * Constructs a Phase with a reference to the game engine and sets the phase name.
     *
     * @param new_ge The game engine associated with this phase.
     * @param new_phaseName The name of the phase.
     */
    public Phase(GameEngine new_ge,GamePhase new_phaseName){
        d_ge=new_ge;
        PHASE_NAME=new_phaseName;
    }

    /**
     * Gets the name of the current game phase.
     *
     * @return The enum value representing the current game phase.
     */
    public abstract GamePhase getPhaseName();
    public abstract void loadMap(String p_filename);
    public abstract void editMap(String p_filename);
    public abstract void modifyMapComponents(String p_command);
    public abstract void saveMap(String p_command);
    public abstract void showMap();
    public abstract void addOrRemovePlayer(String p_command);
    public abstract void issueOrders();
    public abstract void executeOrders();
    public void assignReinforcements() {}
    public abstract void next();

    /**
     * Displays available commands and instructions for the current phase.
     */
    public void showCommands() {
        d_ctx.updateLog("Showcommands entered for " + d_ctx.getPhase().getPhaseName() + " phase.");
        displayPhaseInstructions(PHASE_NAME);
    }

    /**
     * Prints a message indicating that the attempted command is invalid for the current phase.
     *
     * @param p_command The command that was attempted.
     */
    public void printInvalidCommandMessage(String p_command) {
        displayCommandUnavailableMessage(p_command,d_ctx.getPhase().getPhaseName());
    }

    /**
     * Exits the game, logging a message and terminating the application.
     */
    public void exit(){
        d_ctx.updateLog("Exiting the game....");
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }


}
