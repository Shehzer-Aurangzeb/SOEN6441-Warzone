package models.Order.Deploy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BombOrderTest {

    @BeforeEach
    public void setUp() {
        // Mock the MapHolder
        MapHolder.setMap(mock(Map.class));
    }

    @Test
    public void testExecute_ValidTargetCountry() {
        // Create a mock target country
        Country targetCountry = mock(Country.class);

        // Set up mock behavior for the map to return the mock target country
        when(MapHolder.getMap().getCountryByID(1)).thenReturn(targetCountry);

        // Set up the target country with initial armies
        when(targetCountry.getArmiesDeployed()).thenReturn(5);

        // Create a bomb order targeting the mock country
        BombOrder bombOrder = new BombOrder(1);

        // Execute the bomb order
        bombOrder.execute();

        // Verify that the number of armies in the target country has decreased by 1
        verify(targetCountry).setArmiesDeployed(4);

        // Verify the output message
        assertEquals("Bombing country 1. Armies reduced to 4.", bombOrder.toString());
    }

    @Test
    public void testExecute_InvalidTargetCountry() {
        // Create a mock Country object
        Country targetCountryMock = mock(Country.class);

        // Mock the behavior of the Map object
        Map mapMock = mock(Map.class);
        MapHolder.setMap(mapMock);

        // Set up the map mock to return the mock Country object for country ID 1
        when(mapMock.getCountryByID(1)).thenReturn(targetCountryMock);

        // Set up the mock Country object to return 0 for getArmiesDeployed method
        when(targetCountryMock.getArmiesDeployed()).thenReturn(0);

        // Create a bomb order targeting an invalid country
        BombOrder bombOrder = new BombOrder(1);

        // Execute the bomb order
        bombOrder.execute();

        // Verify that the setArmiesDeployed method is never called
        verify(targetCountryMock, never()).setArmiesDeployed(anyInt());

        // Verify the output message
        assertEquals("Cannot bomb country 1. No armies left.", bombOrder.toString());
    }


}
