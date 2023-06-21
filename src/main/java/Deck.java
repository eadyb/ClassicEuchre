import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the deck of cards used in the game.
 * It handles shuffling, dealing cards, and tracking the remaining cards.
 */
public class Deck {

    /**
     * Creates a list of cards to act as the deck.
     */
    private List<Card> cards;

    /**
     * Constructor for Deck model.
     */
    public Deck() {
        cards = new ArrayList<>();
        // needs to be in constructor for game window initialization
        shuffleCards();
    }

    /**
     * Creates an un-shuffled deck of cards.
     */
    private void createDeck() {
        final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        final String[] ranks = {"9", "10", "Jack", "Queen", "King", "Ace"};


        for (final String suit : suits) {
            for (final String rank : ranks) {

                Card card = new Card(suit, rank, 0);

                cards.add(card);
            }
        }
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffleCards() {
        cards.clear();
        createDeck();
        final List<Card> shuffledDeck = new ArrayList<>();
        final Random random = new Random();
        int randInt;

        while (!cards.isEmpty()) {
            randInt = random.nextInt(cards.size());
            shuffledDeck.add(cards.get(randInt));
            cards.remove(randInt);
        }
        cards.addAll(shuffledDeck);
        shuffledDeck.clear();
    }

    /**
     * Deals a card to a player from shuffled deck.
     *
     * @return card to be dealt in player's hand
     */
    public Card dealCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot deal card from an empty deck.");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Gets the list of cards in the deck.
     *
     * @return list of cards in deck
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Sets the list of cards in deck to new list.
     *
     * @param cards new list for deck
     */
    public void setCards(final List<Card> cards) {
        this.cards = cards;
    }
}
