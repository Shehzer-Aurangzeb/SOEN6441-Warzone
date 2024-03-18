package models.Order.Deploy;

import models.Country.Country;
import models.Enums.OrderType;
import models.GameContext.GameContext;
import models.Order.Order;
import models.Player.Player;

/**
 * Represents an order to deploy armies to a country.
 */
public class DeployOrder implements Order {
    private int d_targetCountry;
    private int d_noOfArmies;
    private static final GameContext d_ctx= GameContext.getInstance();


    /**
     * Initializes a deployment order with the target country and number of armies to deploy.
     *
     * @param p_countryID    The target country to deploy armies to.
     * @param p_noOfArmies The number of armies to deploy.
     */
    public DeployOrder(int p_countryID, int p_noOfArmies){
        this.d_targetCountry = p_countryID;
        this.d_noOfArmies = p_noOfArmies;

    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
    @Override
    public OrderType getName(){
        return OrderType.DEPLOY;
    };

    /**
     * Executes the deployment order.
     */
    @Override
    public void execute(){
        Country country = d_ctx.getMap().getCountryByID(this.d_targetCountry);
        country.setArmiesDeployed(this.d_noOfArmies);
    }
    @Override
    public String toString(){
        d_ctx.updateLog("Deploying " + this.d_noOfArmies + " armies to reinforce country " + this.d_targetCountry + ".");
        return "Deploying " + this.d_noOfArmies + " armies to reinforce country " + this.d_targetCountry + ".";
    }
}
