package models.Phase.GamePlay.ExecuteOrder;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Order.Order;
import models.Phase.GamePlay.GamePlay;
import models.Phase.GamePlay.IssueOrder.IssueOrder;
import models.Player.Player;

import java.util.ArrayList;

/**
 * The {@code ExecuteOrder} class extends {@code GamePlay} to specifically handle the execution phase of the game.
 * This phase is responsible for sequentially executing orders from all players until all orders have been processed.
 */
public class ExecuteOrder extends GamePlay {
    final static int MIN_ARMIES_PER_PLAYER = 3;

    private static final GamePhase PHASE_NAME= GamePhase.EXECUTE_ORDERS;

    /**
     * Constructs an ExecuteOrder phase with a reference to the GameEngine.
     *
     * @param new_ge The game engine that this phase is part of.
     */
    public ExecuteOrder(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }
    public GamePhase getPhaseName(){
        return PHASE_NAME;
    }

    /**
     * Attempts to issue orders, which is invalid in this phase, resulting in a printed error message.
     */
    public void issueOrders() {
        printInvalidCommandMessage("issue orders");
    }

    /**
     * Executes all pending orders for all players in a round-robin fashion until no orders remain.
     * It handles various types of orders, including deploy, advance, airlift, bomb, blockade, and diplomacy orders.
     * After executing all orders, it assigns reinforcements for the next turn and resets the order status for all players.
     */
    public void executeOrders() {
        ArrayList<Player> l_existingPlayers = d_ctx.getGamePlayers();
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
                    System.out.println("\nExecuting Deploy order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;
                case ADVANCE:
                    System.out.println("\nExecuting Advance order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;
                case AIRLIFT:
                    System.out.println("\nExecuting AIRLIFT order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;
                case BOMB:
                    System.out.println("\nExecuting BOMB order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;
                case BLOCKADE:
                    System.out.println("\nExecuting BLOCKADE order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;

                case DIPLOMACY:
                    System.out.println("\nExecuting DIPLOMACY order for player " + l_currentPlayer.getName() + ": " + l_order);
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
    public void assignReinforcements() {
        ArrayList<Player> l_existingPlayer = d_ctx.getGamePlayers();
        for (Player player : l_existingPlayer) {
            int l_armyCount = player.getOwnedCountries().size() / 3;
            if (l_armyCount < MIN_ARMIES_PER_PLAYER) l_armyCount = MIN_ARMIES_PER_PLAYER;
            player.setNoOfArmies(l_armyCount);
        }
    }

    /**
     * Resets the hasOrders flag for all players after each turn.
     */
    private void resetOrdersStatus() {
        ArrayList<Player> l_existingPlayer = d_ctx.getGamePlayers();
        for (Player player : l_existingPlayer) {
            player.setHasOrders(true);
        }
    }
    public void addOrRemovePlayer(String p_command) {
        printInvalidCommandMessage(p_command);
    }
    public void next(){
       //go back to issue orders
        d_ctx.setPhase(new IssueOrder(d_ge));
    }
}
