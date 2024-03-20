package models.Order.Bomb;

import models.Country.Country;
import models.Enums.OrderType;

import models.GameContext.GameContext;
import models.Order.Order;
import models.Player.Player;

/**
 * Represents an order to bomb a country.
 */
public class BombOrder implements Order {
    private int targetCountryID;
    private static final GameContext d_ctx = GameContext.getInstance();

    /**
     * Initializes a bomb order with the target country.
     *
     * @param targetCountryID The ID of the country to bomb.
     */
    public BombOrder(int targetCountryID) {
        this.targetCountryID = targetCountryID;
    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
    @Override
    public OrderType getName() {
        return OrderType.BOMB;
    }

    /**
     * Executes the bomb order.
     */
//    int currentArmies = targetCountry.getArmiesDeployed();
//    int remainingArmies = currentArmies - (currentArmies / 2);
//    targetCountry.setArmiesDeployed(remainingArmies);
    public void execute(Player p_player) {
        // Get the target country from the map
        Country targetCountry = d_ctx.getMap().getCountryByID(targetCountryID);

        if (targetCountry == null) {
            System.out.println("\nInvalid country ID. Country does not exist.");
            return;
        }
        int currentArmies = targetCountry.getArmiesDeployed();
        int remainingArmies = Math.max(0,currentArmies - (currentArmies / 2));
        targetCountry.setArmiesDeployed(remainingArmies);
        System.out.println("Bomb order executed successfully. Half of the armies on " + targetCountry.getName() + " have been destroyed.");


    }

    /**
     * Returns a string representation of the bomb order.
     *
     * @return A string representing the bomb order.
     */
    @Override
    public String toString() {
        Country targetCountry = d_ctx.getMap().getCountryByID(this.targetCountryID);
        if (targetCountry != null) {
            int newArmies = Math.max(0, targetCountry.getArmiesDeployed() - 1);
            if (newArmies == 0) {
                return "Cannot bomb country " + this.targetCountryID + ". No armies left.";
            } else {
                d_ctx.updateLog("Bombing country " + this.targetCountryID + ". Armies reduced to " + newArmies + ".");
                return "Bombing country " + this.targetCountryID + ". Armies reduced to " + newArmies + ".";
            }
        } else {
            return "Invalid target country for bombing.";
        }
    }
}
