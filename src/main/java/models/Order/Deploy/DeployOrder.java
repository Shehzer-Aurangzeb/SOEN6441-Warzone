package models.Order.Deploy;

import models.Country.Country;
import models.Enums.OrderType;
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
    public OrderType getName(){
        return OrderType.DEPLOY;
    };
    /**
     * Executes the deployment order.
     */
    public void execute(){
        Country country = MapHolder.getMap().getCountryByID(this.d_targetCountry);
        country.setArmiesDeployed(this.d_noOfArmies);

    }
    @Override
    public String toString(){
        return "Deploying " + this.d_noOfArmies + " armies to reinforce country " + this.d_targetCountry + ".";
    }
}
