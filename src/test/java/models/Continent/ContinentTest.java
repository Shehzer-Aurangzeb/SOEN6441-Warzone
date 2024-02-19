package models.Continent;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContinentTest {

    private static Continent continent;
    @BeforeAll
    static void setUp() {
        continent = new Continent("Asia", 5);
    }



    @Test
    void getName() {
        assertEquals("Asia", continent.getName());
    }

    @Test
    void getArmyBonus() {
        assertEquals(5, continent.getArmyBonus());
    }

    @Test
    void setID() {
        continent.setID(2);
        assertEquals(2, continent.getID());
    }

    @Test
    void setName() {
        continent.setName("Europe");
        assertEquals("Europe", continent.getName());
    }

    @Test
    void setArmyBonus() {
        continent.setArmyBonus(10);
        assertEquals(10, continent.getArmyBonus());
    }
}