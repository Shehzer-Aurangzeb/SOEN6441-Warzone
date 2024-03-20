package models.Phase.MapEditing.Postload;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.Map.MapValidator;
import models.Phase.GamePlay.Startup.Startup;
import models.Phase.MapEditing.MapEditing;

import java.io.File;

import static adapters.FileAdapter.FileAdapter.*;
import static utils.Feedback.printWelcomeMessageWithBanner;

/**
 * The {@code Postload} class extends {@code MapEditing} to handle post-loading operations on a map.
 * This includes adding or removing continents, countries, and neighbors, as well as saving the modified map.
 * This phase is critical for ensuring the map is correctly modified and validated before transitioning to the game startup phase.
 */
public class Postload extends MapEditing {

    private static final GamePhase PHASE_NAME = GamePhase.MAP_EDITING_POSTLOAD;

    public Postload(GameEngine new_ge) {
        super(new_ge, PHASE_NAME);
    }

    public GamePhase getPhaseName() {
        return PHASE_NAME;
    }

    /**
     * Modifies map components based on specified commands, which may include adding or removing continents, countries, and their neighbors.
     *
     * @param p_command The command specifying what modifications to make to the map.
     */
    public void modifyMapComponents(String p_command) {
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
                        d_ctx.getMapService().addContinent(l_entityID, l_entityValue);
                    } else if (l_commandParts[0].equals("editcountry")) {
                        d_ctx.getMapService().addCountry(l_entityID, l_entityValue);
                    } else if (l_commandParts[0].equals("editneighbor")) {
                        d_ctx.getMapService().addNeighbor(l_entityID, l_entityValue);
                    }
                    i += 3;
                    break;
                case "-remove":
                    l_entityID = l_commandParts[i + 1];
                    if (l_commandParts[0].equals("editcontinent")) {
                        d_ctx.getMapService().removeContinent(l_entityID);
                        i += 2;
                    } else if (l_commandParts[0].equals("editcountry")) {
                        d_ctx.getMapService().removeCountry(l_entityID);
                        i += 2;
                    } else if (l_commandParts[0].equals("editneighbor")) {
                        l_entityValue = l_commandParts[i + 2];
                        d_ctx.getMapService().removeNeighbor(l_entityID, l_entityValue);
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

    /**
     * Saves the currently edited map to a file. If the file does not exist, it is created;
     * otherwise, the existing file is overwritten.
     *
     * @param p_filename The name of the file to save the map to.
     */
    public void saveMap(String p_filename) {
        if (!d_ctx.getMapService().getMapRegistry().containsKey(p_filename)) {
            System.out.println("\nPlease specify the same filename used for editing when saving the map.");
            return;
        }
        File l_file = isFileExists(p_filename);
        if (l_file == null) {
            l_file = createFile(p_filename);
            d_ctx.getMapService().saveMap(l_file, d_ctx.getMapService().getMapRegistry().get(p_filename));
        } else {
            d_ctx.getMapService().saveMap(l_file, d_ctx.getMapService().getMapRegistry().get(p_filename));
        }
    }
    public void validateMap(){
        if(!isMapLoaded) System.out.println("\nPlease load the map first using the 'loadmap' command.");
        else if (!MapValidator.validateMap(d_ctx.getMap())) {
            System.out.println("The map is not valid. Please load a valid map.\n");
        }
        else {
            System.out.println("The map is valid. You can proceed to the next phase.");
        }
    }

    /**
     * Transitions from the map editing phase to the game startup phase if the map is valid.
     * If the map is not valid, prompts the user to load a valid map.
     */
    public void next() {
        if (!MapValidator.validateMap(d_ctx.getMap())) {
            System.out.println("The map is not valid. Please load a valid map.\n");
        } else {
            d_ctx.setPhase(new Startup(d_ge));
            printWelcomeMessageWithBanner("You have entered Game Play - Startup phase.");
            System.out.println("Use 'showcommands' to see the commands you can run.");
        }
    }
}
