package models.Country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country country;
    @BeforeEach
    void setUp() {
        country=  new Country(1,"Country 1",1);
    }

    @Test
    void getID() {
        assertEquals(1,country.getID());
    }

    @Test
    void getName() {
        assertEquals("Country 1",country.getName());
    }

    @Test
    void getContinentID() {
        assertEquals(1,country.getContinentID());
    }

    @Test
    void getNeighbours() {
        //initially no neighbors.
        assertEquals(0,country.getNeighbours().size());
        //create a new country to add as a neighbor.
        Country neighbor = new Country(2, "Country B", 1);
        country.addNeighbor(neighbor);

        assertEquals(1, country.getNeighbours().size());
        assertEquals(neighbor, country.getNeighbours().get(0));
    }

    @Test
    void setID() {
        country.setID(2);
        assertEquals(2,country.getID());
    }

    @Test
    void setName() {
        country.setName("Country C");
        assertEquals("Country C",country.getName());

    }

    @Test
    void setContinentID() {
        country.setContinentID(2);
        assertEquals(2,country.getContinentID());
    }

    @Test
    void addNeighbor() {
        //create a new country to add as a neighbor.
        Country neighbor = new Country(2, "Country B", 1);
        country.addNeighbor(neighbor);
        assertEquals(1, country.getNeighbours().size());
        assertEquals(neighbor, country.getNeighbours().get(0));
    }

    @Test
    void removeNeighbor() {
        //create a new country to add as a neighbor.
        Country neighbor = new Country(2, "Country B", 1);
        country.removeNeighbor(neighbor);
        assertEquals(0, country.getNeighbours().size());
    }
}