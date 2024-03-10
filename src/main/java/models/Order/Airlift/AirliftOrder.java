package models.Order.Airlift;
import models.Order.Order;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Country.Country;

public class AirliftOrder implements Order {
    private int sourceCountryID;
    private int targetCountryID;
    private int numArmies;

    public AirliftOrder(int sourceCountryID, int targetCountryID, int numArmies) {
        this.sourceCountryID = sourceCountryID;
        this.targetCountryID = targetCountryID;
        this.numArmies = numArmies;
    }

    @Override
    public OrderType getName() {
        return OrderType.AIRLIFT;
    }

    public void execute() {
        Country sourceCountry = MapHolder.getMap().getCountryByID(sourceCountryID);
        Country targetCountry = MapHolder.getMap().getCountryByID(targetCountryID);

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

    @Override
    public String toString() {
        Country sourceCountry = MapHolder.getMap().getCountryByID(sourceCountryID);
        Country targetCountry = MapHolder.getMap().getCountryByID(targetCountryID);
        String sourceCountryName = sourceCountry != null ? sourceCountry.getName() : "Unknown";
        String targetCountryName = targetCountry != null ? targetCountry.getName() : "Unknown";
        return "Airlifting " + numArmies + " armies from " + sourceCountryName + " to " + targetCountryName + ".";
    }

}
