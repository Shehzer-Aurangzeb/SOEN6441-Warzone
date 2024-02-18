package models.Order.Deploy;

import models.Country.Country;
import models.MapHolder.MapHolder;
import models.Order.Order;

/**
 * Represents a deployment order to place armies on a country.
 */
public class DeployOrder implements Order {
    private int d_targetCountry;
    private int d_noOfArmies;

    /**
     * Initializes a deployment order with the target country and number of armies to deploy.
     *
     * @param new_countryID    The target country to deploy armies to.
     * @param new_noOfArmies The number of armies to deploy.
     */
    public DeployOrder(int new_countryID, int new_noOfArmies){
        this.d_targetCountry = new_countryID;
        this.d_noOfArmies = new_noOfArmies;
    }
    /**
     * Executes the deployment order.
     */
    public void execute(){

    }

}
