
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import adapters.FileAdapter.FileAdapterTest;
import controllers.MapEditor.MapEditorTest;
import models.Country.CountryTest;
import models.Map.MapTest;
import models.Map.MapValidatorTest;
import models.Command.CommandTest;
import models.Continent.ContinentTest;
import utils.FeedbackTest;
import views.MapView.MapViewTest;
import models.Player.PlayerTest;
/**
 * Test Suite for Warzone Game
 */
@RunWith(JUnitPlatform.class)
@SelectClasses({
        FileAdapterTest.class,
        MapEditorTest.class,
        CountryTest.class,
        MapTest.class,
        MapValidatorTest.class,
        PlayerTest.class,
        CommandTest.class,
        ContinentTest.class,
        FeedbackTest.class,
        MapViewTest.class
})

public class TestSuite {
}
