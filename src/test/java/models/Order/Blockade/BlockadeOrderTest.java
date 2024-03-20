package models.Order.Blockade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Enums.PlayerType;
import models.Country.Country;
import models.GameContext.GameContext;
import models.Map.Map;
import models.Order.Blockade.BlockadeOrder;
import models.Player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockadeOrderTest {
    private GameContext gameContext;
    private Country targetCountry;
    private Player player;
    @BeforeEach
    public void setUp() {
        // Create a new GameContext instance
        gameContext = GameContext.getInstance();

        targetCountry = new Country("TargetCountry",1);
        targetCountry.setPlayer(PlayerType.PLAYER); // Set the player owning the country
        targetCountry.setArmiesDeployed(10); // Set the number of armies deployed in the country
        player= new Player("test-player");

        // Add the target country to the map
        gameContext.getMap().addCountry(targetCountry);
        gameContext.addPlayer(player);
    }
    @AfterEach
    public void tearDown() {
        // Create a new GameContext instance
        gameContext =null;
        targetCountry = null;
        player=null;
    }

    @Test
    public void testExecute_TargetCountryExistsAndOwnedByPlayer() {
        // Create an instance of BlockadeOrder with the ID of the target country
        BlockadeOrder blockadeOrder = new BlockadeOrder(targetCountry.getID());

        // Call execute() method
        blockadeOrder.execute(player);

        // Assert that half of the armies are removed from the target country and it becomes neutral
        assertEquals(5, targetCountry.getArmiesDeployed());
        assertEquals(PlayerType.NEUTRAL, targetCountry.getPlayer());
    }

    @Test
    public void testExecute_TargetCountryIsNeutral() {
        // Set the target country as neutral
        targetCountry.setPlayer(PlayerType.NEUTRAL);

        // Create an instance of BlockadeOrder with the ID of the target country
        BlockadeOrder blockadeOrder = new BlockadeOrder(targetCountry.getID());

        // Call execute() method
        blockadeOrder.execute(player);

        // Assert that no change occurs as neutral countries cannot be blockaded
        assertEquals(10, targetCountry.getArmiesDeployed());
        assertEquals(PlayerType.NEUTRAL, targetCountry.getPlayer());
    }

    @Test
    public void testExecute_InvalidTargetCountry() {
        // Create an instance of BlockadeOrder with an invalid target country ID
        BlockadeOrder blockadeOrder = new BlockadeOrder(-1);

        // Call execute() method
        blockadeOrder.execute(player);

        // Assert that no change occurs as the target country is invalid
        assertEquals(10, targetCountry.getArmiesDeployed());
        assertEquals(PlayerType.PLAYER, targetCountry.getPlayer());
    }

}
