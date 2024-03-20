package models.Order.Blockade;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Enums.PlayerType;
import models.Country.Country;
import models.GameContext.GameContext;
import models.Order.Blockade.BlockadeOrder;
import models.Player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

        targetCountry = new Country("TargetCountry", 1);
        targetCountry.setPlayer(PlayerType.PLAYER); // Set the player owning the country
        targetCountry.setArmiesDeployed(10); // Set the number of armies deployed in the country
        player = new Player("test-player");

        // Add the target country to the map
        gameContext.getMap().addCountry(targetCountry);
        gameContext.addPlayer(player);
    }

    @AfterEach
    public void tearDown() {
        // Resetting variables
        gameContext = null;
        targetCountry = null;
        player = null;
    }

    @Test
    public void testExecute_TargetCountryExistsAndOwnedByPlayer() {
        // Create an instance of BlockadeOrder with the ID of the target country
        BlockadeOrder blockadeOrder = new BlockadeOrder(targetCountry.getID());

        // Call execute() method
        blockadeOrder.execute(player);

        assertEquals(30, targetCountry.getArmiesDeployed());
        Assertions.assertNull(targetCountry.getOwner());
    }



}
