package models.Phase.GamePlay.ExecuteOrder;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Order.Order;
import models.Phase.GamePlay.GamePlay;
import models.Phase.GamePlay.IssueOrder.IssueOrder;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;

import java.util.ArrayList;

public class ExecuteOrder extends GamePlay {
    final static int MIN_ARMIES_PER_PLAYER = 3;

    private static final GamePhase PHASE_NAME= GamePhase.EXECUTE_ORDERS;
    public ExecuteOrder(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }
    public GamePhase getPhaseName(){
        return PHASE_NAME;
    }

    public void issueOrders() {
        printInvalidCommandMessage("issue orders",d_ge.getPhase().getPhaseName());
    }

    public void executeOrders() {
        System.out.println("Executing orders");
        ArrayList<Player> l_existingPlayers = PlayerHolder.getPlayers();
        int l_totalOrders = 0;
        for (Player player : l_existingPlayers) {
            l_totalOrders += player.getOrders().size();
        }
        int l_currentOrder = 1;
        int l_currentPlayerIndex = 0;
        while (l_currentOrder <= l_totalOrders) {
            Player l_currentPlayer = l_existingPlayers.get(l_currentPlayerIndex);
            Order l_order = l_currentPlayer.next_order();
            if (l_order == null) {
                l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
                continue;
            }
            switch (l_order.getName()) {
                case DEPLOY:
                    System.out.println("\nExecuting order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;
            }
            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
        }
        //after all orders are executed. assign reinforcements for next turn.
        assignReinforcements();
        resetOrdersStatus();
        next();
    }
    /**
     * Assigns reinforcements to players based on the number of countries owned.
     */
    public static void assignReinforcements() {
        ArrayList<Player> l_existingPlayer = PlayerHolder.getPlayers();
        for (Player player : l_existingPlayer) {
            int l_armyCount = player.getOwnedCountries().size() / 3;
            if (l_armyCount < MIN_ARMIES_PER_PLAYER) l_armyCount = MIN_ARMIES_PER_PLAYER;
            player.setNoOfArmies(l_armyCount);
        }
    }

    /**
     * Resets the hasOrders flag for all players after each turn.
     */
    private static void resetOrdersStatus() {
        ArrayList<Player> l_existingPlayer = PlayerHolder.getPlayers();
        for (Player player : l_existingPlayer) {
            player.setHasOrders(true);
        }
    }
    public void addOrRemovePlayer(String p_command, ArrayList<Player> p_existingPlayers) {
        printInvalidCommandMessage(p_command,d_ge.getPhase().getPhaseName());
    }
    public void next(){
       //go back to issue orders
        d_ge.setPhase(new IssueOrder(d_ge));
    }
}
