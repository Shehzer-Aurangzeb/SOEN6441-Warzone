package controllers.MapEditor;

import models.Continent.Continent;
import models.Country.Country;
import models.Enums.LineType;
import models.GameContext.GameContext;
import models.Map.Map;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MapEditor {
    private final String CONTINENTS = "[continents]";
    private final String COUNTRIES = "[countries]";
    private final String BORDERS = "[borders]";
    private Map d_map;
    private final HashMap<String, Map> d_mapRegistry = new HashMap<>();
    private String d_currentEditingFilename;

    public HashMap<String, Map> getMapRegistry() {
        return this.d_mapRegistry;
    }

    private final GameContext d_ctx;

    public MapEditor(GameContext ctx) {
        this.d_ctx = ctx;
    }


    /**
     * Retrieves the file currently being edited.
     *
     * @return The file currently being edited.
     */
    public String getCurrentEditingFilename() {
        return this.d_currentEditingFilename;
    }

    public void setCurrentEditingFilename(String p_filename) {
        this.d_currentEditingFilename = p_filename;
    }

    public void setMapInRegistry(String p_filename, Map p_map) {
        this.d_mapRegistry.put(p_filename, p_map);
    }

    /**
     * Loads a map file and processes its contents.
     *
     * @param p_file The file object representing the map file to be loaded.
     * @throws FileNotFoundException If the specified file is not found.
     * @throws IOException           If an I/O error occurs while reading the file.
     */
    public void loadMap(File p_file) throws FileNotFoundException, IOException {
        d_map=new Map();
        List<Continent> l_mapContinents= d_map.getContinents();
        BufferedReader READER = new BufferedReader(new FileReader(p_file));
        String l_line = READER.readLine();
        boolean l_startReading = false;
        LineType l_lineType = LineType.CONTINENT; //initializing to remove error.
        while (l_line != null) {
            if (l_line.startsWith(CONTINENTS)) {
                l_startReading = true;
                l_lineType = LineType.CONTINENT;

            } else if (l_line.startsWith(COUNTRIES)) {
                l_startReading = true;
                l_lineType = LineType.COUNTRY;

            } else if (l_line.startsWith(BORDERS)) {
                l_startReading = true;
                l_lineType = LineType.NEIGHBOR;

            } else if (l_startReading) {
                processMapLine(l_line, l_lineType,l_mapContinents);
            }
            l_line = READER.readLine();
        }
        //when done loading set the global state
        d_ctx.setMap(d_map);
        d_map=null;

    }

    public void addContinent(String p_continentName, String p_continentValue) {
        if (d_currentEditingFilename == null || d_currentEditingFilename.isEmpty()) {
            System.out.println("\nCannot add continent. Please use the 'editmap' command to select the file for editing.");
            return;
        }
        Map l_mapToEdit = d_mapRegistry.get(d_currentEditingFilename);
        Continent l_continent = l_mapToEdit.getContinentByName(p_continentName);
        if (l_continent == null) {
            l_mapToEdit.addContinent(new Continent(p_continentName, Integer.parseInt(p_continentValue)));
            System.out.println("\nContinent '" + p_continentName + "' with value '" + p_continentValue + "' has been successfully added.");

        } else {
            System.out.println("\nError: Continent '" + p_continentName + "' already exists. " +
                    "Please remove the existing continent before adding a new one.");
        }

    }

    public void removeContinent(String p_continentName) {
        if (d_currentEditingFilename == null || d_currentEditingFilename.isEmpty()) {
            System.out.println("\nCannot remove continent. Please use the 'editmap' command to select the file for editing.");
            return;
        }
        Map l_mapToEdit = d_mapRegistry.get(d_currentEditingFilename);
        Continent l_continent = l_mapToEdit.getContinentByName(p_continentName);
        if (l_continent == null) {
            System.out.println("\nError: Continent '" + p_continentName + "' does not exist.");
        } else {
            l_mapToEdit.removeContinent(l_continent);
            //delete all countries of this continent
            Iterator<Country> l_iterator = l_mapToEdit.getCountries().iterator();
            while (l_iterator.hasNext()) {
                Country l_country = l_iterator.next();
                if (l_country.getContinentID() == l_continent.getID()) {
                    l_iterator.remove();
                }
            }
            restructureMapAfterContinentDeletion(l_mapToEdit);
            System.out.println("\nContinent '" + p_continentName + "' has been successfully removed.");
        }
    }

    public void addCountry(String p_countryName, String p_continentName) {
        if (d_currentEditingFilename == null || d_currentEditingFilename.isEmpty()) {
            System.out.println("\nCannot add country. Please use the 'editmap' command to select the file for editing.");
            return;
        }
        Map l_mapToEdit = d_mapRegistry.get(d_currentEditingFilename);
        Continent l_continent = l_mapToEdit.getContinentByName(p_continentName);
        Country l_country = l_mapToEdit.getCountryByName(p_countryName);
        if (l_continent == null) {
            System.out.println("\nError: Continent '" + p_continentName + "' does not exists.");
        } else if (l_country == null) {
            l_mapToEdit.addCountry(new Country(p_countryName, l_continent.getID()));
            System.out.println("\nCountry '" + p_countryName + "' added to continent '" + p_continentName + "'.");
        } else {
            System.out.println("\nError: Country '" + p_countryName + "' already exists." +
                    " Please remove the existing country before adding a new one.");
        }

    }

    public void removeCountry(String p_countryName) {
        if (d_currentEditingFilename == null || d_currentEditingFilename.isEmpty()) {
            System.out.println("\nCannot remove country. Please use the 'editmap' command to select the file for editing.");
            return;
        }
        Map l_mapToEdit = d_mapRegistry.get(d_currentEditingFilename);
        Country l_country = l_mapToEdit.getCountryByName(p_countryName);
        if (l_country == null) {
            System.out.println("\nError: Country '" + p_countryName + "' does not exists.");
        } else {
            l_mapToEdit.removeCountry(l_country);
            System.out.println("\nCountry '" + p_countryName + "' removed successfully.");
        }
    }

    public void addNeighbor(String p_countryName, String p_neighborCountryName) {
        if (d_currentEditingFilename == null || d_currentEditingFilename.isEmpty()) {
            System.out.println("\nCannot add neighbor. Please use the 'editmap' command to select the file for editing.");
            return;
        }
        Map l_mapToEdit = d_mapRegistry.get(d_currentEditingFilename);
        Country l_country = l_mapToEdit.getCountryByName(p_countryName);
        Country l_neighborCountry = l_mapToEdit.getCountryByName(p_neighborCountryName);
        if (l_country == null) {
            System.out.println("\nError: Country '" + p_countryName + "' does not exists.");
        } else if (l_neighborCountry == null) {
            System.out.println("\nError: Neighbor Country '" + p_neighborCountryName + "' does not exists.");
        } else {
            l_country.addNeighbor(l_neighborCountry);
            System.out.println("\nThe country '" + p_neighborCountryName + "' has been " +
                    "successfully added to the list of neighbors for the country '" + p_countryName + "'.");
        }
    }

    public void removeNeighbor(String p_countryName, String p_neighborCountryName) {
        if (d_currentEditingFilename == null || d_currentEditingFilename.isEmpty()) {
            System.out.println("\nCannot remove neighbor. Please use the 'editmap' command to select the file for editing.");
            return;
        }
        Map l_mapToEdit = d_mapRegistry.get(d_currentEditingFilename);
        Country l_country = l_mapToEdit.getCountryByName(p_countryName);
        Country l_neighborCountry = l_mapToEdit.getCountryByName(p_neighborCountryName);
        if (l_country == null) {
            System.out.println("\nError: Country '" + p_countryName + "' does not exists.");
        } else if (l_neighborCountry == null) {
            System.out.println("\nError: Neighbor Country '" + p_neighborCountryName + "' does not exists.");
        } else {
            l_country.removeNeighbor(l_neighborCountry);
            System.out.println("\nThe country '" + p_neighborCountryName + "' has been " +
                    "successfully removed from the list of neighbors for the country '" + p_countryName + "'.");
        }

    }

    public void saveMap(File p_file, Map p_map) {
        emptyFile(p_file);
        try (PrintWriter l_pw = new PrintWriter(new FileOutputStream(p_file))) {
            l_pw.println("; map: " + p_file.getName() + ".map");
            l_pw.println();
            writeContinents(l_pw, p_map.getContinents());
            writeCountriesAndBorders(l_pw, p_map.getCountries());
            System.out.println("\nMap successfully saved to the file: " + p_file.getName());

        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found. " + e.getMessage());
        }
    }

    private void emptyFile(File p_file) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(p_file))) {
            //writing nothing to files make it empty.
        } catch (FileNotFoundException e) {
            System.out.println("\nFile not found. " + e.getMessage());
        }
    }

    private void writeContinents(PrintWriter p_pw, ArrayList<Continent> p_continents) {
        p_pw.println(CONTINENTS);
        for (Continent l_continent : p_continents) {
            p_pw.println(l_continent.getName() + " " + l_continent.getArmyBonus());
        }
        //new empty line
        p_pw.println();
    }

    private void writeCountriesAndBorders(PrintWriter p_pw, ArrayList<Country> p_countries) {
        StringBuilder l_line = new StringBuilder();
        p_pw.println(COUNTRIES);
        for (Country l_country : p_countries) {
            l_line.append(l_country.getID()).append(" ").append(l_country.getName()).append(" ").append(l_country.getContinentID());
            p_pw.println(l_line.toString());
            //reset line content
            l_line.setLength(0);
        }
        //new empty line
        p_pw.println();
        p_pw.println(BORDERS);
        for (Country l_country : p_countries) {
            //reset line
            l_line.setLength(0);
            l_line.append(l_country.getID());
            for (Country l_neighbor : l_country.getNeighbours()) {
                l_line.append(" ").append(l_neighbor.getID());
            }
            p_pw.println(l_line.toString());

        }
    }

    private void restructureMapAfterContinentDeletion(Map p_map) {
        ArrayList<Continent> l_continents = p_map.getContinents();
        ArrayList<Country> l_countries = p_map.getCountries();
        int newID = 1;
        for (Continent l_continent : l_continents) {
//            System.out.println("\nContinent " + l_continent.getName() + " " + l_continent.getID());
            int l_oldID = l_continent.getID();
            l_continent.setID(newID);
//            System.out.println("\nContinent " + l_continent.getName() + " " + l_continent.getID());
            for (Country l_country : l_countries) {
                if (l_country.getContinentID() == l_oldID) {
                    l_country.setContinentID(newID);
//                    System.out.println("\nCountry " + l_country.getName() + " " + l_country.getContinentID());
                }
            }
            newID++;
        }
        Continent.lastAssignedID = newID;
    }

    private void processMapLine(String p_line, LineType p_lineType,List<Continent> p_mapContinents) {
        // skips empty line or comments. comment starts with ;
        if (p_line.isBlank() || p_line.startsWith(";")) return;
        String[] l_parts = p_line.split(" ");
        switch (p_lineType) {
            case CONTINENT:
                processContinentLine(l_parts);
                break;
            case COUNTRY:
                processCountryLine(l_parts,p_mapContinents);
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
        d_map.addContinent(l_continent);
    }

    private void processCountryLine(String[] p_parts,List<Continent> p_mapContinents) {
        int l_countryId = Integer.parseInt(p_parts[0]);
        String l_countryName = p_parts[1];
        int l_continentIndex=Integer.parseInt(p_parts[2])-1;
        int l_continentId = p_mapContinents.get(l_continentIndex).getID();
        Country l_country = new Country(l_countryId, l_countryName, l_continentId);
        d_map.addCountry(l_country);
    }

    private void processNeighborLine(String[] p_parts) {
        int l_countryId = Integer.parseInt(p_parts[0]);
        Country l_country = d_map.getCountryByID(l_countryId);
        if (l_country == null) return;
        for (int i = 1; i < p_parts.length; i++) {
            Country l_neighbor = d_map.getCountryByID(Integer.parseInt(p_parts[i]));
            if (l_neighbor != null) {
                l_country.addNeighbor(l_neighbor);
            }
        }
    }


}
