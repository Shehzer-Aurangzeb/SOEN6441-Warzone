package models.Map;

import models.Continent.Continent;
import models.Country.Country;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    private Map map;
    @BeforeEach
    void setUp() {
        map = new Map();
    }


    @Test
    void addCountry() {
        // Create a country
        Country country = new Country(1, "Country1", 1);

        // Add country to the map
        map.addCountry(country);

        // Check to see if country is added to the map
        ArrayList<Country> countries = map.getCountries();
        assertNotNull(countries);
        assertEquals(1, countries.size());
        assertEquals(country, countries.get(0));
    }

    @Test
    void addContinent() {
        // Create a continent
        Continent continent = new Continent("Continent1", 5);

        // Add continent to the map
        map.addContinent(continent);

        // Check to see if continent is added to the map
        ArrayList<Continent> continents = map.getContinents();
        assertNotNull(continents);
        assertEquals(1, continents.size());
        assertEquals(continent, continents.get(0));
    }

    @Test
    void getCountries() {
        ArrayList<Country> countries = map.getCountries();

        // Check if an empty list is returned
        assertNotNull(countries);
        assertEquals(0, countries.size());
    }

    @Test
    void getCountryByID_CountryExists() {
        // Create and add a country to the map
        Country country = new Country(1, "Country1", 1);
        map.addCountry(country);

        // Get the country by ID
        Country foundCountry = map.getCountryByID(1);

        // Check if the correct country is returned
        assertNotNull(foundCountry);
        assertEquals(country, foundCountry);
    }
    @Test
    void getCountryByID_CountryDoesNotExist() {
        Country foundCountry = map.getCountryByID(1);

        assertNull(foundCountry);
    }
    @Test
    void getContinentByID_ContinentDoesNotExist() {
        Continent foundContinent = map.getContinentByID(1);
        assertNull(foundContinent);
    }

    @Test
    void getContinents() {
        ArrayList<Continent> continents = map.getContinents();
        // Check if an empty list is returned
        assertNotNull(continents);
        assertEquals(0, continents.size());

    }
}