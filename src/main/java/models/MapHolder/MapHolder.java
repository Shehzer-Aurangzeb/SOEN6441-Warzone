/**
 * The MapHolder class provides global access to the game map.
 * It allows setting and retrieving the map object from anywhere in the application.
 */
package models.MapHolder;

import models.Map.Map;

public class MapHolder {
    private static Map map;

    /**
     * Retrieves the current map object.
     *
     * @return The current map object.
     */
    public static Map getMap() {
        return map;
    }
    /**
     * Sets the map object to the specified value.
     *
     * @param map The map object to set.
     */
    public static void setMap(Map map) {
        MapHolder.map = map;
    }

}
