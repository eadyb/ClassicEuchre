/**
 * Represents a player in the game.
 * It holds the players hand.
 */
public class Player {

    /**
     * Holds a hand of cards for a player.
     */
    private Hand hand;

    /**
     * Constructor the Player model.
     *
     * @param hand player's hand
     */
    public Player(final Hand hand) {
        this.hand = hand;
    }


    /**
     * Gets the player's hand.
     *
     * @return a hand of cards
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Sets a player's hand.
     *
     * @param hand new hand for player
     */
    public void setHand(final Hand hand) {
        this.hand = hand;
    }


}
