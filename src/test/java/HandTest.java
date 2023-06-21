import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    private Hand hand;

    @BeforeEach
    public void setUp() {
        hand = new Hand();
    }


    /**
     * Verify the getCards function returns a list of cards.
     */
    @Test
    void getCards() {
        // create some cards and add them to the hand
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Spades", "King", 10);
        hand.addCard(card1);
        hand.addCard(card2);

        // get the cards from the hand
        List<Card> cards = hand.getCards();

        // verify that the returned list contains the added cards
        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));
    }

    /**
     * Verify the addCards function adds a card to the list of cards in a hand.
     */
    @Test
    void addCard() {
        // create a card to add
        Card card = new Card("Spades", "Ace", 11);

        // add the card to the hand
        hand.addCard(card);

        // get the cards from the hand
        List<Card> cards = hand.getCards();

        // verify that the added card is in the hand
        assertTrue(cards.contains(card));
    }

    /**
     * Verify the removeCards function removes a card to the list of cards in a hand.
     */
    @Test
    void removeCard() {
        // create two cards
        Card card1 = new Card("Hearts", "Queen", 10);
        Card card2 = new Card("Spades", "King", 10);

        // add the cards to the hand
        hand.addCard(card1);
        hand.addCard(card2);

        // get the cards from the hand
        List<Card> cards = hand.getCards();

        // verify that the cards are in the hand
        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));

        // remove one of the cards from the hand
        hand.removeCard(card1);

        // verify that the removed card is no longer in the hand
        assertFalse(cards.contains(card1));
        assertTrue(cards.contains(card2));
    }

    /**
     * Verify the dealHand function creates a hand of five cards and that they are removed from the deck.
     */
    @Test
    void dealHand() {
        // create a deck object
        Deck deck = new Deck();

        // deal a hand of 5 cards from the deck
        hand.dealHand(deck);

        // get the cards from the hand
        List<Card> cards = hand.getCards();

        // verify that the hand contains 5 cards
        assertEquals(5, cards.size());

        // verify that the cards in the hand are not in the deck
        assertFalse(deck.getCards().containsAll(cards));
    }
}