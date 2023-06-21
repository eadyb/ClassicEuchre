import java.util.ArrayList;
import java.util.List;


/**
 * Represents a collection of cards held by a player.
 * provide methods for adding and removing cards from the hand,
 * sorting the cards, and performing other hand-related operations.
 */
public class Hand {

    /**
     * Creates a list to hold cards as objects.
     */
    private final List<Card> cards;

    /**
     * Constructor for Hand model.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Gets the cards in the hand.
     *
     * @return list of card objects
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Adds a card to a hand.
     *
     * @param card adds a card to the hand
     */
    public void addCard(final Card card) {
        cards.add(card);
    }

    /**
     * Removes a card from a hand.
     *
     * @param card card to be removed
     */
    public void removeCard(final Card card) {
        cards.remove(card);
    }

    /**
     * Deals 5 cards from the deck to create a hand.
     *
     * @param deck the game deck
     */
    public void dealHand(final Deck deck) {
        for (int i = 0; i < 5; i++) {
            final Card card = deck.dealCard();
            addCard(card);
        }
    }
}
