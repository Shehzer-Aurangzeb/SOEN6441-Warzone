package models.Player;

import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player player;
    private String[] command;
    private int countryID;
    private int noOfArmies;
    private ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map();
        player = new Player("Player A");
        MapHolder.setMap(map);
        // Set up test data
        command = new String[]{"deploy", "1", "5"};
        countryID = Integer.parseInt(command[1]);
        noOfArmies = Integer.parseInt(command[2]);
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

    @AfterEach
    void tearDown() {
        // Reset System.out
        System.setOut(System.out);
    }
}
