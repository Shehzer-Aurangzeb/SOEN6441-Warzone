package models.Order.Deploy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Enums.PlayerType;
import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlockadeOrderTest {

    @BeforeEach
    public void setUp() {
        // Mock the MapHolder
        MapHolder.setMap(mock(Map.class));
    }

    @Test
    public void testExecute_ValidTargetCountry() {
        // Create a mock target country owned by a player
        Country targetCountry = mock(Country.class);
        when(targetCountry.getPlayer()).thenReturn(PlayerType.PLAYER); // Owned by a player
        when(targetCountry.getArmiesDeployed()).thenReturn(6); // Initial armies

        // Set up mock behavior for the map to return the mock target country
        when(MapHolder.getMap().getCountryByID(1)).thenReturn(targetCountry);

        // Create a blockade order targeting the mock country
        BlockadeOrder blockadeOrder = new BlockadeOrder(1);

        // Execute the blockade order
        blockadeOrder.execute();

        // Verify that the number of armies in the target country has been reduced to half
        verify(targetCountry).setArmiesDeployed(3);

        // Verify that the player of the target country has been set to neutral
        verify(targetCountry).setPlayer(PlayerType.NEUTRAL);

        // Verify the output message
        assertEquals("Blockading country 1.", blockadeOrder.toString());
    }

    @Test
    public void testExecute_NeutralTargetCountry() {
        // Create a mock target country that is neutral
        Country targetCountry = mock(Country.class);
        when(targetCountry.getPlayer()).thenReturn(PlayerType.NEUTRAL); // Neutral
        when(targetCountry.getArmiesDeployed()).thenReturn(6); // Initial armies

        // Set up mock behavior for the map to return the mock target country
        when(MapHolder.getMap().getCountryByID(1)).thenReturn(targetCountry);

        // Create a blockade order targeting the mock country
        BlockadeOrder blockadeOrder = new BlockadeOrder(1);

        // Execute the blockade order
        blockadeOrder.execute();

        // Verify that no changes are made to the target country
       // verify(targetCountry).getPlayer();
        //verify(targetCountry).getArmiesDeployed();

        // Verify the output message
        verify(targetCountry, never()).getArmiesDeployed();
        assertEquals("Blockading country 1.", blockadeOrder.toString());
    }

    @Test
    public void testExecute_InvalidTargetCountry() {
        // Set up the map to return null for the target country
        when(MapHolder.getMap().getCountryByID(1)).thenReturn(null);

        // Create a blockade order targeting an invalid country
        BlockadeOrder blockadeOrder = new BlockadeOrder(1);

        // Execute the blockade order
        blockadeOrder.execute();

        // Verify the output message
        assertEquals("Blockading country 1.", blockadeOrder.toString());
    }
}
