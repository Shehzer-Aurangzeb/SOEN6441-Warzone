package models.Order.Deploy;

import models.Country.Country;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Order.Order;

/**
 * Represents an order to airlift armies from one country to another.
 */
public class AirliftOrder implements Order {
    private int sourceCountryID;
    private int targetCountryID;
    private int numArmies;

    /**
     * Initializes an airlift order with the source country ID, target country ID, and number of armies to airlift.
     *
     * @param sourceCountryID The ID of the source country.
     * @param targetCountryID The ID of the target country.
     * @param numArmies       The number of armies to airlift.
     */
    public AirliftOrder(int sourceCountryID, int targetCountryID, int numArmies) {
        this.sourceCountryID = sourceCountryID;
        this.targetCountryID = targetCountryID;
        this.numArmies = numArmies;
    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
    public OrderType getName() {
        return OrderType.AIRLIFT;
    }

    /**
     * Executes the airlift order.
     */
    public void execute() {
        // Retrieve the source and target countries
        Country sourceCountry = MapHolder.getMap().getCountryByID(sourceCountryID);
        Country targetCountry = MapHolder.getMap().getCountryByID(targetCountryID);

        // Check if both source and target countries exist
        if (sourceCountry != null && targetCountry != null) {
            // Check if the source country has enough armies to airlift
            if (sourceCountry.getArmiesDeployed() >= numArmies) {
                // Move armies from the source country to the target country
                sourceCountry.setArmiesDeployed(sourceCountry.getArmiesDeployed() - numArmies);
                targetCountry.setArmiesDeployed(targetCountry.getArmiesDeployed() + numArmies);
                System.out.println("Airlifted " + numArmies + " armies from country " + sourceCountryID + " to country " + targetCountryID + ".");
            } else {
                System.out.println("Not enough armies in country " + sourceCountryID + " to airlift.");
            }
        } else {
            System.out.println("Invalid source or target country IDs.");
        }
    }
}
