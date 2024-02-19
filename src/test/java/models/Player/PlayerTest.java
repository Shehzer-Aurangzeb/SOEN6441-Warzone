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

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private String [] command;
    private int countryID;
    private int noOfArmies;
    private ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
    private Map map ;
    @BeforeEach
    void setUp() {
        map = new Map();
        player = new Player("Player A");
        MapHolder.setMap(map);
        // Set up test data
        command = new String[]{"deploy", "1", "5"};
        countryID = Integer.parseInt(command[1]);
        noOfArmies =  Integer.parseInt(command[2]);
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
    void issue_order_countryDoesNotExist() {
        System.setOut(new PrintStream(outContent1));
        //give player 5 armies.
        player.setNoOfArmies(5);
        // Invoke the method
        player.createDeployOrder(command);

        //1. when country does not exist.
        assertEquals(outContent1.toString(),"\nInvalid country ID. Country does not exist.\n");

    }

    @Test
    void issue_order_playerDoesNotOwnTheCountry() {
        System.setOut(new PrintStream(outContent1));
        Country country = new Country(countryID,"Country A",1);
        map.addCountry(country);
        player.setNoOfArmies(5);
        // Invoke the method
        player.createDeployOrder(command);

        assertEquals(outContent1.toString(),"\nCannot deploy armies to country " +countryID+". You do not own this country. Please select a country that you own to deploy your armies\n");


    }
    @Test
    void issue_order_playerDoesNotHaveEnoughArmies() {
        System.setOut(new PrintStream(outContent1));
        Country country = new Country(countryID,"Country A",1);
        map.addCountry(country);
        // Invoke the method
        player.createDeployOrder(command);

        assertEquals(outContent1.toString(),"\nYou do not have enough armies.\n");


    }
    @Test
    void next_order() {
    }

    @AfterEach
    void tearDown(){
        // Reset System.out
        System.setOut(System.out);
    }
}