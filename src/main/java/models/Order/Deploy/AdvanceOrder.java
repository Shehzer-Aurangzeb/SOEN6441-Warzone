package models.Order.Deploy;

import models.Country.Country;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Order.Order;

/**
 * Represents an order to advance armies from one country to another.
 */
public class AdvanceOrder implements Order {
    private String d_countryFrom;
    private String d_countryTo;
    private int d_noOfArmies;

    /**
     * Initializes an advance order with the source country, target country, and number of armies to advance.
     *
     * @param p_countryFrom The country to advance armies from.
     * @param p_countryTo   The country to advance armies to.
     * @param p_noOfArmies  The number of armies to advance.
     */
    public AdvanceOrder(String p_countryFrom, String p_countryTo, int p_noOfArmies){
        this.d_countryFrom = p_countryFrom;
        this.d_countryTo = p_countryTo;
        this.d_noOfArmies = p_noOfArmies;
    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
    public OrderType getName(){
        return OrderType.ADVANCE;
    };

    /**
     * Executes the advance order.
     */
    public void execute() {
        // Retrieve the source country and target country from the map
        Country sourceCountry = MapHolder.getMap().getCountryByName(this.d_countryFrom);
        Country targetCountry = MapHolder.getMap().getCountryByName(this.d_countryTo);

        // Check if both countries exist
        if (sourceCountry != null && targetCountry != null) {
            // Check if the source country has enough armies to advance
            if (sourceCountry.getArmiesDeployed() >= this.d_noOfArmies) {
                // Move the specified number of armies from the source country to the target country
                int remainingArmies = Math.max(0, sourceCountry.getArmiesDeployed() - this.d_noOfArmies);
                sourceCountry.setArmiesDeployed(remainingArmies);
                targetCountry.setArmiesDeployed(targetCountry.getArmiesDeployed() + this.d_noOfArmies);
                System.out.println("Advancing " + this.d_noOfArmies + " armies from " + this.d_countryFrom + " to " + this.d_countryTo + ".");
            } else {
                System.out.println("Not enough armies in " + this.d_countryFrom + " to advance.");
            }
        } else {
            System.out.println("Invalid source or target country for advance order.");
        }
    }

    @Override
    public String toString(){
        return "Advancing " + this.d_noOfArmies + " armies from " + this.d_countryFrom + " to " + this.d_countryTo + ".";
    }
}
