import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class tests the GameController.
 */
class GameControllerTest {

    /**
     * GameController class.
     */
    private GameController gameController;
    /**
     * Game class.
     */
    private Game game;
    /**
     * GameView class.
     */
    private GameView gameView;

    /**
     * Initialize the entire game for each test.
     */
    @BeforeEach
    public void setup() {
        game = new Game();
        gameController = new GameController(game);
        gameView = new GameView(game, gameController);
    }

    /**
     * Create a card, play it, and confirm it was added to the trick
     */
    @Test
    void playCard() {

        final Card card = new Card("Hearts", "Ten", 10);
        gameController.playCard(card, 0);

        final Card playedCard = game.getTrick().getCardsPlayed()[0];

        // assert that the card is present in trick
        assertEquals(card, playedCard, "Card is in the trick.");
    }

    /**
     * Make sure the AI makes the correct decision when deciding on trump
     */
    @Test
    void decideTrump() {
        // set up the test scenario for the first round where a player orders up
        game.setDealer(0); // Set the dealer to player 0
        game.setCurrentPlayerTurn(1); // Set the current player turn to player 1
        final String trump = game.getKittyCard().getSuit();

        // set up the AI player's hand with 3 trump cards
        final Hand hand = new Hand();
        hand.addCard(new Card(trump, "9", 14));
        hand.addCard(new Card(trump, "10", 15));
        hand.addCard(new Card(trump, "Jack", 20));
        hand.addCard(new Card("blank", "9", 2));
        hand.addCard(new Card("blank", "10", 3));
        game.getPlayers()[game.getCurrentPlayerTurn()].setHand(hand);

        // call the decideTrump() method
        gameController.decideTrump(gameView);

        // verify that the player ordered up trump
        assertTrue(game.isOrderedUp(), "Player ordered up trump.");

        // verify that the player who ordered up trump is set correctly
        assertEquals(1, game.getPlayerDecidedTrump(), "Player who ordered up trump is stored.");

        // verify player after dealer is current player after deciding
        assertEquals(1, game.getCurrentPlayerTurn(), "Current player turn correct.");
    }

    /**
     * Make sure the AI makes the correct decision when deciding on trump
     */
    @Test
    void decideTrump2() {
        // set up the test scenario where AI has 2 or more trump cards and is the dealer
        game.setDealer(2);
        game.setCurrentPlayerTurn(2);
        final String trump = game.getKittyCard().getSuit();

        // create a hand with more than 2 trump cards
        final Hand hand = new Hand();
        hand.addCard(new Card(trump, "9", 14));
        hand.addCard(new Card(trump, "10", 15));
        hand.addCard(new Card("blank", "Jack", 20));
        hand.addCard(new Card("blank", "10", 3));
        hand.addCard(new Card("blank", "Jack", 4));

        // set the hand for the current player
        game.getPlayers()[game.getCurrentPlayerTurn()].setHand(hand);

        // call the method to decide trump
        game.aiDecideTrump();

        // assert that the AI picked up the kitty card
        assertTrue(game.isPickedUp(), "AI picked up kitty card.");
    }

    /**
     * Test a specific scenario with cards and
     * assert the game logic updates accordingly.
     */
    @Test
    void playGame() {
        // set up the test scenario where trick is full
        game.getTrick().setCardsPlayed(new Card[] {
                new Card("Hearts", "Ace", 11),
                new Card("Spades", "King", 10),
                new Card("Diamonds", "Queen", 10),
                new Card("Clubs", "Jack", 10)
        });

        // assert trick contains a win
        assertTrue(game.getTrick().checkTrickForWin(), "Trick contains a win.");


        gameController.playGame(gameView);

        // assert that trick has been cleared
        assertFalse(game.getTrick().checkTrickForWin(), "Trick has been cleared.");

        // assert the correct player is set to start round
        assertEquals(0, game.getCurrentPlayerTurn(), "Correct starting player.");
    }

    /**
     * Test the end game function that is called when the end game button is pressed.
     */
    @Test
    void endGame() {

        // assert there is a game
        assertNotNull(gameController.getGame(), "A game exists.");

        // end the current game and create a new one
        gameController.endGame(gameView);

        // assert there still exists a game
        assertNotNull(gameController.getGame(), "A new game window exists.");
    }

    /**
     * Verifies that when the updateView method is called, the appropriate
     * methods of GameView are invoked.
     */
    @Test
    void updateView() {
        // create mocks
        final Game mockGame = mock(Game.class);
        final Trick mockTrick = mock(Trick.class);
        when(mockGame.getTrick()).thenReturn(mockTrick);

        final GameView mockView = mock(GameView.class);

        final GameController controller = new GameController(mockGame);

        // call the update view method
        controller.updateView(mockView);

        // assert that all methods were invoked
        verify(mockView).setKittyCardImage();
        verify(mockView).updateTrickView(mockTrick);
        verify(mockView).assignCardIcons();
        verify(mockView).updateScores();
        verify(mockView).updateTricks();
        verify(mockView).updateTrump();
    }
}
