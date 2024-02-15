package main.java.models.Order.Deploy;

import main.java.models.Country.Country;
import main.java.models.Order.Order;

/**
 * Represents a deployment order to place armies on a country.
 */
public class Deploy implements Order {
        private Country d_targetCountry;
        private int d_noOfArmies;

        /**
         * Initializes a deployment order with the target country and number of armies to deploy.
         *
         * @param new_country    The target country to deploy armies to.
         * @param new_noOfArmies The number of armies to deploy.
         */
        public Deploy(Country new_country,int new_noOfArmies){
                this.d_targetCountry = new_country;
                this.d_noOfArmies = new_noOfArmies;
        }
        /**
         * Executes the deployment order.
         */
        public void execute(){}

}
