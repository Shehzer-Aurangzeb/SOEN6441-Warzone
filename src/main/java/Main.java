import controllers.GameEngine.GameEngine;

/**
 * The {@code Main} class serves as the entry point for the game application.
 * It initializes the game engine and starts the game.
 */
public class Main {

    /**
     * The main method that is executed when the application starts. It creates an instance of the {@code GameEngine}
     * and calls the {@code startGame} method to begin the game.
     */
    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        gameEngine.startGame();

    }
}

//
// loadmap test_map
// proceed
// gameplayer -add Tania -add Nabil
// startgame
 // endturn
// deploy 528 3
//advance Pakistan India 3