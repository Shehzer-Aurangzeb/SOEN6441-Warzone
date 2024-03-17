package models.Order.Advance;

import models.Country.Country;
import models.Enums.OrderType;
import models.MapHolder.MapHolder;
import models.Order.Order;

import java.util.Random;

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
    public AdvanceOrder(String p_countryFrom, String p_countryTo, int p_noOfArmies) {
        this.d_countryFrom = p_countryFrom;
        this.d_countryTo = p_countryTo;
        this.d_noOfArmies = p_noOfArmies;
    }

    /**
     * Returns the type of the order.
     *
     * @return The order type.
     */
    public OrderType getName() {
        return OrderType.ADVANCE;
    }

    ;

    /**
     * Executes the advance order.
     */
    public void execute() {
        // Retrieve the source country and target country from the map
        Country sourceCountry = MapHolder.getMap().getCountryByName(this.d_countryFrom);
        Country targetCountry = MapHolder.getMap().getCountryByName(this.d_countryTo);

        if (sourceCountry != null && targetCountry != null) {
            int sourceArmies = sourceCountry.getArmiesDeployed(); // Store the number of armies deployed in a variable
            if (sourceArmies >= this.d_noOfArmies) {
                if (sourceCountry.getPlayer() != null && !sourceCountry.getPlayer().equals(targetCountry.getPlayer())) {
                    boolean conquered = simulateAttack(sourceCountry, targetCountry);

                    if (conquered) {
                        // Transfer ownership
                        targetCountry.setPlayer(sourceCountry.getPlayer());
                        System.out.println("Conquered " + targetCountry.getName());
                    } else {
                        System.out.println("Attack on " + targetCountry.getName() + " failed.");
                    }
                } else {
                    System.out.println("Invalid operation. Either countries don't exist or not enough armies.");
                }
                // Move the specified number of armies from the source country to the target country
                int remainingArmies = Math.max(0, sourceArmies - this.d_noOfArmies); // Use the stored variable here
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

    /**
     * Simulates an attack from one country to another and determines the outcome.
     * This method calculates the outcome of an attack based on the number of attacking
     * and defending armies. It uses a simple probabilistic model where each attacking army
     * has a 60% chance of winning against a defending army in each round. The battle continues
     * until either the attacking or defending armies are depleted.
     * If the attackers conquer the target country (i.e., all defending armies are defeated),
     * it is assumed that a third of the attacking armies are lost in the battle, and the
     * remaining forces are moved to the target country. If the attack fails, all attacking
     * armies are considered lost.
     *
     * @param sourceCountry The country from which the attack is launched.
     * @param targetCountry The country being attacked.
     * @return True if the attack was successful and the target country was conquered, false otherwise.
     */
    public boolean simulateAttack(Country sourceCountry, Country targetCountry) {
        int attackingArmies = d_noOfArmies; // Number of armies attacking
        int defendingArmies = targetCountry.getArmiesDeployed(); // Number of armies defending
        Random rand = new Random();
        while (attackingArmies > 0 && defendingArmies > 0) {
            if (rand.nextDouble() < 0.6) { // Assuming attackers have a 60% chance to win each small round
                defendingArmies--; // Attacker wins this small round
            } else {
                attackingArmies--; // Defender wins this small round
            }
        }

        // If defending armies are depleted, attack is successful
        boolean conquered = defendingArmies <= 0;

        if (conquered) {
            // Assuming attackers lose a third of their forces in a successful attack, rounded up
            int survivingArmies = attackingArmies - (int) Math.ceil(attackingArmies / 3.0);
            targetCountry.setArmiesDeployed(survivingArmies); // Update target country with surviving armies
        } else {
            // If attack fails, all attacking armies are lost.
            sourceCountry.setArmiesDeployed(sourceCountry.getArmiesDeployed() - d_noOfArmies);
        }

        return conquered;
    }


    @Override
    public String toString() {
        return "Advancing " + this.d_noOfArmies + " armies from " + this.d_countryFrom + " to " + this.d_countryTo + ".";
    }

}
