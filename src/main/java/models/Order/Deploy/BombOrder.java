package models.Order.Deploy;

import models.Country.Country;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Order.Order;

/**
 * Represents an order to bomb a country.
 */
public class BombOrder implements Order {
    private int d_targetCountry;

    /**
     * Initializes a bomb order with the target country.
     *
     * @param p_countryID The country to bomb.
     */
    public BombOrder(int p_countryID) {
        this.d_targetCountry = p_countryID;
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
    @Override
    public void execute() {
        Country targetCountry = MapHolder.getMap().getCountryByID(this.d_targetCountry);
        // Check if the target country exists
        if (targetCountry != null) {
            // Reduce the number of armies in the target country
            int remainingArmies = Math.max(0, targetCountry.getArmiesDeployed() - 1);
            targetCountry.setArmiesDeployed(remainingArmies);
            System.out.println("Bombing country " + this.d_targetCountry + ". Armies reduced to " + remainingArmies + ".");
        } else {
            System.out.println("Invalid target country for bombing.");
        }
    }
}