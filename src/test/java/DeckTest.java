import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Deck model.
 */
class DeckTest {

    /**
     * Sets the deck with cards, gets them, and asserts they are equivalent.
     */
    @Test
    void getCards() {
        // create a list of cards
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card("Hearts", "Ace", 0));
        cards.add(new Card("Spades", "King", 10));
        cards.add(new Card("Diamonds", "Queen", 10));

        // create a deck object and set the cards
        final Deck deck = new Deck();
        deck.setCards(cards);

        // verify they are the same
        final List<Card> retrievedCards = deck.getCards();
        assertEquals(cards, retrievedCards, "Cards are the same.");
    }

    /**
     * Set the deck of cards and get the deck of cards twice, verify they are equal.
     */
    @Test
    void setCards() {
        // create a list of cards
        final List<Card> cards = new ArrayList<>();
        cards.add(new Card("Hearts", "Ace", 0));
        cards.add(new Card("Spades", "King", 10));
        cards.add(new Card("Diamonds", "Queen", 10));

        // create a deck object and set the cards
        final Deck deck = new Deck();
        deck.setCards(cards);

        // assert the cards gotten are the cards received
        final List<Card> retrievedCards = deck.getCards();
        assertEquals(cards, retrievedCards, "Cards are the same.");

        // create a new list of cards
        final List<Card> newCards = new ArrayList<>();
        newCards.add(new Card("Clubs", "Jack", 10));
        newCards.add(new Card("Hearts", "King", 10));

        // set the new cards using setCards()
        deck.setCards(newCards);

        // verify the updated list
        final List<Card> updatedCards = deck.getCards();
        assertEquals(newCards, updatedCards, "Cards are the same.");
    }

    /**
     * Test that shuffling the deck re-orders the cards.
     * Since references aren't changed, hard values have to be
     * compared. Which means this test will fail 1/24 times.
     */
    @Test
    void shuffleCards() {

        // initialize the deck
        final Deck deck = new Deck();

        // store the rank and suit of the first card
        final String originalFirstCard = deck.getCards().get(0).getRank() + deck.getCards().get(0).getSuit();

        // shuffle the cards
        deck.shuffleCards();

        // verify that the size of the shuffled cards
        // list is the same as the initial cards list
        assertEquals(24, deck.getCards().size(), "Deck size remained same.");

        // verify the first card in the deck has changed
        assertNotEquals(deck.getCards().get(0).getRank() + deck.getCards().get(0).getSuit(), originalFirstCard);

    }

    /**
     * Deals a card to and verifies the deck no longer contains that card.
     */
    @Test
    void dealCard() {
        // create a deck object
        final Deck deck = new Deck();

        // deal a card from the deck
        final Card dealtCard = deck.dealCard();

        // verify that the size of the deck is reduced by one
        assertEquals(23, deck.getCards().size(), "Size of deck reduced by 1.");


        // verify that the dealt card is no longer in the deck
        assertFalse(deck.getCards().contains(dealtCard), "Card no longer in deck.");
    }
}