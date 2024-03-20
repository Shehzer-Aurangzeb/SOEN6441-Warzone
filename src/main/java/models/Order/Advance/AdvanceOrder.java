package models.Order.Advance;

import models.Country.Country;
import models.Enums.OrderType;
import models.GameContext.GameContext;
import models.Order.Order;
import models.Player.Player;

import java.util.Random;

/**
 * Represents an order to advance armies from one country to another.
 */
public class AdvanceOrder implements Order {
    private String d_countryFrom;
    private String d_countryTo;
    private int d_noOfArmies;
    private static final GameContext d_ctx = GameContext.getInstance();

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
    @Override
    public OrderType getName() {
        return OrderType.ADVANCE;
    }

    /**
     * Executes the advance order.
     *
     * @param p_player the player who issues the order.
     */
    public void execute(Player p_player) {

        // Retrieve the source country and target country from the map
        Country sourceCountry = d_ctx.getMap().getCountryByName(this.d_countryFrom);
        Country targetCountry = d_ctx.getMap().getCountryByName(this.d_countryTo);
        int numArmies = this.d_noOfArmies;
        // Check ownership of the source country
        if (!p_player.getOwnedCountries().contains(sourceCountry)) {
            System.out.println("\nYou do not own the source country.");
            return;
        }
        // Check if the player has enough armies to advance
        if (sourceCountry.getArmiesDeployed() < numArmies) {
            // If the player doesn't have enough armies, move all available armies
            this.d_noOfArmies = sourceCountry.getArmiesDeployed();
        }

        if (p_player.getOwnedCountries().contains(targetCountry)) {
            // Move armies from source to target country
            moveArmies(sourceCountry, targetCountry);
            System.out.println(this.d_noOfArmies + " armies moved from " + sourceCountry.getName() + " to " + targetCountry.getName() + ".");
        } else {
            simulateAttack(sourceCountry, targetCountry);
        }
    }

    /**
     * Simulates an attack between two territories.
     *
     * @param attacker The country launching the attack.
     * @param defender The country being attacked.
     */
    public void simulateAttack(Country attacker, Country defender) {
        int numOfAttackingArmies = this.d_noOfArmies;
        int attackerCasualties = 0;
        int defenderCasualties = 0;

        // Simulate battle
        for (int i = 0; i < numOfAttackingArmies; i++) {
            if (Math.random() < 0.6) { // Attacker's army has a 60% chance of killing a defender's army
                defenderCasualties++;
            }
        }
        for (int i = 0; i < defender.getArmiesDeployed(); i++) {
            if (Math.random() < 0.7) { // Defender's army has a 70% chance of killing an attacker's army
                attackerCasualties++;
            }
        }

        attacker.setArmiesDeployed(attacker.getArmiesDeployed() - numOfAttackingArmies);
        defender.setArmiesDeployed(Math.max(0, defender.getArmiesDeployed() - defenderCasualties));
        // Determine the outcome of the attack
        if (defender.getArmiesDeployed() == 0) {
            int survivingArmies = numOfAttackingArmies - attackerCasualties;
            // Attacker captures the territory
            defender.setArmiesDeployed(survivingArmies);
            // Update ownership
            defender.setOwner(attacker.getOwner());
            attacker.getOwner().addOwnedCountry(defender);
            defender.getOwner().removeOwnedCountry(defender);
            System.out.println("The attack was successful! " + defender.getName() + " has been conquered by " + attacker.getOwner().getName() + ", with " + survivingArmies + " armies remaining.");
            d_ctx.updateLog("The attack was successful! " + defender.getName() + " has been conquered by " + attacker.getOwner().getName() + ", with " + survivingArmies + " armies remaining.");
        } else {
            System.out.println("The defending forces have repelled the attack! " + defender.getName() + " successfully defends against " + attacker.getOwner().getName() + ", with " + defender.getArmiesDeployed() + " armies remaining.");
            d_ctx.updateLog("The defending forces have repelled the attack! " + defender.getName() + " successfully defends against " + attacker.getOwner().getName() + ", with " + defender.getArmiesDeployed() + " armies remaining.");
        }
    }

    private void moveArmies(Country from, Country to) {
        to.setArmiesDeployed(to.getArmiesDeployed() + this.d_noOfArmies);
        from.setArmiesDeployed(Math.max(0, from.getArmiesDeployed() - this.d_noOfArmies));
    }

    @Override
    public String toString() {
        return "Advancing " + this.d_noOfArmies + " armies from " + this.d_countryFrom + " to " + this.d_countryTo + ".";
    }

}
