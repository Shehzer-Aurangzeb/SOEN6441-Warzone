package models.Order.Bomb;

import models.Country.Country;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Order.Order;

/**
 * Represents an order to bomb a country.
 */
public class BombOrder implements Order {
    private int targetCountryID;

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
    public void execute() {
        // Get the target country from the map
        Country targetCountry = MapHolder.getMap().getCountryByID(targetCountryID);

        // Check if the target country exists
        if (targetCountry != null) {
            // Check if there are armies left in the target country
            int armies = targetCountry.getArmiesDeployed();
            if (armies > 0) {
                // Decrease the number of armies in the target country by 1
                targetCountry.setArmiesDeployed(armies - 1);
            } else {
                // No armies left in the target country
                System.out.println("Cannot bomb country " + targetCountryID + ". No armies left.");
            }
        } else {
            // Handle the case where the target country does not exist
            System.out.println("Invalid target country for bombing.");
        }
    }

    /**
     * Returns a string representation of the bomb order.
     *
     * @return A string representing the bomb order.
     */
    @Override
    public String toString() {
        Country targetCountry = MapHolder.getMap().getCountryByID(this.targetCountryID);
        if (targetCountry != null) {
            int newArmies = Math.max(0, targetCountry.getArmiesDeployed() - 1);
            if (newArmies == 0) {
                return "Cannot bomb country " + this.targetCountryID + ". No armies left.";
            } else {
                return "Bombing country " + this.targetCountryID + ". Armies reduced to " + newArmies + ".";
            }
        } else {
            return "Invalid target country for bombing.";
        }
    }
}
