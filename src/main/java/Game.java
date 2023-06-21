import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class represents the Game as a model and holds
 * the overall game state and logic and manages
 * the deck, players, tricks, dealer and score.
 */
public class Game {

    /**
     * Integer array to hold the number of tricks won by each team.
     */
    private int[] tricksWon;
    /**
     * Holds the player who decided what trump should be.
     */
    private int playerDecidedTrump;
    /**
     * Stores whether the kitty card was ordered up.
     */
    private boolean orderedUp;
    /**
     * Stores whether the kitty card was picked up.
     */
    private boolean pickedUp;

    /**
     * The card that is turned over for the players to decide trump on.
     */
    private Card kittyCard;
    /**
     * Holds the current player turn.
     */
    private int currentPlayerTurn;
    /**
     * Represents a trick for the game.
     */
    private Trick trick;

    /**
     * The deck of cards.
     */
    private Deck deck;
    /**
     * The array of players.
     */
    private Player[] players;
    /**
     * The current dealer.
     */
    private int dealer;
    /**
     * The array of scores.
     */
    private int[] scores;
    /**
     * The array of hands.
     */
    private Hand[] hands;
    /**
     * Holds the trump value as a String.
     */
    private String trump;
    /**
     * Holds whether each player has decided on trump or not.
     */
    private boolean goneOnce;


    /**
     * This is the constructor for the game.
     */
    public Game() {
        this.tricksWon = new int[2];
        this.playerDecidedTrump = -1;
        this.orderedUp = false;
        this.pickedUp = false;
        this.currentPlayerTurn = this.dealer + 1;
        this.trick = new Trick();
        this.deck = new Deck();
        this.players = new Player[4];
        this.dealer = 0;
        this.scores = new int[2];
        this.hands = new Hand[4];
        this.trump = "undecided";

        this.goneOnce = false;

        // needs to be here to initialize game window
        createPlayers();
        decideDealer();

    }

    /**
     * Deals each player a new random hand, resets their
     * trick counter, and selects the kitty card.
     */
    public void createPlayers() {
        for (int i = 0; i < 4; i++) {
            hands[i] = new Hand();
            hands[i].dealHand(deck); // Deal 5 cards to each hand
            players[i] = new Player(hands[i]);
        }
        kittyCard = deck.getCards().get(0);
    }

    /**
     * Decides a dealer at random by picking a number between 0 and 3.
     */
    public void decideDealer() {
        final Random random = new Random();
        dealer = random.nextInt(4);
        currentPlayerTurn = (dealer + 1) % 4;
    }

    /**
     * Increments the score of the winning team.
     *
     * @param teamNum   the number of the winning team
     * @param numPoints the number of points to be awarded
     */
    public void scorePoints(final int teamNum, final int numPoints) {
        scores[teamNum] += numPoints;
    }

    /**
     * Returns the local players hand.
     *
     * @return hand[0] as local player is referred to as player[0]
     */
    public Hand getPlayerHand() {
        return hands[0];
    }

    /**
     * Resets players hands and tricks won (move to controller class).
     */
    public void newHand() {
        deck.shuffleCards();
        this.players = new Player[4];
        createPlayers();
    }


