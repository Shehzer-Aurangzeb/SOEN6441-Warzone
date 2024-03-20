package models.Order.Advance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import models.Country.Country;
import models.GameContext.GameContext;
import models.Map.Map;
import org.junit.jupiter.api.*;

public class AdvanceOrderTest {
    private GameContext gameContext;
    private Country sourceCountry;
    private Country targetCountry;
    @BeforeEach
    public void setUp() {
        // Create a new GameContext instance
        gameContext = GameContext.getInstance();

        // Create mock countries
        sourceCountry = new Country("SourceCountry",1);
        targetCountry = new Country("TargetCountry",1);

        // Add the countries to the map
        gameContext.getMap().addCountry(sourceCountry);
        gameContext.getMap().addCountry(targetCountry);
    }
    @AfterEach
    public void tearDown() {
        // Create a new GameContext instance
        gameContext = null;
        // Create mock countries
        sourceCountry =null;
        targetCountry = null;
    }

//    @Test
//    public void testExecute_SourceCountryHasEnoughArmies() {
//        // Set armies deployed in the source country
//        int sourceArmies = 10;
//        sourceCountry.setArmiesDeployed(sourceArmies);
//
//        // Create an instance of AdvanceOrder with enough armies
//        int numArmies = 5;
//        AdvanceOrder advanceOrder = new AdvanceOrder("SourceCountry", "TargetCountry", numArmies);
//
//        // Call execute() method
//        advanceOrder.execute();
//
//        // Assert that the armies are correctly moved from the source to the target country
//        assertEquals(sourceArmies - numArmies, sourceCountry.getArmiesDeployed());
//        assertEquals(numArmies, targetCountry.getArmiesDeployed());
//    }

    @Test
    public void testExecute_SourceCountryDoesNotHaveEnoughArmies() {
        // Set fewer armies deployed in the source country than required for the order
        int sourceArmies = 2;
        sourceCountry.setArmiesDeployed(sourceArmies);

        // Create an instance of AdvanceOrder with more armies than available in the source country
        int numArmies = 5;
        AdvanceOrder advanceOrder = new AdvanceOrder("SourceCountry", "TargetCountry", numArmies);

        // Call execute() method
        advanceOrder.execute();

        // Assert that no armies are moved from the source to the target country
        assertEquals(sourceArmies, sourceCountry.getArmiesDeployed());
        assertEquals(0, targetCountry.getArmiesDeployed());
    }

    @Test
    public void testExecute_InvalidCountries() {
        // Create an instance of AdvanceOrder with invalid source and target countries
        int numArmies = 5;
        AdvanceOrder advanceOrder = new AdvanceOrder("InvalidSourceCountry", "InvalidTargetCountry", numArmies);

        // Call execute() method
        advanceOrder.execute();

        // Assert that no armies are moved from the source to the target country
        assertEquals(0, sourceCountry.getArmiesDeployed());
        assertEquals(0, targetCountry.getArmiesDeployed());
    }

    @Disabled
    @Test
    public void simulateAttack_ConquestSuccessful() {

        sourceCountry.setArmiesDeployed(10);
        targetCountry.setArmiesDeployed(5);

        // Create an AdvanceOrder object
        AdvanceOrder advanceOrder = new AdvanceOrder("SourceCountry", "TargetCountry", 7);

        // Execute the simulateAttack function
        advanceOrder.simulateAttack(sourceCountry, targetCountry);

        // Ensure that the target country was conquered
        assertEquals("SourceCountry", targetCountry.getPlayer());
        // Ensure that the surviving armies are as expected
        assertEquals(5, sourceCountry.getArmiesDeployed());
        assertEquals(2, targetCountry.getArmiesDeployed());
    }
}
