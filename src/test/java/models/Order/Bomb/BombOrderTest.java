package models.Order.Bomb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Country.Country;
import models.Enums.PlayerType;
import models.GameContext.GameContext;
import models.Map.Map;
import models.Order.Bomb.BombOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BombOrderTest {
    private GameContext gameContext;
    private Country targetCountry;
    @BeforeEach
    public void setUp() {
        gameContext= GameContext.getInstance();
        // Create a mock target country
        targetCountry = new Country("TargetCountry",1);
        targetCountry.setPlayer(PlayerType.PLAYER); // Set the player owning the country
        targetCountry.setArmiesDeployed(10); // Set the number of armies deployed in the country

        // Add the target country to the map
        gameContext.getMap().addCountry(targetCountry);
    }
    @AfterEach
    public void tearDown(){
        gameContext=null;
        targetCountry=null;
    }

    @Test
    public void testExecute_TargetCountryExistsAndHasArmies() {
        // Create an instance of BombOrder with the ID of the target country
        BombOrder bombOrder = new BombOrder(targetCountry.getID());

        // Call execute() method
        bombOrder.execute();

        // Assert that the number of armies in the target country is reduced by 1
        assertEquals(9, targetCountry.getArmiesDeployed());
    }
    @Test
    public void testExecute_TargetCountryHasNoArmies() {
        // Set the number of armies in the target country to 0
        targetCountry.setArmiesDeployed(0);

        // Create an instance of BombOrder with the ID of the target country
        BombOrder bombOrder = new BombOrder(targetCountry.getID());

        // Call execute() method
        bombOrder.execute();

        // Assert that no change occurs as the target country has no armies
        assertEquals(0, targetCountry.getArmiesDeployed());
    }

    @Test
    public void testExecute_InvalidTargetCountry() {
        // Create an instance of BombOrder with an invalid target country ID
        BombOrder bombOrder = new BombOrder(-1);

        // Call execute() method
        bombOrder.execute();

        // Assert that no change occurs as the target country is invalid
        assertEquals(10, targetCountry.getArmiesDeployed());
    }

}
