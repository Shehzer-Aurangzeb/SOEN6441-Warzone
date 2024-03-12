package models.Order.Diplomacy;

import models.Enums.OrderType;
import models.Order.Order;

/**
 * Represents a diplomacy order in the game.
 */
public class DiplomacyOrder implements Order {
    private int playerID;

    /**
     * Constructs a diplomacy order with the specified player ID.
     *
     * @param playerID The ID of the player to negotiate with.
     */
    public DiplomacyOrder(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Retrieves the ID of the player to negotiate with.
     *
     * @return The ID of the player.
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * Retrieves the name of the order.
     *
     * @return The name of the order as an OrderType enum.
     */
    @Override
    public OrderType getName() {
        return OrderType.DIPLOMACY;
    }

    /**
     * Executes the order.
     */
    @Override
    public void execute() {
        // Placeholder implementation for executing a diplomacy order
        // This method will be called when the order is executed
        System.out.println("Executing diplomacy order with player ID: " + playerID);
        // Implement the actual logic for diplomacy here
    }
}