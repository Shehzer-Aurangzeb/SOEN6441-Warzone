package views.MapView;

import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MapViewTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

//    @Test
//    void testDisplayMapInformation() {
//        // Mock the map object
//        Map mockedMap = mock(Map.class);
//        MapHolder.setMap(mockedMap);
//        // Create sample countries and continents
//        ArrayList<Country> countries = new ArrayList<>();
//        Country country1 = new Country(1, "s1", 1);
//        Country country2 = new Country(2, "s2", 1);
//        Country country3 = new Country(10, "b1", 2);
//
//        // Set up mock for country1's neighbors
//        country1.addNeighbor(country2);
//        country1.addNeighbor(country3);
//
//        // Add countries to the list
//        countries.add(country1);
//        ArrayList<Continent> continents = new ArrayList<>();
//        continents.add(new Continent("Scandinavia", 0));
//
//        // Mock the behavior of the map object
//        when(mockedMap.getCountries()).thenReturn(countries);
//        when(mockedMap.getContinents()).thenReturn(continents);
//
//        // Call the method to be tested
//        MapView.displayMapInformation();
//
//        String expectedOutput = "Map Information:\n" +
//                "_____________________________________________"+"\n"+
//                "| Country ID | Name| Continent    | Neighbours|"+"\n"+
//                "_____________________________________________"+"\n"+
//                "| 1          | s1  | Scandinavia  | s2, b1  |"+"\n"+
//                "_____________________________________________"+"\n";
//        assertEquals(expectedOutput, outputStreamCaptor.toString());
//    }
}
