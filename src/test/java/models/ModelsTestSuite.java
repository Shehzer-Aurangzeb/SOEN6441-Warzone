package models;
import models.Continent.ContinentTest;
import models.Country.CountryTest;
import models.Player.PlayerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({PlayerTest.class, CountryTest.class, ContinentTest.class})
public class ModelsTestSuite {
}
