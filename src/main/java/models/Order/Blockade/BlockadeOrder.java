package models.Order.Blockade;

import models.Country.Country;
import models.Enums.OrderType;
import models.Enums.PlayerType;
import models.GameContext.GameContext;
import models.Order.Order;
import models.Player.Player;


/**
 * Represents an order to blockade a country.
 */
public class BlockadeOrder implements Order {
    private int d_targetCountry;
    private static final GameContext d_ctx= GameContext.getInstance();

    /**
     * Initializes a blockade order with the target country.
     *
     * @param p_countryID The country to blockade.
     */
    public BlockadeOrder(int p_countryID){
        this.d_targetCountry = p_countryID;
    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
    public OrderType getName(){
        return OrderType.BLOCKADE;
    };

    /**
     * Executes the blockade order.
     */
    /**
     * Executes the blockade order.
     */
    @Override
    public void execute(Player p_player) {
        // Retrieve the target country from the map
        Country targetCountry = d_ctx.getMap().getCountryByID(this.d_targetCountry);

        // Check if the target country exists
        if (targetCountry != null) {
            // Triple the armies of the target country
            int remainingArmies = targetCountry.getArmiesDeployed()*3;
            targetCountry.setArmiesDeployed(remainingArmies);
            targetCountry.setOwner(null); // Make the country neutral
            p_player.removeOwnedCountry(targetCountry);
            System.out.println("Blockading country " + this.d_targetCountry + ".");
            d_ctx.updateLog("Blockading country " + this.d_targetCountry + ".");
        } else {
            System.out.println("Invalid target country for blockade order.");
        }
    }


    @Override
    public String toString(){
        return "Blockading country " + this.d_targetCountry + ".";
    }
}
