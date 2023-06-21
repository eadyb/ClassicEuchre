import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;


/**
 * GameView is the GUI (view) class that holds all the UI elements and logic.
 */
public class GameView extends JFrame {
    private static final long serialVersionUID = 1L;
    /**
     * Main JPanel to hold all UI elements.
     */
    private JPanel mainPanel;
    /**
     * Label to display even team's tricks.
     */
    private JLabel lblTeam1Score;
    /**
     * Label to display odd teams tricks.
     */
    private JLabel lblTeam2Score;
    /**
     * Start game button.
     */
    private JButton btnStartGame;
    /**
     * End game button.
     */
    private JButton btnEndGame;
    /**
     * Card button 1.
     */
    private JButton btnC1;
    /**
     * Card button 2.
     */
    private JButton btnC2;
    /**
     * Card button 3.
     */
    private JButton btnC3;
    /**
     * Card button 4.
     */
    private JButton btnC4;
    /**
     * Card button 5.
     */
    private JButton btnC5;
    /**
     * Trick label 0 for card image.
     */
    private JLabel lblTrick0;
    /**
     * Trick label 1 for card image.
     */
    private JLabel lblTrick1;
    /**
     * Trick label 2 for card image.
     */
    private JLabel lblTrick2;
    /**
     * Trick label 3 for card image.
     */
    private JLabel lblTrick3;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP1C1;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP1C2;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP1C3;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP1C4;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP1C5;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP2C1;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP2C2;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP2C3;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP2C4;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP2C5;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP3C1;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP3C2;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP3C3;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP3C4;
    /**
     * Label placeholder for AI hand card image.
     */
    private JLabel lblP3C5;
    /**
     * Label for displaying team 0's tricks.
     */
    private JLabel lblTeam0Tricks;
    /**
     * Label for displaying team 1's tricks.
     */
    private JLabel lblTeam1Tricks;
    /**
     * Label for displaying the kitty card image.
     */
    private JLabel lblKittyCard;
    /**
     * Label for displaying the current trump suit.
     */
    private JLabel lblTrump;
    /**
     * Image for the red back of a card.
     */
    private final ImageIcon backIcon1;
    /**
     * Image for the black back of a card.
     */
    private final ImageIcon backIcon2;
    /**
     * Game model.
     */
    private Game game;
    /**
     * Game controller.
     */
    private GameController gameController;
    /**
     * GameView to be passed to controller.
     */
    private GameView gameView;
    /**
     * To hold the user's cards and eliminate use of long method chain calls.
     */
    private List<Card> playerHandCards;
    /**
     * To store the relative path of the card image folder.
     */
    private final String imagePath;

