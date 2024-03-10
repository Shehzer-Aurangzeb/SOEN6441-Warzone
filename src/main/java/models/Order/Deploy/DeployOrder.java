package models.Order.Deploy;

import models.Country.Country;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Order.Order;
import models.Player.Player;

/**
 * Represents an order to deploy armies to a country.
 */
public class DeployOrder implements Order {
    private int d_targetCountry;
    private int d_noOfArmies;
    private Player player; // Declare the Player variable

    /**
     * Initializes a deployment order with the target country and number of armies to deploy.
     *
     * @param p_countryID    The target country to deploy armies to.
     * @param p_noOfArmies The number of armies to deploy.
     */
    public DeployOrder(int p_countryID, int p_noOfArmies, Player player){
        this.d_targetCountry = p_countryID;
        this.d_noOfArmies = p_noOfArmies;
        this.player = player; // Assign the Player variable
    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
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