    /**
     * Assigns a card a point value based off of the leading card suit and trump.
     */
    public void assignCardPoints() {
        final Card leadingCard = trick.getLeadCard();
        String leadingSuit = null;

        // find the suit of the left bower
        String leftBowerSuit = switch (trump) {
            case "Hearts" -> "Diamonds";
            case "Diamonds" -> "Hearts";
            case "Clubs" -> "Spades";
            case "Spades" -> "Clubs";
            default -> "";
        };



        // if someone has played a card, assign a point value to the leading
        // card in the trick since it is no longer in the player's hand
        if (leadingCard != null) {
            leadingSuit = trick.getLeadCard().getSuit();
            if (trick.getLeadCard().getSuit().equals(trump)) {
                switch (trick.getLeadCard().getRank()) {
                    case "9" -> trick.getLeadCard().setPointValue(14);
                    case "10" -> trick.getLeadCard().setPointValue(15);
                    case "Jack" -> trick.getLeadCard().setPointValue(20);
                    case "Queen" -> trick.getLeadCard().setPointValue(16);
                    case "King" -> trick.getLeadCard().setPointValue(17);
                    case "Ace" -> trick.getLeadCard().setPointValue(18);
                    default -> {
                    }
                }

                // lead card is left bower
            } else if (leadingCard.getSuit().equals(leftBowerSuit)
                    && "Jack".equals(leadingCard.getRank())) {
                trick.getLeadCard().setPointValue(19);
            } else {
                switch (trick.getLeadCard().getRank()) {
                    case "9" -> trick.getLeadCard().setPointValue(8);
                    case "10" -> trick.getLeadCard().setPointValue(9);
                    case "Jack" -> trick.getLeadCard().setPointValue(10);
                    case "Queen" -> trick.getLeadCard().setPointValue(11);
                    case "King" -> trick.getLeadCard().setPointValue(12);
                    case "Ace" -> trick.getLeadCard().setPointValue(13);
                    default -> {
                    }
                }
            }
        }

        // assign point values to the cards in each players hand if trump
        for (final Player player : players) {
            final Hand hand = player.getHand();
            final List<Card> cards = hand.getCards();


            for (final Card card : cards) {
                if (card.getSuit().equals(trump)) {

                    switch (card.getRank()) {
                        case "9" -> card.setPointValue(14);
                        case "10" -> card.setPointValue(15);
                        case "Jack" -> card.setPointValue(20);
                        case "Queen" -> card.setPointValue(16);
                        case "King" -> card.setPointValue(17);
                        case "Ace" -> card.setPointValue(18);
                        default -> {
                        }
                    }

                    // if card is of the leading suit but not trump
                } else if (card.getSuit().equals(leadingSuit) && !(card.getSuit().equals(trump))) {
                    switch (card.getRank()) {
                        case "9" -> card.setPointValue(8);
                        case "10" -> card.setPointValue(9);
                        case "Jack" -> card.setPointValue(10);
                        case "Queen" -> card.setPointValue(11);
                        case "King" -> card.setPointValue(12);
                        case "Ace" -> card.setPointValue(13);
                        default -> {
                        }
                    }

                    // if card is the left bower
                } else if ("Jack".equals(card.getRank()) && card.getSuit().equals(leftBowerSuit)) {
                    card.setPointValue(19);

                    // for all remaining cards
                } else {
                    switch (card.getRank()) {
                        case "9" -> card.setPointValue(2);
                        case "10" -> card.setPointValue(3);
                        case "Jack" -> card.setPointValue(4);
                        case "Queen" -> card.setPointValue(5);
                        case "King" -> card.setPointValue(6);
                        case "Ace" -> card.setPointValue(7);
                        default -> card.setPointValue(0);
                    }
                }
            }
        }
    }


    /**
     * Asks the AI player to decide trump. If not everyone
     * has gone, it will choose pass or pickup.
     */
    public void aiDecideTrump() {
        int trumpCount = 0;
        final Hand hand = this.getPlayers()[currentPlayerTurn].getHand();

        if (!goneOnce) {
            trump = kittyCard.getSuit();
        } else {
            trump = "undecided";
        }

        assignCardPoints();

        // increments trumpCount for each trump card in hand
        for (final Card card : hand.getCards()) {
            if (card.getSuit().equals(trump)) {
                trumpCount++;
            }
        }


        // if not everyone has gone, use this logic to make a decision
        if (!goneOnce) {
            // if AI player has more than 3 trump, order it up. dealer must discard one.
            if (trumpCount >= 3 && currentPlayerTurn != dealer) {
                trump = kittyCard.getSuit();

                // set pickedUp to true, remove the lowest card and add the kitty card
                // to player hand, set player decided trump to current player
                playerDecidedTrump = currentPlayerTurn;
                orderedUp = true;
                assignCardPoints();
                aiDecideDiscard();

                // if AI player has 2 or more trump in his hand,
                // and he is the dealer, pick up kitty card
            } else if (trumpCount >= 2 && currentPlayerTurn == dealer) {
                trump = kittyCard.getSuit();
                pickedUp = true;
                playerDecidedTrump = currentPlayerTurn;
                assignCardPoints();
                aiDecideDiscard();
            }
        } else {
            // check if player has good enough cards to choose trump
            // by adding point value of similar suit cards
            int highestSuitScore = 0;
            int[] suitPoints = new int[4];

            for (final Card card : hand.getCards()) {
                switch (card.getSuit()) {
                    case "Hearts" -> suitPoints[0] += card.getPointValue();
                    case "Diamonds" -> suitPoints[1] += card.getPointValue();
                    case "Clubs" -> suitPoints[2] += card.getPointValue();
                    case "Spades" -> suitPoints[3] += card.getPointValue();
                    default -> throw new IllegalArgumentException("Error in checking if player has"
                            + " good enough cards to choose trump.");
                }

                // checks to see if the user has good enough
                // cards to choose when trump is undecided
                for (int suitPoint : suitPoints) {
                    if (suitPoint > 25 && suitPoint > highestSuitScore) {
                        playerDecidedTrump = currentPlayerTurn;
                        highestSuitScore = suitPoint;
                        trump = card.getSuit();
                        assignCardPoints();
                        orderedUp = true;
                    }
                }
            }
        }
    }