    /**
     * Constructor for GameView UI class.
     *
     * @param game to receive data from the game
     * @param gameController to communicate with the controller
     */
    public GameView(final Game game, final GameController gameController) {
        super();
        this.game = game;
        this.gameController = gameController;
        this.gameView = this;
        this.imagePath = System.getProperty("user.dir") + "/src/main/java/CardImages/";

        this.backIcon1 = resizeImageIcon(new ImageIcon(imagePath + "2B.png"), 50, 65);
        this.backIcon2 = resizeImageIcon(new ImageIcon(imagePath + "1B.png"), 70, 90);

        setContentPane(mainPanel);
        setTitle("Euchre");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLocationRelativeTo(null);




        setVisible(true);

        btnStartGame.addActionListener(new ActionListener() {
            /**
             * Starts a new game when pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                assignCardIcons();
                cardBtnReset();
                JOptionPane.showMessageDialog(null, "The deal goes to Player " + game.getDealer(),
                        "The game is starting!", JOptionPane.INFORMATION_MESSAGE);
                btnEndGame.setEnabled(true);
                btnStartGame.setEnabled(false);
                gameController.startGame(gameView);
            }
        });

        btnEndGame.addActionListener(new ActionListener() {
            /**
             * Ends the current game.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(null, "The game has ended.",
                        "Message", JOptionPane.INFORMATION_MESSAGE);
                btnEndGame.setEnabled(false);
                btnStartGame.setEnabled(true);
                gameController.endGame(gameView);
            }
        });


        btnC1.addActionListener(new ActionListener() {
            /**
             * Plays the card assigned to the button when pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnC1.setIcon(backIcon1);
                btnC1.setEnabled(false);
                gameController.playCard(playerHandCards.get(0), 0);


                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

                gameController.updateView(gameView);

                gameController.playGame(gameView);
            }
        });

        btnC2.addActionListener(new ActionListener() {
            /**
             * Plays the card assigned to the button when pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnC2.setIcon(backIcon1);
                btnC2.setEnabled(false);
                gameController.playCard(playerHandCards.get(1), 0);

                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

                gameController.updateView(gameView);

                gameController.playGame(gameView);
            }
        });

        btnC3.addActionListener(new ActionListener() {
            /**
             * Plays the card assigned to the button when pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnC3.setIcon(backIcon1);
                btnC3.setEnabled(false);
                gameController.playCard(playerHandCards.get(2), 0);

                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

                gameController.updateView(gameView);

                gameController.playGame(gameView);
            }
        });

        btnC4.addActionListener(new ActionListener() {
            /**
             * Plays the card assigned to the button when pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnC4.setIcon(backIcon1);
                btnC4.setEnabled(false);
                gameController.playCard(playerHandCards.get(3), 0);

                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

                gameController.updateView(gameView);

                gameController.playGame(gameView);
            }
        });


        btnC5.addActionListener(new ActionListener() {
            /**
             * Plays the card assigned to the button when pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(final ActionEvent e) {
                btnC5.setIcon(backIcon1);
                btnC5.setEnabled(false);
                gameController.playCard(playerHandCards.get(4), 0);

                game.setCurrentPlayerTurn((game.getCurrentPlayerTurn() + 1) % 4);

                gameController.updateView(gameView);

                gameController.playGame(gameView);
            }
        });

        // initialize the game window with UI elements so that starting
        // a game doesn't force you to resize the window
        cardBtnInitialize();
        gameController.updateView(gameView);
    }

    /**
     * Assigns card icons for all players.
     */
    public void assignCardIcons() {
        playerHandCards = game.getPlayerHand().getCards();

        final JButton[] buttons = {btnC1, btnC2, btnC3, btnC4, btnC5};
        final JLabel[][] cardLabels = {
                {lblP1C1, lblP1C2, lblP1C3, lblP1C4, lblP1C5},
                {lblP2C1, lblP2C2, lblP2C3, lblP2C4, lblP2C5},
                {lblP3C1, lblP3C2, lblP3C3, lblP3C4, lblP3C5}
        };

        // assign image to AI player card labels
        for (final JLabel[] cardLabelArray : cardLabels) {
            for (final JLabel cardLabel : cardLabelArray) {
                cardLabel.setIcon(backIcon1);
            }
        }

        // assign images to local player's buttons
        for (int i = 0; i < buttons.length; i++) {
            final Card card = playerHandCards.get(i);
            final String suit = card.getSuit();
            final String rank = card.getRank();
            ImageIcon playerCardIcon;
            final String cardImgStr = getCardImgStr(suit, rank);

            //get path and set to string to be used in string format. no absolute path
            playerCardIcon = new ImageIcon(String.format(imagePath + "%s.png", cardImgStr));
            playerCardIcon = resizeImageIcon(playerCardIcon, 70, 90);
            buttons[i].setIcon(playerCardIcon);
        }
    }

    /**
     * Resizes the PNG card images so they are displayed consistently.
     *
     * @param icon the image to be resized
     * @param width the width to resize the image to
     * @param height the height to resize the image to
     * @return the resized ImageIcon
     */
    public ImageIcon resizeImageIcon(final ImageIcon icon, final int width, final int height) {
        final Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);

    }

    /**
     * Identifies the card and creates a new string
     * for getting the appropriate card image.
     *
     * @param suit the suit of the card
     * @param rank the rank/face value of the card
     * @return the string to be used in setting the appropriate image
     */
    private String getCardImgStr(final String suit, final String rank) {
        String cardSuitImgStr;
        String cardRankImgStr;
        switch (suit) {
            case "Hearts":
                cardSuitImgStr = "H";
                break;
            case "Diamonds":
                cardSuitImgStr = "D";
                break;
            case "Spades":
                cardSuitImgStr = "S";
                break;
            case "Clubs":
                cardSuitImgStr = "C";
                break;
            default:
                cardSuitImgStr = "X";
                break;
        }

        switch (rank) {
            case "9":
                cardRankImgStr = "9";
                break;
            case "10":
                cardRankImgStr = "10";
                break;
            case "Jack":
                cardRankImgStr = "J";
                break;
            case "Queen":
                cardRankImgStr = "Q";
                break;
            case "King":
                cardRankImgStr = "K";
                break;
            case "Ace":
                cardRankImgStr = "A";
                break;
            default:
                cardRankImgStr = "X";
                break;
        }

        return cardRankImgStr + cardSuitImgStr;
    }

    /**
     * Updates the GUI to display the currently played cards in the trick.
     *
     * @param trick current trick holding Card[] of played cards
     */
    public void updateTrickView(final Trick trick) {
        final Card[] playedCards = trick.getCardsPlayed();
        final JLabel[] trickLabelImages = {lblTrick0, lblTrick1, lblTrick2, lblTrick3};

        // loops through current cards in trick
        for (int i = 0; i < 4; i++) {
            final Card currentCard = playedCards[i];
            // if there is card in the trick, add its image to the appropriate label
            if (currentCard != null) {
                final String suit = currentCard.getSuit();
                final String rank = currentCard.getRank();
                final String cardImgString = getCardImgStr(suit, rank);
                ImageIcon trickCardIcon;


                trickCardIcon = new ImageIcon(String.format(imagePath + "%s.png", cardImgString));
                trickCardIcon = resizeImageIcon(trickCardIcon, 70, 90);
                trickLabelImages[i].setIcon(trickCardIcon);
            } else {
                trickLabelImages[i].setIcon(backIcon2);
            }

        }
    }

    /**
     * Update the labels for displaying the score.
     */
    public void updateScores() {
        final int[] teamScores = game.getScores();

        lblTeam1Score.setText("Team 0 Score: " + teamScores[0]);
        lblTeam1Score.setFont(new Font("Arial", Font.BOLD, 16));

        lblTeam2Score.setText("Team 1 Score: " + teamScores[1]);
        lblTeam2Score.setFont(new Font("Arial", Font.BOLD, 16));
    }

    /**
     * Update the labels for displaying the tricks won.
     */
    public void updateTricks() {
        final int[] teamTricks = game.getTricksWon();

        lblTeam0Tricks.setText("Team 0 Tricks: " + teamTricks[0]);
        lblTeam0Tricks.setFont(new Font("Arial", Font.BOLD, 16));

        lblTeam1Tricks.setText("Team 1 Tricks: " + teamTricks[1]);
        lblTeam1Tricks.setFont(new Font("Arial", Font.BOLD, 16));
    }

    /**
     * Update the labels for displaying what trump currently is.
     */
    public void updateTrump() {
        lblTrump.setText("Trump: " + game.getTrump());
        lblTrump.setFont(new Font("Arial", Font.BOLD, 16)); // Set font to Arial, bold, size 16
    }


    /**
     * If all players passed on the kitty card, ask them to decide trump.
     */
    public void askForTrump() {
        final Card kittyCard = game.getKittyCard();
        final String kittySuit = kittyCard.getSuit();

        final List<String> availableSuits = new ArrayList<>(Arrays.asList(
                "Hearts", "Diamonds", "Spades", "Clubs"));
        availableSuits.remove(kittySuit);

        final String[] suits = availableSuits.toArray(new String[0]);

        final String trump = (String) JOptionPane.showInputDialog(
                null, "Choose the trump suit:", "Decide Trump",
                JOptionPane.QUESTION_MESSAGE, null, suits, suits[0]);
        game.setTrump(trump);

        // if user selected a trump suit
        if (trump != null) {
            game.setPickedUp(false);
            game.setOrderedUp(false);
            game.setPlayerDecidedTrump(0);
            // to prevent trump from being null and throwing nullPointerException
        } else {
            game.setTrump("undecided");
        }

    }

    /**
     * Presents the user with the option to pass or order up trump.
     */
    public void askForPass() {
        final int decision = JOptionPane.showConfirmDialog(null, String.format(
                "Do you want to order up the %s of %s to player %s?", game.getKittyCard().getRank(),
                        game.getKittyCard().getSuit(), game.getDealer()), "Order Up or Pass",
                JOptionPane.YES_NO_OPTION);
        if (decision == JOptionPane.YES_OPTION) {
            if (game.getDealer() == 0) {
                game.setPickedUp(true);
                game.setPlayerDecidedTrump(0);
            } else {
                game.setOrderedUp(true);
                game.setPlayerDecidedTrump(0);
            }

            game.setTrump(game.getKittyCard().getSuit());
            game.assignCardPoints();
        } else if (decision == JOptionPane.NO_OPTION) {
            game.setOrderedUp(false);
            game.setOrderedUp(false);
        }
    }


    /**
     * Displays the decision the AI made when deciding on trump.
     */
    public void displayTrumpMessage() {


        if (game.isPickedUp()) {
            JOptionPane.showMessageDialog(null, String.format(
                    "Player %d picked up the %s of %s. \n %s is Trump. \nPlayer %d goes first.",
                    game.getPlayerDecidedTrump(), game.getKittyCard().getRank(),
                    game.getKittyCard().getSuit(), game.getTrump(), (game.getDealer() + 1) % 4));

        } else if (game.isOrderedUp()) {
            JOptionPane.showMessageDialog(null, String.format("Player %d"
                            + " ordered up the %s of %s to Player %d. \n %s is Trump. \nPlayer"
                            + " %d goes first.", game.getPlayerDecidedTrump(),
                    game.getKittyCard().getRank(), game.getKittyCard().getSuit(),
                    game.getDealer(), game.getTrump(), (game.getDealer() + 1) % 4));

            // else if kitty card suit does not equal trump and a player has decided trump
        } else if (!(game.getKittyCard().getSuit().equals(game.getTrump()))
                && game.getPlayerDecidedTrump() != -1) {
            JOptionPane.showMessageDialog(null, String.format(
                    "Player %d chooses %s to be Trump. \nPlayer %d goes first.",
                    game.getPlayerDecidedTrump(),
                    game.getTrump(), (game.getDealer() + 1) % 4));
        } else {
            JOptionPane.showMessageDialog(null, String.format(
                    "Player %d passed.", game.getCurrentPlayerTurn()));
        }

    }

    /**
     * Displays the winner of the game.
     *
     * @param winningTeam the winning team
     */
    public void displayWinner(final int winningTeam) {
        JOptionPane.showMessageDialog(null, String.format(
                "Game Over! Team %d wins!!", winningTeam));
    }

    /**
     * Displays the winner of the trick.
     *
     * @param winningPlayer number of the winning player
     */
    public void displayTrickWinner(final int winningPlayer) {
        JOptionPane.showMessageDialog(null, String.format(
                "Player %d won the trick!", winningPlayer));
    }

    /**
     * Displays the winner of the hand.
     *
     * @param winningTeam number of winning team
     */
    public void displayHandWinner(final int winningTeam) {
        JOptionPane.showMessageDialog(null, String.format(
                "Team %d won the hand!", winningTeam));
    }

    /**
     * Notifies the user that no one has decided on trump
     * and that the deal is moving to the next player.
     */
    public void displayNoChoice() {
        JOptionPane.showMessageDialog(null, String.format(
                "No one decided on trump. Deal goes to player %d!", game.getDealer()));
    }

    /**
     * Displays the next dealer.
     */
    public void displayDealer() {
        JOptionPane.showMessageDialog(null, String.format(
                "Deal moves to player %d!", game.getDealer()));
    }

    /**
     * Warning message for the user when playing off suit.
     *
     * @return true if player wants to continue off suit
     */
    public boolean displaySuitWarning() {
        final int decision = JOptionPane.showConfirmDialog(null,
                String.format("Are you sure you don't have any %s to play?",
                        game.getTrick().getLeadCard().getSuit()), "Play off suit?",
                JOptionPane.YES_NO_OPTION);
        return decision == JOptionPane.YES_OPTION;

    }

    /**
     * Prompts the user to play a card.
     */
    public void askUserPlayCard() {
        JOptionPane.showMessageDialog(
                null, "Please select a card to play.");
    }

    /**
     * Prompts the user to select a card to discard.
     */
    public void askUserForDiscard() {
        final List<String> playersCards = new ArrayList<>(List.of());
        for (final Card card : game.getPlayerHand().getCards()) {
            playersCards.add(String.format("%s of %s", card.getRank(), card.getSuit()));
        }

        final String[] cardsArray = playersCards.toArray(new String[0]);

        final String discardCard = (String) JOptionPane.showInputDialog(null,
                "Choose a card to discard:", "Choose Discard",
                JOptionPane.QUESTION_MESSAGE, null, cardsArray, cardsArray[0]);

        // for every card in players hand, if String discardCard contains both
        // the suit and rank, remove the card and add the kitty card
        for (final Card card : game.getPlayerHand().getCards()) {

            try {
                if (discardCard.contains(card.getSuit()) && discardCard.contains(card.getRank())) {
                    game.getPlayerHand().removeCard(card);
                    game.getPlayerHand().addCard(game.getKittyCard());
                    return;
                }
            } catch (NullPointerException e) {
                // game breaks if no discard is selected
            }
        }

    }

    /**
     * Resets the player's card buttons.
     */
    public void cardBtnReset() {
        btnC1.setEnabled(true);
        btnC2.setEnabled(true);
        btnC3.setEnabled(true);
        btnC4.setEnabled(true);
        btnC5.setEnabled(true);
    }

    /**
     * Prevents the player from playing a card without starting the game.
     */
    public void cardBtnInitialize() {
        btnC1.setEnabled(false);
        btnC2.setEnabled(false);
        btnC3.setEnabled(false);
        btnC4.setEnabled(false);
        btnC5.setEnabled(false);
    }

    /**
     * Sets the image for the kitty card.
     */
    public void setKittyCardImage() {

        final String cardImgString = getCardImgStr(game.getKittyCard().getSuit(),
                game.getKittyCard().getRank());
        ImageIcon kittyCardIcon;

        kittyCardIcon = new ImageIcon(String.format(imagePath + "%s.png", cardImgString));
        kittyCardIcon = resizeImageIcon(kittyCardIcon, 105, 135);
        lblKittyCard.setIcon(kittyCardIcon);
        if (game.getPlayerDecidedTrump() != -1) {
            lblKittyCard.setIcon(backIcon2);
        }
    }

    /**
     * Retrieves the current game instance.
     *
     * @return the game instance currently associated with the object.
     */
    public Game getGame() {
        return game;
    }
    /**
     * Sets the current game instance.
     *
     * @param game the game instance to be set.
     */
    public void setGame(final Game game) {
        this.game = game;
    }
    /**
     * Retrieves the current game controller instance.
     *
     * @return the game controller instance currently associated with the object.
     */
    public GameController getGameController() {
        return gameController;
    }
    /**
     * Sets the current game controller instance.
     *
     * @param gameController the game controller instance to be set.
     */
    public void setGameController(final GameController gameController) {
        this.gameController = gameController;
    }
    /**
     * Retrieves the current game view instance.
     *
     * @return the game view instance currently associated with the object.
     */
    public GameView getGameView() {
        return gameView;
    }
    /**
     * Sets the current game view instance.
     *
     * @param gameView the game view instance to be set.
     */
    public void setGameView(final GameView gameView) {
        this.gameView = gameView;
    }
    /**
     * Retrieves the list of cards currently in the player's hand.
     *
     * @return the list of Card objects representing the player's hand.
     */
    public List<Card> getPlayerHandCards() {
        return playerHandCards;
    }
    /**
     * Sets the cards in the player's hand.
     *
     * @param playerHandCards the list of Card objects to be set as the player's hand.
     */
    public void setPlayerHandCards(final List<Card> playerHandCards) {
        this.playerHandCards = playerHandCards;
    }

}
