package models.Continent;


import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContinentTest {

    private static Continent continent;

    @BeforeEach
    public void setUp() {
        continent = mock(Continent.class);
    }

    @Test
    void getName() {
        String expectedName = "Test Continent";

        // Set up mock behavior
        when(continent.getName()).thenReturn(expectedName);

        // Test the getName method
        assertEquals(expectedName, continent.getName());
    }

    @Test
    void getArmyBonus() {
        int expectedArmyBonus = 5;

        // Set up mock behavior
        when(continent.getArmyBonus()).thenReturn(expectedArmyBonus);

        // Test the getArmyBonus method
        assertEquals(expectedArmyBonus, continent.getArmyBonus());
    }

    @Test
    void getID() {
        // Define test data
        int expectedId = 1;

        // Set up mock behavior
        when(continent.getID()).thenReturn(expectedId);
        // Test the getID method
        assertEquals(expectedId, continent.getID());
    }

    @AfterEach
    public void tearDown(){
        continent=null;
    }
}