    /**
     * Chooses a card for the AI to play.
     */
    public Card aiDecideCard() {
        Card cardToPlay = new Card("blank", "blank", 0);
        int highestCardInTrick = 0;
        int lowestCardInHand = 21;


        // find the point value of the current high card
        for (final Card card : trick.getCardsPlayed()) {
            if (card != null && card.getPointValue() > highestCardInTrick) {
                highestCardInTrick = card.getPointValue();
            }
        }

        if (highestCardInTrick != 0) {
            for (final Card playerCard : players[currentPlayerTurn].getHand().getCards()) {

                // if no card following lead suit has been found, card has higher point
                // value than the highest card in trick, and is trump, set as card to be played
                if (playerCard.getPointValue() > highestCardInTrick && playerCard.getSuit().equals(trump)
                        && !(cardToPlay.getSuit().equals(trick.getLeadCard().getSuit()))) {
                    // makes sure lowest trump to win trick is played
                    if (playerCard.getPointValue() < cardToPlay.getPointValue()
                            && cardToPlay.getSuit().equals(trump)) {
                        cardToPlay = playerCard;
                    }

                    // else if card has higher point value than the highest card in trick
                    // and is same suit as leading card, set as card to be played
                } else if (playerCard.getPointValue() > highestCardInTrick
                        && playerCard.getSuit().equals(trick.getLeadCard().getSuit())) {
                    cardToPlay = playerCard;
                    // else if card follows leading suit and current cardToPlay < highestScore
                } else if (playerCard.getSuit().equals(trick.getLeadCard().getSuit()) && cardToPlay.getPointValue()
                        < highestCardInTrick && playerCard.getPointValue() < cardToPlay.getPointValue()) {
                    // if the card is lower than the current card, choose it
                    cardToPlay = playerCard;
                }
            }
        }


        // if no playable card is found, choose
        // the card with the lowest point value to play
        if ("blank".equals(cardToPlay.getSuit())) {
            for (final Card card : players[currentPlayerTurn].getHand().getCards()) {
                if (card.getPointValue() < lowestCardInHand) {
                    lowestCardInHand = card.getPointValue();
                    cardToPlay = card;
                }
            }
        }
        players[currentPlayerTurn].getHand().removeCard(cardToPlay);
        return cardToPlay;
    }


    /**
     * Removes the lowest card from the AI players hand if ordered up or picked up.
     */
    public void aiDecideDiscard() {

        assignCardPoints();

        int lowestCard = 20;
        Card discardCard = null;
        final Hand hand = this.getPlayers()[dealer].getHand();

        // this for loop finds the lowest card in
        // hand based off trump suit and discards it
        for (final Card card : hand.getCards()) {
            if (card.getPointValue() <= lowestCard) {
                lowestCard = card.getPointValue();
                discardCard = card;
            }
        }
        assignCardPoints();

        hand.removeCard(discardCard);
        hand.addCard(kittyCard);

    }


