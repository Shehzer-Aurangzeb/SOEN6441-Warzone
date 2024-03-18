package models.Enums;

/**
 * The {@code GamePhase} enum represents the different phases within the game, focusing on map editing processes and gameplay stages. Each phase is associated with a descriptive name to enhance clarity and debuggability.
 */
public enum GamePhase {
    MAP_EDITING("Map Editing"),
    MAP_EDITING_PRELOAD("Map Editing - Preload"),
    MAP_EDITING_POSTLOAD("Map Editing - Postload"),
    GAMEPLAY("Game Play"),
    STARTUP("Startup"),
    ISSUE_ORDERS("Issue Orders"),
    EXECUTE_ORDERS("Execute Orders");

    private final String name;

    /**
     * Constructs a GamePhase enum constant with a specified name.
     *
     * @param name The human-readable name of the game phase.
     */
    GamePhase(String name) {
        this.name = name;
    }

    /**
     * Retrieves the human-readable name of this game phase.
     *
     * @return The name of the game phase.
     */
    public String getName() {
        return this.name;
    }
}
