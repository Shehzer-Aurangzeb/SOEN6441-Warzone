package utils;

import constants.Commands;
import models.Enums.GamePhase;

import java.util.ArrayList;

public class Command {
    /**
     * Get the command object by name for the current game phase.
     *
     * @param p_commandName The name of the command to search for .
     * @return true if command exist in the current phase.
     */
    public static boolean isCommandValidForPhase(String p_commandName, GamePhase p_currentPhase) {
        ArrayList<models.Command.Command> phaseCommands = Commands.PHASE_COMMANDS_MAP.get(p_currentPhase);
        for (models.Command.Command command : phaseCommands) {
            String l_commandName= command.getName().split(" ")[0];
            if (l_commandName.equals(p_commandName)) {
                return true;
            }
        }
        return false;
    }
}
