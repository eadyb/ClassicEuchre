import java.util.Arrays;

/**
 * Represents a trick in the game. It tracks the cards played
 * by each player and determines the winner of the trick.
 */
public class Trick {

    /**
     * Array of cards for the trick.
     */
    private Card[] cardsPlayed;
    /**
     * Holds the leading card of the current round.
     */
    private Card leadCard;

    /**
     * Constructor for Trick model.
     */
    public Trick() {
        cardsPlayed = new Card[4];
    }


    /**
     * Determines the winner.
     *
     * @return true if trick is full
     */
    public boolean checkTrickForWin() {
        int numCards = 0;
        for (final Card card : cardsPlayed) {
            if (card != null) {
                numCards++;
            }
        }
        return numCards == 4;
    }


    /**
     * Adds a card to the trick and sets it as the
     * lead card if it is the first card played.
     *
     * @param card card to be added to the trick
     * @param playerNum the player that played the card
     */
    public void addCardToTrick(final Card card, final int playerNum) {
        cardsPlayed[playerNum] = card;

        int numEmpty = 0;
        // checks each index in cards played if null
        for (int i = 0; i < 4; i++) {
            if (cardsPlayed[i] == null) {
                numEmpty++;
            }
        }

        // if there are three empty trick slots, set card to leading card
        if (numEmpty == 3) {
            leadCard = card;
        }
    }

    /**
     * Sets each index in the trick to null.
     */
    public void clearTrick() {
        for (int i = 0; i < 4; i++) {
            cardsPlayed[i] = null;
        }
    }

    public Card[] getCardsPlayed() {
        return Arrays.copyOf(cardsPlayed, cardsPlayed.length);
    }

    public void setCardsPlayed(final Card... cardsPlayed) {
        this.cardsPlayed = Arrays.copyOf(cardsPlayed, cardsPlayed.length);
    }

    public Card getLeadCard() {
        return leadCard;
    }

    public void setLeadCard(final Card leadCard) {
        this.leadCard = leadCard;
    }

}
