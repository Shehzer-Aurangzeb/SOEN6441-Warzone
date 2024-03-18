package models.Phase.GamePlay.IssueOrder;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Phase.GamePlay.ExecuteOrder.ExecuteOrder;
import models.Phase.GamePlay.GamePlay;
import models.Player.Player;

import java.util.ArrayList;

import static utils.Feedback.printWelcomeMessageWithBanner;

/**
 * Represents the phase in the game where players are issuing their orders. This phase allows each player to issue
 * their orders in turn, cycling through all players until all have either issued all their orders or have no more orders to issue.
 */
public class IssueOrder extends GamePlay {
    private static final GamePhase PHASE_NAME = GamePhase.ISSUE_ORDERS;

    /**
     * Constructs a new IssueOrder phase associated with a specific game engine.
     *
     * @param new_ge The game engine this phase is part of.
     */
    public IssueOrder(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }
    public GamePhase getPhaseName() {
        return PHASE_NAME;
    }

    /**
     * Processes the issuance of orders from all players in the game. This method cycles through each player,
     * prompting them to issue their orders until all players have finished.
     */
    public void issueOrders(){
        int l_currentPlayerIndex = 0;
        ArrayList<Player> l_existingPlayers =d_ctx.getGamePlayers();
        while (true) {
            if (allPlayersDoneWithOrders()) break;
            Player l_currentPlayer = l_existingPlayers.get(l_currentPlayerIndex);
            if (!l_currentPlayer.hasOrders()) {
                l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
                continue;
            }
            l_currentPlayer.issue_order();
            if (!l_currentPlayer.lastCommandValidForOrders()) {
                continue;
            }
            l_currentPlayer.setHasOrders(l_currentPlayer.getNoOfArmies() > 0);
            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
        }
        System.out.println("\nAll players have finished issuing orders. The game is now proceeding to execute orders.");
        next();

    }

    public void addOrRemovePlayer(String p_command) {
        printInvalidCommandMessage(p_command);
    }
    public void executeOrders(){};

    public void next(){
        d_ctx.setPhase(new ExecuteOrder(d_ge));
        printWelcomeMessageWithBanner("Executing Orders ...");
    }
    /**
     * Checks if all players are done issuing orders.
     *
     * @return True if all players are done issuing orders, false otherwise.
     */
    private boolean allPlayersDoneWithOrders() {
        boolean l_allPlayersDone = true;
        for (Player player : d_ctx.getGamePlayers()) {
            if (player.hasOrders()) {
                l_allPlayersDone = false;
                break;
            }
        }
        return l_allPlayersDone;
    }
}
