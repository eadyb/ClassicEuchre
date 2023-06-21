/**
 * Main class for Euchre game.
 * Includes all necessary calls to start the program.
 */
public class Main {

    /**
     * Main function that makes necessary calls to start the Euchre application.
     *
     * @param args String[] of arguments returned
     */
    public static void main(final String[] args) {
        final Game gameModel = new Game();
        final GameController gameController = new GameController(gameModel);
        new GameView(gameModel, gameController);

    }
}
