package controllers.MapEditor;

import models.Continent.Continent;
import models.Country.Country;
import models.Enums.LineType;
import models.Map.Map;
import models.MapHolder.MapHolder;

import java.io.*;

public class MapEditor {
    private BufferedReader READER;
    private Map MAP;

    /**
     * Constructor for MapEditor class.
     */
    public MapEditor() {
        MAP= MapHolder.getMap();
    }

    /**
     * Loads a map file and processes its contents.
     *
     * @param p_file The file object representing the map file to be loaded.
     * @throws FileNotFoundException If the specified file is not found.
     * @throws IOException           If an I/O error occurs while reading the file.
     */
    public void loadMap(File p_file) throws FileNotFoundException, IOException {
        READER = new BufferedReader(new FileReader(p_file));
        String l_line = READER.readLine();
        boolean l_startReading = false;
        LineType l_lineType = LineType.CONTINENT; //initializing to remove error.
        while (l_line != null) {
            if (l_line.startsWith("[continents]")) {
                l_startReading = true;
                l_lineType = LineType.CONTINENT;

            } else if (l_line.startsWith("[countries]")) {
                l_startReading = true;
                l_lineType = LineType.COUNTRY;

            } else if (l_line.startsWith("[borders]")) {
                l_startReading = true;
                l_lineType = LineType.NEIGHBOR;

            } else if (l_startReading) {
                processMapLine(l_line, l_lineType);
            }
            l_line = READER.readLine();
        }

    }

    private void processMapLine(String p_line, LineType p_lineType) {
        // skips empty line or comments. comment starts with ;
        if (p_line.isBlank() || p_line.startsWith(";")) return;
        String[] l_parts = p_line.split(" ");
        switch (p_lineType) {
            case CONTINENT:
                processContinentLine(l_parts);
                break;
            case COUNTRY:
                processCountryLine(l_parts);
                break;
            case NEIGHBOR:
                processNeighborLine(l_parts);
                break;

        }

    }

    private void processContinentLine(String[] p_parts) {
        String l_continentName = p_parts[0];
        int l_armyBonus = Integer.parseInt(p_parts[1]);
        Continent l_continent = new Continent(l_continentName, l_armyBonus);
        MAP.addContinent(l_continent);
    }

    private void processCountryLine(String[] p_parts) {
        int l_countryId = Integer.parseInt(p_parts[0]);
        String l_countryName = p_parts[1];
        int l_continentId = Integer.parseInt(p_parts[2]);
        Country l_country = new Country(l_countryId, l_countryName, l_continentId);
        MAP.addCountry(l_country);
    }

    private void processNeighborLine(String[] p_parts) {
        int l_countryId = Integer.parseInt(p_parts[0]);
        Country l_country = MAP.getCountryByID(l_countryId);
        if (l_country == null) return;
        for (int i = 1; i < p_parts.length; i++) {
            Country l_neighbor = MAP.getCountryByID(Integer.parseInt(p_parts[i]));
            if (l_neighbor != null) {
                l_country.addNeighbor(l_neighbor);
            }
        }
    }
}
