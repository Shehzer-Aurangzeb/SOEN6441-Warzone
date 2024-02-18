package models.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import models.Continent.Continent;
import models.Country.Country;

public class MapValidatorTest {
    @BeforeEach
    void setUp() {
    }


    @Test
    public void testValidMap() {
        // Create a valid map with connected graph, connected continents, and unique country names
        Map map = createValidMap();
        assertTrue(MapValidator.validateMap(map));
    }

    @Test
    public void testInvalidMapDisconnectedGraph() {
        // Create an invalid map with a disconnected graph
        Map map = createInvalidMapDisconnectedGraph();
        assertFalse(MapValidator.validateMap(map));
    }

    @Test
    public void testInvalidMapDisconnectedContinent() {
        // Create an invalid map with a disconnected continent
        Map map = createInvalidMapDisconnectedContinent();
        assertFalse(MapValidator.validateMap(map));
    }

    @Test
    public void testInvalidMapDuplicateCountryName() {
        // Create an invalid map with duplicate country names
        Map map = createInvalidMapDuplicateCountryName();
        assertFalse(MapValidator.validateMap(map));
    }

    private Map createValidMap() {
        Map map = new Map();

        // Create continents
        Continent continent1 = new Continent("Continent1", 3);
        Continent continent2 = new Continent("Continent2", 2);
        map.addContinent(continent1);
        map.addContinent(continent2);

        // Create countries
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country country3 = new Country(3, "Country3", 2);
        Country country4 = new Country(4, "Country4", 2);

        // Connect countries to form a connected graph
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);
        country2.addNeighbor(country3);
        country3.addNeighbor(country2);
        country3.addNeighbor(country4);
        country4.addNeighbor(country3);

        // Add countries to the map
        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(country3);
        map.addCountry(country4);

        return map;
    }

    private Map createInvalidMapDisconnectedGraph() {
        Map map = new Map();

        // Create a continent
        Continent continent = new Continent("Continent1", 3);
        map.addContinent(continent);

        // Create countries
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country country3 = new Country(3, "Country3", 1);

        // Connect country1 and country2, but country3 is not connected
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);

        // Add countries to the map
        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(country3);

        return map;
    }

    private Map createInvalidMapDisconnectedContinent() {
        Map map = new Map();

        // Create continents
        Continent continent1 = new Continent("Continent1", 3);
        Continent continent2 = new Continent("Continent2", 2);
        map.addContinent(continent1);
        map.addContinent(continent2);

        // Create countries
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country country3 = new Country(3, "Country3", 2);
        Country country4 = new Country(4, "Country4", 2);

        // Connect country1 and country2 in Continent1
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);
        country2.addNeighbor(country3);
        country3.addNeighbor(country2);


        // Add countries to the map
        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(country3);
        map.addCountry(country4);

        return map;
    }

    private Map createInvalidMapDuplicateCountryName() {
        Map map = new Map();

        // Create continents
        Continent continent1 = new Continent("Continent1", 3);
        map.addContinent(continent1);

        // Create countries
        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country duplicateCountry = new Country(3, "Country1", 1); // Duplicate name

        // Connect countries to form a connected graph
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);
        country2.addNeighbor(duplicateCountry);
        duplicateCountry.addNeighbor(country2);

        // Add countries to the map
        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(duplicateCountry);

        return map;
    }

}
