package controllers.MapEditor;

import models.Map.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class MapEditorTest {

    private Map map;
    private MapEditor mapEditor;
    @BeforeEach
    void setUp() {
        map = new Map();
        mapEditor = new MapEditor();

    }

    @Test
    void loadMap() {
    }
}