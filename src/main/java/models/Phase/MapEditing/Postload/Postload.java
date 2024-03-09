package models.Phase.MapEditing.Postload;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.MapValidator;
import models.MapHolder.MapHolder;
import models.Phase.GamePlay.Startup.Startup;
import models.Phase.MapEditing.MapEditing;

import java.io.File;

import static adapters.FileAdapter.FileAdapter.createFile;
import static adapters.FileAdapter.FileAdapter.isFileExists;
import static utils.Feedback.printWelcomeMessageWithBanner;
import static views.MapView.MapView.displayMapInformation;

public class Postload extends MapEditing {

    private static final GamePhase PHASE_NAME= GamePhase.MAP_EDITING_POSTLOAD;
    public Postload(GameEngine new_ge){
        super(new_ge,PHASE_NAME);
    }

    public GamePhase getPhaseName(){
        return PHASE_NAME;
    }
    public void modifyMapComponents(String p_command, MapEditor p_mapEditor) {
        String[] l_commandParts = p_command.split("\\s+");
        boolean l_validOption = true; // Flag to track if the option is valid
        //i=1 because 0th index contains name
        int i = 1;
        while (i < l_commandParts.length && l_validOption) {
            String l_option = l_commandParts[i];
            String l_entityID;
            String l_entityValue;
            switch (l_option) {
                case "-add":
                    l_entityID = l_commandParts[i + 1];
                    l_entityValue = l_commandParts[i + 2];
                    if (l_commandParts[0].equals("editcontinent")) {
                        p_mapEditor.addContinent(l_entityID, l_entityValue);
                    } else if (l_commandParts[0].equals("editcountry")) {
                        p_mapEditor.addCountry(l_entityID, l_entityValue);
                    } else if (l_commandParts[0].equals("editneighbor")) {
                        p_mapEditor.addNeighbor(l_entityID, l_entityValue);
                    }
                    i += 3;
                    break;
                case "-remove":
                    l_entityID = l_commandParts[i + 1];
                    if (l_commandParts[0].equals("editcontinent")) {
                        p_mapEditor.removeContinent(l_entityID);
                        i += 2;
                    } else if (l_commandParts[0].equals("editcountry")) {
                        p_mapEditor.removeCountry(l_entityID);
                        i += 2;
                    } else if (l_commandParts[0].equals("editneighbor")) {
                        l_entityValue = l_commandParts[i + 2];
                        p_mapEditor.removeNeighbor(l_entityID, l_entityValue);
                        i += 3;
                    }

                    break;
                default:
                    System.out.println("\nInvalid option: " + l_option);
                    l_validOption = false; // Set the flag to false if option is invalid
                    break;
            }
        }
    }

    public void saveMap(String p_filename, MapEditor p_mapEditor) {
        if (!p_mapEditor.getMapRegistry().containsKey(p_filename)) {
            System.out.println("\nPlease specify the same filename used for editing when saving the map.");
            return;
        }
        File l_file = isFileExists(p_filename);
        if (l_file == null) {
            l_file = createFile(p_filename);
            p_mapEditor.saveMap(l_file, p_mapEditor.getMapRegistry().get(p_filename));
        } else {
            p_mapEditor.saveMap(l_file, p_mapEditor.getMapRegistry().get(p_filename));
        }
    }

    public void next() {
        if (!MapValidator.validateMap(MapHolder.getMap())) {
            System.out.println("The map is not valid. Please load a valid map.\n");
        }
        else {
            d_ge.setPhase(new Startup(d_ge));
            printWelcomeMessageWithBanner("You have entered Game Play - Startup phase.");
            System.out.println("Use 'showcommands' to see the commands you can run.");
        }
    }
    public void showMap() {
        displayMapInformation();
    }
}
