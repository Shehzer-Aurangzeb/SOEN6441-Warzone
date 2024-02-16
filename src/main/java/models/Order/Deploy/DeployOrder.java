package models.Order.Deploy;

import models.Country.Country;
import models.Order.Order;

/**
 * Represents a deployment order to place armies on a country.
 */
public class DeployOrder implements Order {
    private Country d_targetCountry;
    private int d_noOfArmies;

    /**
     * Initializes a deployment order with the target country and number of armies to deploy.
     *
     * @param new_country    The target country to deploy armies to.
     * @param new_noOfArmies The number of armies to deploy.
     */
    public DeployOrder(Country new_country, int new_noOfArmies){
        this.d_targetCountry = new_country;
        this.d_noOfArmies = new_noOfArmies;
    }
    /**
     * Executes the deployment order.
     */
    public void execute(){}

}
