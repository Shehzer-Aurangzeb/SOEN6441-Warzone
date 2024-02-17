package models.Player;

import models.Country.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    @BeforeEach
    void setUp() {
        player = new Player("Player A");
    }

    @Test
    void getName() {
        assertEquals("Player A", player.getName());
    }

    @Test
    void getOrders() {
        assertEquals(0, player.getOrders().size()); // Initially, the list should be empty
    }

    @Test
    void getOwnedCountries() {
        assertEquals(0, player.getOwnedCountries().size()); // Initially, the list should be empty
    }

    @Test
    void addOwnedCountry() {
        Country country = new Country(1, "Country A", 1);
        // Add the country and check if it is added
        player.addOwnedCountry(country);
        ArrayList<Country> ownedCountries = player.getOwnedCountries();
        assertEquals(1, ownedCountries.size());
        assertEquals(country, ownedCountries.get(0));
    }

    @Test
    void issue_order() {
    }

    @Test
    void next_order() {
    }
}