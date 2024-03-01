package models.Order.Deploy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeployOrderTest {

    @BeforeEach
    public void setUp() {
        // Mock the MapHolder
        MapHolder.setMap(mock(Map.class));

        // Mock the behavior of the getCountryByID method to return a non-null country
        Country country = mock(Country.class);
        when(MapHolder.getMap().getCountryByID(1)).thenReturn(country);
    }

    @Test
    public void testExecute() {
        // Create a mock player
        Player player = mock(Player.class);

        // Create a mock country and add it to the map
        Country country = mock(Country.class);
        when(MapHolder.getMap().getCountryByID(1)).thenReturn(country);

        // Create a deploy order
        DeployOrder deployOrder = new DeployOrder(1, 5, player);

        // Execute the deploy order
        deployOrder.execute();

        // Verify that the getCountryByID method was called once with the correct argument
        verify(MapHolder.getMap(), times(1)).getCountryByID(1);

        // Verify that the setArmiesDeployed method was called once with the correct argument
        verify(country, times(1)).setArmiesDeployed(5);
    }

    @Test
    public void testToString() {
        // Create a deploy order
        DeployOrder deployOrder = new DeployOrder(1, 5, null); // Player is not needed for this test

        // Verify the toString representation
        assertEquals("Deploying 5 armies to reinforce country 1.", deployOrder.toString());
    }
}