    /**
     * Increments the team trick score for the winning team of the trick.
     *
     * @return the playerNum of the player who won the trick
     */
    public int awardTrickPoints() {
        int highestCard = 0;
        int winningTeam = 0;
        int playerWonTrick = 0;

        Card trickCard;

        for (int i = 0; i < 4; i++) {
            trickCard = trick.getCardsPlayed()[i];
            if (trickCard != null) {
                if (trickCard.getPointValue() > highestCard) {
                    highestCard = trickCard.getPointValue();
                    winningTeam = i % 2;
                    playerWonTrick = i;
                }
            } else {
                throw new IllegalArgumentException("Null card found in trick");
            }
        }
        tricksWon[winningTeam]++;
        return playerWonTrick;
    }


    /**
     * Awards the winning team of the hand points and returns the winning team.
     *
     * @return the winning team number
     */
    public int awardTeamPoints() {
        if (tricksWon[0] >= 3) {
            // if all 5 tricks were taken by team 0
            if (tricksWon[0] == 5) {
                scorePoints(0, 2);
                return 0;
                // else if opposite team decided trump
            } else if (playerDecidedTrump % 2 != 0) {
                scorePoints(0, 2);
                return 0;
            } else {
                scorePoints(0, 1);
                return 0;
            }
        } else if (tricksWon[1] == 5) {
            scorePoints(1, 2);
            return 1;
        } else if (playerDecidedTrump % 2 != 1) {
            scorePoints(1, 2);
            return 1;
        } else {
            scorePoints(1, 1);
            return 1;
        }

    }


    /**
     * Gets the current deck of cards.
     *
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Sets the deck to new deck.
     *
     * @param deck new deck object to be set as deck
     */
    public void setDeck(final Deck deck) {
        this.deck = deck;
    }

    /**
     * Gets the array of players.
     *
     * @return Player[0-3]
     */
    public Player[] getPlayers() {
        return players.clone();
    }

    /**
     * Set the Player[].
     *
     * @param players updates player
     */
    public void setPlayers(final Player... players) {
        this.players = Arrays.copyOf(players, players.length);
    }

    /**
     * Gets the current dealer.
     *
     * @return an integer that represents the index
     * of the player number that is the dealer
     */
    public int getDealer() {
        return dealer;
    }

    /**
     * Sets the dealer.
     *
     * @param dealer is the playerNum of the dealer
     */
    public void setDealer(final int dealer) {
        this.dealer = dealer;
    }

    /**
     * Gets the array of scores.
     *
     * @return the array of scores
     */
    public int[] getScores() {
        return scores.clone();
    }

    /**
     * Sets the scores of the teams.
     *
     * @param scores is int[] of scores
     */
    public void setScores(final int... scores) {
        this.scores = Arrays.copyOf(scores, scores.length);
    }

    /**
     * Gets the array of hands.
     *
     * @return hands as an array
     */
    public Hand[] getHands() {
        return hands.clone();
    }

    /**
     * Sets the array of hands.
     *
     * @param hands is the array of hands to be set
     */
    public void setHands(final Hand... hands) {
        this.hands = Arrays.copyOf(hands, hands.length);
    }

    public Trick getTrick() {
        return trick;
    }

    public void setTrick(final Trick trick) {
        this.trick = trick;
    }

    public Card getKittyCard() {
        return kittyCard;
    }

    public void setKittyCard(final Card kittyCard) {
        this.kittyCard = kittyCard;
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(final int currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public String getTrump() {
        return trump;
    }

    public void setTrump(final String trump) {
        this.trump = trump;
    }


    public boolean isGoneOnce() {
        return goneOnce;
    }

    public void setGoneOnce(final boolean goneOnce) {
        this.goneOnce = goneOnce;
    }


    public boolean isOrderedUp() {
        return orderedUp;
    }

    public void setOrderedUp(final boolean orderedUp) {
        this.orderedUp = orderedUp;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(final boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public int[] getTricksWon() {
        return tricksWon.clone();
    }

    public void setTricksWon(final int... tricksWon) {
        this.tricksWon = Arrays.copyOf(tricksWon, tricksWon.length);
    }

    public int getPlayerDecidedTrump() {
        return playerDecidedTrump;
    }

    public void setPlayerDecidedTrump(final int playerDecidedTrump) {
        this.playerDecidedTrump = playerDecidedTrump;
    }

}
