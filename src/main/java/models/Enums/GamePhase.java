package models.Enums;

public enum GamePhase {
    MAP_EDITING,
    MAP_EDITING_PRELOAD,
    MAP_EDITING_POSTLOAD,
    GAMEPLAY,
    STARTUP,
    ISSUE_ORDERS,
    EXECUTE_ORDERS;

    // Method to get the string name of the game phase
    public String getName() {
        switch (this) {
            case MAP_EDITING:
                return "Map Editing";
            case MAP_EDITING_PRELOAD:
                return "Map Editing - Preload";
            case MAP_EDITING_POSTLOAD:
                return "Map Editing - Postload";
            case STARTUP:
                return "Startup";
            case GAMEPLAY:
                return "Game Play";
            case ISSUE_ORDERS:
                return "Issue Orders";
            case EXECUTE_ORDERS:
                return "Execute Orders";
            default:
                return "Unknown";
        }
    }
}
