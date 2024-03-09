package models.Phase.GamePlay.IssueOrder;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Phase.GamePlay.ExecuteOrder.ExecuteOrder;
import models.Phase.GamePlay.GamePlay;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;

import java.util.ArrayList;

import static utils.Feedback.printWelcomeMessageWithBanner;


public class IssueOrder extends GamePlay {
    private static final GamePhase PHASE_NAME = GamePhase.ISSUE_ORDERS;
    public IssueOrder(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }
    public GamePhase getPhaseName() {
        return PHASE_NAME;
    }

    public void issueOrders(){
        int l_currentPlayerIndex = 0;
        ArrayList<Player> l_existingPlayers = PlayerHolder.getPlayers();
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


    public void addOrRemovePlayer(String p_command, ArrayList<Player> p_existingPlayers) {
        printInvalidCommandMessage(p_command,d_ge.getPhase().getPhaseName());
    }
    public void executeOrders(){};

    public void next(){
        d_ge.setPhase(new ExecuteOrder(d_ge));
        printWelcomeMessageWithBanner("Executing Orders ...");
    }
    /**
     * Checks if all players are done issuing orders.
     *
     * @return True if all players are done issuing orders, false otherwise.
     */
    private static boolean allPlayersDoneWithOrders() {
        boolean l_allPlayersDone = true;
        for (Player player : PlayerHolder.getPlayers()) {
            if (player.hasOrders()) {
                l_allPlayersDone = false;
                break;
            }
        }
        return l_allPlayersDone;
    }
}
