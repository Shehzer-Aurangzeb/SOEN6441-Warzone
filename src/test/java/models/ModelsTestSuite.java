package models;

import constants.Commands;
import models.Continent.ContinentTest;
import models.Country.CountryTest;
import models.GameContext.GameContext;
import models.Map.MapTest;
import models.Map.MapValidatorTest;
import models.Order.Advance.AdvanceOrderTest;
import models.Order.Blockade.BlockadeOrderTest;
import models.Order.Bomb.BombOrderTest;
import models.Order.Deploy.DeployOrderTest;
import models.Order.Diplomacy.DiplomacyOrder;
import models.Order.Diplomacy.DiplomacyOrderTest;
import models.Phase.GamePlay.ExecuteOrder.ExecuteOrder;
import models.Phase.GamePlay.ExecuteOrder.ExecuteOrderTest;
import models.Phase.GamePlay.GamePlayTest;
import models.Phase.GamePlay.IssueOrder.IssueOrderTest;
import models.Phase.MapEditing.Postload.PostloadTest;
import models.Phase.MapEditing.Preload.PreloadTest;
import models.Player.PlayerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({PlayerTest.class, CountryTest.class, ContinentTest.class, Commands.class,
        GameContext.class, MapTest.class
        ,MapValidatorTest.class, BlockadeOrderTest.class, DeployOrderTest.class,
        BombOrderTest.class, DiplomacyOrderTest.class, ExecuteOrderTest.class, IssueOrderTest.class,
        GamePlayTest.class, PostloadTest.class, PreloadTest.class
})
public class ModelsTestSuite {
}
