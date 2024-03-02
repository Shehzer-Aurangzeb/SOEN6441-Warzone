package models.Order.Deploy;

import models.Enums.OrderType;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Order.Diplomacy.DiplomacyOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DiplomacyOrderTest {
    @BeforeEach
    public void setUp() {
        // Mock the MapHolder
        MapHolder.setMap(mock(Map.class));

    }
    @Test
    void testConstructorAndGetPlayerID() {
        // Arrange
        int playerID = 1;

        // Act
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(playerID);

        // Assert
        assertEquals(playerID, diplomacyOrder.getPlayerID());
    }

    @Test
    void testGetName() {
        // Arrange
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(1);

        // Act
        OrderType orderType = diplomacyOrder.getName();

        // Assert
        assertEquals(OrderType.DIPLOMACY, orderType);
    }
}
