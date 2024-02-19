package models.Map;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import models.Continent.Continent;
import models.Country.Country;

import java.util.ArrayList;

public class MapValidatorTest {
    private Map map;


    @BeforeEach
    void setUp() {
        map = new Map();
    }

    @AfterEach
    void tearDown() {
        map = null;
    }

    //@Disabled
    @Test
    void ValidMap() {
        map = createValidMap();
        assertTrue(MapValidator.validateMap(map));
    }

    @Test
    void InvalidMapDisconnectedGraph() {
        map = createInvalidMapDisconnectedGraph();
        assertFalse(MapValidator.validateMap(map));
    }

    @Test
    void InvalidMapDisconnectedSubgraphs() {
        map = createInvalidMapDisconnectedSubgraphs();
        assertFalse(MapValidator.validateMap(map));
    }

    @Test
    void InvalidMapDuplicateCountryName() {
        map = createInvalidMapDuplicateCountryName();
        assertFalse(MapValidator.validateMap(map));
    }

    @Test
    void InvalidMapContinentWithoutCountry () {
        map = createInvalidMapContinentWithoutAnyCountry();
        assertFalse(MapValidator.validateMap(map));
    }


    // Created different types of Demo maps for checking the validation logic of the map
    private Map createValidMap() {
        Map map = new Map();

        Continent continent1 = new Continent("Continent1", 3);
        Continent continent2 = new Continent("Continent2", 2);
        map.addContinent(continent1);
        map.addContinent(continent2);

        ArrayList<Continent> continents = map.getContinents();
        int i = 1;
        for (Continent continent : continents){
            continent.setID(i);
            i++;
        }

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

        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(country3);
        map.addCountry(country4);

        return map;
    }

    private Map createInvalidMapDisconnectedGraph() {
        Map map = new Map();

        Continent continent1 = new Continent("Continent1", 3);
        Continent continent2 = new Continent("Continent2", 2);
        Continent continent3 = new Continent("Continent3", 5);
        map.addContinent(continent1);
        map.addContinent(continent2);
        map.addContinent(continent3);

        ArrayList<Continent> continents = map.getContinents();
        int i = 1;
        for (Continent continent : continents){
            continent.setID(i);
            i++;
        }

        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country country3 = new Country(3, "Country3", 2);
        Country country4 = new Country(4, "Country4", 2);
        Country country5 = new Country(5, "Country5", 2);
        Country country6 = new Country(6, "Country6", 3);

        // Continent1 and Continent2 are not connected
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);

        country3.addNeighbor(country4);
        country4.addNeighbor(country3);
        country4.addNeighbor(country5);
        country5.addNeighbor(country4);

        country6.addNeighbor(country1);
        country1.addNeighbor(country6);


        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(country3);
        map.addCountry(country4);
        map.addCountry(country5);
        map.addCountry(country6);

        return map;
    }

    private Map createInvalidMapDisconnectedSubgraphs() {
        Map map = new Map();

        Continent continent1 = new Continent("Continent1", 3);
        Continent continent2 = new Continent("Continent2", 2);
        map.addContinent(continent1);
        map.addContinent(continent2);

        ArrayList<Continent> continents = map.getContinents();
        int i = 1;
        for (Continent continent : continents){
            continent.setID(i);
            i++;
        }

        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country country3 = new Country(3, "Country3", 2);
        Country country4 = new Country(4, "Country4", 2);
        Country country5 = new Country(5, "Country5", 2);

        // Connect country4 and country5 are not connected
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);
        country2.addNeighbor(country3);
        country3.addNeighbor(country2);
        country3.addNeighbor(country4);
        country4.addNeighbor(country3);

        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(country3);
        map.addCountry(country4);
        map.addCountry(country5);

        return map;
    }

    private Map createInvalidMapDuplicateCountryName() {
        Map map = new Map();

        Continent continent1 = new Continent("Continent1", 3);
        map.addContinent(continent1);

        ArrayList<Continent> continents = map.getContinents();
        int i = 1;
        for (Continent continent : continents){
            continent.setID(i);
            i++;
        }

        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);
        Country duplicateCountry = new Country(3, "Country1", 1); // Duplicate name

        // Connect countries to form a connected graph
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);
        country2.addNeighbor(duplicateCountry);
        duplicateCountry.addNeighbor(country2);

        map.addCountry(country1);
        map.addCountry(country2);
        map.addCountry(duplicateCountry);

        return map;
    }

    private Map createInvalidMapContinentWithoutAnyCountry() {
        Map map = new Map();

        Continent continent1 = new Continent("Continent1", 3);
        Continent continent2 = new Continent("Continent2", 2);
        map.addContinent(continent1);
        map.addContinent(continent2);

        ArrayList<Continent> continents = map.getContinents();
        int i = 1;
        for (Continent continent : continents){
            continent.setID(i);
            i++;
        }

        Country country1 = new Country(1, "Country1", 1);
        Country country2 = new Country(2, "Country2", 1);

        country1.addNeighbor(country2);
        country2.addNeighbor(country1);

        map.addCountry(country1);
        map.addCountry(country2);

        return map;
    }


}
