package models.Order.Deploy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import models.Country.Country;
import models.MapHolder.MapHolder;
import models.Order.Advance.AdvanceOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdvanceOrderTest {

    @BeforeEach
    public void setUp() {
        // Mock the MapHolder
        MapHolder.setMap(mock(models.Map.Map.class));
    }

    @Test
    public void testExecute_SourceCountryHasEnoughArmies() {
        // Create mock countries for source and target
        Country sourceCountry = mock(Country.class);
        Country targetCountry = mock(Country.class);

        // Set up mock behavior for the map to return the mock countries
        when(MapHolder.getMap().getCountryByName("SourceCountry")).thenReturn(sourceCountry);
        when(MapHolder.getMap().getCountryByName("TargetCountry")).thenReturn(targetCountry);

        // Set up the source country to have enough armies
        when(sourceCountry.getArmiesDeployed()).thenReturn(10);

        // Create an advance order with enough armies
        AdvanceOrder advanceOrder = new AdvanceOrder("SourceCountry", "TargetCountry", 5);

        // Execute the advance order
        advanceOrder.execute();

        // Verify that the correct methods were called on the source and target countries
        verify(sourceCountry).setArmiesDeployed(5);
        verify(targetCountry).setArmiesDeployed(5);

        // Verify the output message
        assertEquals("Advancing 5 armies from SourceCountry to TargetCountry.", advanceOrder.toString());
    }

    @Test
    public void testExecute_SourceCountryDoesNotHaveEnoughArmies() {
        // Create mock countries for source and target
        Country sourceCountry = mock(Country.class);
        Country targetCountry = mock(Country.class);

        // Set up mock behavior for the map to return the mock countries
        when(MapHolder.getMap().getCountryByName("SourceCountry")).thenReturn(sourceCountry);
        when(MapHolder.getMap().getCountryByName("TargetCountry")).thenReturn(targetCountry);

        // Set up the source country to not have enough armies
        when(sourceCountry.getArmiesDeployed()).thenReturn(3);

        // Create an advance order with more armies than available in the source country
        AdvanceOrder advanceOrder = new AdvanceOrder("SourceCountry", "TargetCountry", 5);

        // Execute the advance order
        advanceOrder.execute();

        // Verify that the source country's armies are not changed
        verify(sourceCountry).getArmiesDeployed();
        verify(sourceCountry, never()).setArmiesDeployed(5);

        // Verify the output message
        assertEquals("Advancing 5 armies from SourceCountry to TargetCountry.", advanceOrder.toString());
    }

    @Test
    public void testExecute_InvalidCountries() {
        // Set up the map to return null for both countries
        when(MapHolder.getMap().getCountryByName("InvalidCountry")).thenReturn(null);

        // Create an advance order with invalid source and target countries
        AdvanceOrder advanceOrder = new AdvanceOrder("InvalidCountry", "InvalidCountry", 5);

        // Execute the advance order
        advanceOrder.execute();

        // Verify the output message
        assertEquals("Advancing 5 armies from InvalidCountry to InvalidCountry.", advanceOrder.toString());
    }
}
