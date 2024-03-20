package models.Order.Diplomacy;

import models.Enums.OrderType;
import models.Player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiplomacyOrderTest {
    @Test
    public void testGetName() {
        // Create a DiplomacyOrder instance with a player name
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder("PlayerName");

        // Check if the name returned by getName() matches the expected OrderType
        assertEquals(OrderType.DIPLOMACY, diplomacyOrder.getName());
    }

    @Test
    public void testGetPlayerName() {
        // Create a DiplomacyOrder instance with a player name
        String playerName = "PlayerName";
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(playerName);

        // Check if the player name returned by getPlayerName() matches the expected value
        assertEquals(playerName, diplomacyOrder.getPlayerName());
    }

    @Test
    public void testExecute() {
        // Create a DiplomacyOrder instance with a player name
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder("PlayerName");

        // Placeholder test; actual implementation of execute() should be tested separately
    }
}
