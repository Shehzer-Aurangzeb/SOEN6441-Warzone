package models.Order.Airlift;
import models.GameContext.GameContext;
import models.Order.Order;
import models.Enums.OrderType;
import models.Country.Country;


/**
 * The {@code AirliftOrder} class implements the {@code Order} interface to represent an airlift order in the game.
 * It allows for the transfer of a specified number of armies from one country to another.
 */
public class AirliftOrder implements Order {
    private int sourceCountryID;
    private int targetCountryID;
    private int numArmies;
    private static final GameContext d_ctx= GameContext.getInstance();

    /**
     * Constructs an AirliftOrder with specified source and target country IDs and the number of armies to be airlifted.
     *
     * @param sourceCountryID The ID of the country from which armies are to be airlifted.
     * @param targetCountryID The ID of the country to which armies are to be airlifted.
     * @param numArmies The number of armies to airlift.
     */
    public AirliftOrder(int sourceCountryID, int targetCountryID, int numArmies) {
        this.sourceCountryID = sourceCountryID;
        this.targetCountryID = targetCountryID;
        this.numArmies = numArmies;
    }

    /**
     * Gets the type of the order, which is AIRLIFT in this case.
     *
     * @return The order type AIRLIFT.
     */
    @Override
    public OrderType getName() {
        return OrderType.AIRLIFT;
    }

    /**
     * Executes the airlift order by transferring the specified number of armies from the source country to the target country.
     * It updates the army count for both countries accordingly. If there are not enough armies in the source country,
     * or if any of the country IDs are invalid, it throws an exception.
     *
     * @throws IllegalStateException If there are not enough armies in the source country.
     * @throws IllegalArgumentException If the source or target country IDs are invalid.
     */
    @Override
    public void execute() {
        Country sourceCountry = d_ctx.getMap().getCountryByID(sourceCountryID);
        Country targetCountry = d_ctx.getMap().getCountryByID(targetCountryID);

        if (sourceCountry != null && targetCountry != null) {
            if (sourceCountry.getArmiesDeployed() >= numArmies) {
                sourceCountry.setArmiesDeployed(sourceCountry.getArmiesDeployed() - numArmies);
                targetCountry.setArmiesDeployed(targetCountry.getArmiesDeployed() + numArmies);
            } else {
                throw new IllegalStateException("Not enough armies in country " + sourceCountryID + " to airlift.");
            }
        } else {
            throw new IllegalArgumentException("Invalid source or target country IDs.");
        }
    }

    /**
     * Returns a string representation of the airlift order, including the number of armies to be transferred,
     * and the names of the source and target countries.
     *
     * @return A string describing the airlift order.
     */
    @Override
    public String toString() {
        Country sourceCountry = d_ctx.getMap().getCountryByID(sourceCountryID);
        Country targetCountry = d_ctx.getMap().getCountryByID(targetCountryID);
        String sourceCountryName = sourceCountry != null ? sourceCountry.getName() : "Unknown";
        String targetCountryName = targetCountry != null ? targetCountry.getName() : "Unknown";
        d_ctx.updateLog("Airlifting " + numArmies + " armies from " + sourceCountryName + " to " + targetCountryName + ".");
        return "Airlifting " + numArmies + " armies from " + sourceCountryName + " to " + targetCountryName + ".";

    }

}
