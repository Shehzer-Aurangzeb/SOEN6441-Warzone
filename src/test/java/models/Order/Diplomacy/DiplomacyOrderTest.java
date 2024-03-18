package models.Order.Diplomacy;

import models.Enums.OrderType;
import models.GameContext.GameContext;
import models.Map.Map;
import models.Order.Diplomacy.DiplomacyOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DiplomacyOrderTest {
    @BeforeEach
    public void setUp() {


    }
    @Test
    public void testGetName() {
        // Create a DiplomacyOrder instance with a player ID
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(1);

        // Check if the name returned by getName() matches the expected OrderType
        assertEquals(OrderType.DIPLOMACY, diplomacyOrder.getName());
    }

    @Test
    public void testGetPlayerID() {
        // Create a DiplomacyOrder instance with a player ID
        int playerID = 5;
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(playerID);

        // Check if the player ID returned by getPlayerID() matches the expected value
        assertEquals(playerID, diplomacyOrder.getPlayerID());
    }

    @Test
    public void testExecute() {
        // Create a DiplomacyOrder instance with a player ID
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(1);

        // Call execute() method
        diplomacyOrder.execute();

        // Placeholder test; actual implementation of execute() should be tested separately
    }
}
