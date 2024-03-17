package models.Order.Deploy;

import static org.junit.jupiter.api.Assertions.*;
import models.Country.Country;
import models.Enums.OrderType;
import models.GameContext.GameContext;
import org.junit.jupiter.api.*;


public class DeployOrderTest {

    private DeployOrder deployOrder;
    private Country country;
    private GameContext gameContext;
    @BeforeEach
    public void setUp() {
        country = new Country(1,"new_country",1);
        gameContext= GameContext.getInstance();
        gameContext.getMap().addCountry(country);
        deployOrder = new DeployOrder(1, 5);

    }

    @Test
    public void getName_ReturnsCorrectOrderType() {
        assertEquals(OrderType.DEPLOY, deployOrder.getName());
    }

    @Test
    public void execute_DeploysArmiesToTargetCountry() {
        // Execute the DeployOrder
        deployOrder.execute();

        //check if armies are deployed
       assertEquals(5,gameContext.getMap().getCountryByID(1).getArmiesDeployed());
    }

    @Test
    public void testToString() {
        // Create a deployment order
        DeployOrder deployOrder = new DeployOrder(1, 5); // Player is not needed for this test

        // Verify the toString representation
        assertEquals("Deploying 5 armies to reinforce country 1.", deployOrder.toString());
    }

    @AfterEach
    public void tearDown(){
        gameContext=null;
        country=null;
        deployOrder=null;
    }
}
