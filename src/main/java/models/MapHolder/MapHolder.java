package models.MapHolder;

import models.Map.Map;

public class MapHolder {
    private static Map map;

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        MapHolder.map = map;
    }

}
