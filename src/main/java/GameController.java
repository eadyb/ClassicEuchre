/**
 * This class handles user input and interactions,
 * playing cards, and maintaining the flow of the game.
 * It communicates with the Model to update the
 * game state and triggers updates to the View.
 */
public class GameController {

    /**
     * Creates new game object to hold game state and logic.
     */
    private Game game;

    /**
     * Constructor for controller.
     */
    public GameController(final Game game) {
        this.game = game;
    }

    /**
     * Adds a card to the trick.
     */
    public void playCard(final Card card, final int playerNum) {
        game.getTrick().addCardToTrick(card, playerNum);
        game.assignCardPoints();
    }

    /**
     * Asks each player to decide on their Trump option.
     *
     * @param view to update view when a player makes a decision, or it is their turn
     */
    public void decideTrump(final GameView view) {
        updateView(view);
        game.setOrderedUp(false);
        game.setPickedUp(false);
        game.setGoneOnce(false);
        game.setPlayerDecidedTrump(-1);

        // first round: asking each player to pass, pick up, or order up the kitty card
        while (!game.isGoneOnce() && !game.isOrderedUp() && !game.isPickedUp()
                && game.getPlayerDecidedTrump() == -1) {

            // if it is user's turn
            if (game.getCurrentPlayerTurn() == 0) {
                view.askForPass();
            } else {
                // if it's AIs turn
                game.aiDecideTrump();
                view.displayTrumpMessage();
            }

            // check if all players have had a chance to decide
            if (game.getCurrentPlayerTurn() == game.getDealer()) {
                game.setGoneOnce(true);
            }

            // move to next player
            game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

        }

        // if no one ordered up in the first round, go for second round
        if (!game.isOrderedUp() && !game.isPickedUp()
                && game.getPlayerDecidedTrump() == -1 && game.isGoneOnce()) {
            // since no one decided trump, set to undecided
            game.setTrump("undecided");

            // while this is everyone's second turn and no decision has been made,
            // ask to choose a suit for trump
            while (game.isGoneOnce() && !game.isOrderedUp()
                    && !game.isPickedUp() && game.getPlayerDecidedTrump() == -1) {

                // if it is user's turn
                if (game.getCurrentPlayerTurn() == 0) {
                    view.askForTrump();
                } else {
                    // it's AIs turn
                    game.aiDecideTrump();
                    view.displayTrumpMessage();
                }

                // check if all players have had a chance to decide
                if (game.getCurrentPlayerTurn() == game.getDealer()) {
                    game.setGoneOnce(false);
                }

                // move to next player
                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

            }

        }

        updateView(view);

        // after both rounds, check if the trump has been decided or needs a re-deal
        if (game.getPlayerDecidedTrump() != -1) {
            // player after dealer starts the round
            game.setCurrentPlayerTurn((game.getDealer() + 1) % 4);

            // if the user ordered up or chose trump, display the message
            if (game.getPlayerDecidedTrump() == 0) {
                view.displayTrumpMessage();
            }

            // if the user is the dealer and is picked up or ordered up, ask for discard
            if ((game.isOrderedUp() || game.isPickedUp()) && game.getDealer() == 0) {
                view.askUserForDiscard();
                // else if an AI player is picked up or ordered up, make them discard
            } else if (game.isOrderedUp() || game.isPickedUp()) {
                game.aiDecideDiscard();
            }

            // assign card points with trump scores
            game.assignCardPoints();

        } else {
            // if no one orders up, picks up, or chooses trump, re-deal
            game.setDealer((game.getDealer() + 1) % 4);
            game.setCurrentPlayerTurn((game.getDealer() + 1) % 4);
            view.displayNoChoice();
            game.newHand();

            updateView(view);
            decideTrump(view);


        }

        // update the kitty image to hide card during gameplay
        view.setKittyCardImage();

    }

    /**
     * Main game loop. Loops through the game logic
     * to progress game moves until game is over.
     */
    public void playGame(final GameView view) {
        updateView(view);
        if (game.getTrick().getLeadCard() != null) {
            game.assignCardPoints();
        }
        // while the trick is not full, ask AI players to play cards
        while (!game.getTrick().checkTrickForWin()) {
            // if user's turn, ask to play card
            if (game.getCurrentPlayerTurn() == 0) {
                updateView(view);
                view.askUserPlayCard();
                return;

            } else {
                // else AI plays a card
                playCard(game.aiDecideCard(), game.getCurrentPlayerTurn());
                updateView(view);

                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

            }

        }

        // set the current player to the winner of the trick,
        // update view, and display the winner
        game.setCurrentPlayerTurn(game.awardTrickPoints());
        updateView(view);
        view.displayTrickWinner(game.getCurrentPlayerTurn());

        // create local variables instead of using game.getTricksWon() in logic
        final int[] tricksWon = game.getTricksWon();
        final int[] teamScore = game.getScores();
        // if the total of both teams trick score is 5,
        // awardTeamPoints, create newHand, and check for a winner
        if (tricksWon[0] + tricksWon[1] == 5) {
            view.displayHandWinner(game.awardTeamPoints());
            game.setPickedUp(false);
            game.setOrderedUp(false);
            game.setPlayerDecidedTrump(-1);
            game.getTrick().clearTrick();
            game.newHand();
            game.setTricksWon(new int[2]);

            view.cardBtnReset();
            game.setDealer((game.getDealer() + 1) % 4);
            game.setCurrentPlayerTurn((game.getDealer() + 1) % 4);

            updateView(view);
            // if neither team has won, ask for trump again
            if (teamScore[0] <= 10 && teamScore[1] <= 10) {
                view.displayDealer();
                decideTrump(view);
            }
        }

        // if one of the teams has a score of 10 or greater,
        // declare team winner and end game. Else, continue playing game.
        if (teamScore[0] >= 10) {
            view.displayWinner(0);
            endGame(view);
        } else if (teamScore[1] >= 10) {
            view.displayWinner(1);
            endGame(view);
        } else {
            game.getTrick().clearTrick();
            updateView(view);

            playGame(view);
        }

    }

    /**
     * Creates a new shuffled deck and initializes the game.
     *
     * @param view view to be initialized
     */
    public void startGame(final GameView view) {
        decideTrump(view);
        playGame(view);
    }


    /**
     * Declares end of game and creates a new game.
     *
     * @param view to close the current game window
     */
    public void endGame(final GameView view) {
        view.dispose();
        final Game gameModel = new Game();
        final GameController gameController = new GameController(gameModel);
        new GameView(gameModel, gameController);
    }

    /**
     * Updates the game view.
     *
     * @param view to communicate with the view update functions
     */
    public void updateView(final GameView view) {
        final Trick trick = game.getTrick();
        view.setKittyCardImage();
        view.updateTrickView(trick);
        view.assignCardIcons();
        view.updateScores();
        view.updateTricks();
        view.updateTrump();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(final Game game) {
        this.game = game;
    }

}
