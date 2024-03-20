package models.Order.Diplomacy;

import models.Enums.OrderType;
import models.Order.Order;
import models.Player.Player;



/**
 * Represents a diplomacy order in the game.
 */
public class DiplomacyOrder implements Order {
    private String playerName;

    /**
     * Constructs a diplomacy order with the specified player name.
     *
     * @param playerName The name of the player to negotiate with.
     */
    public DiplomacyOrder(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Retrieves the name of the player to negotiate with.
     *
     * @return The name of the player.
     */
    public String getPlayerName() {
        return playerName;
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
     * Executes the diplomacy order.
     */
    @Override
    public void execute(Player currentPlayer) {
        // Placeholder implementation for executing a diplomacy order
        // This method will be called when the order is executed
        if (!currentPlayer.getName().equals(playerName)) {
            System.out.println("Executing diplomacy order with player name: " + playerName);
            // Implement the actual logic for diplomacy here
        } else {
            System.out.println("Cannot negotiate with yourself.");
        }
    }


}
