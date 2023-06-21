import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrickTest {

    private Trick trick;

    @BeforeEach
    public void setUp() {
        this.trick = new Trick();
    }

    /**
     * Verify the checkTrickForWin function returns true when 4 cards are in the trick.
     */
    @Test
    void checkTrickForWin() {

        // add four cards to the trick
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Spades", "King", 10);
        Card card3 = new Card("Diamonds", "Queen", 10);
        Card card4 = new Card("Clubs", "Jack", 10);
        trick.addCardToTrick(card1, 0);
        trick.addCardToTrick(card2, 1);
        trick.addCardToTrick(card3, 2);
        trick.addCardToTrick(card4, 3);

        // verify that the trick is considered full
        assertTrue(trick.checkTrickForWin());
    }

    /**
     * Verify the checkTrickForWin function returns false when 3 cards are in the trick.
     */
    @Test
    void checkTrickForWin2() {
        // add three cards to the trick
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Spades", "King", 10);
        Card card3 = new Card("Diamonds", "Queen", 10);
        trick.addCardToTrick(card1, 0);
        trick.addCardToTrick(card2, 1);
        trick.addCardToTrick(card3, 2);

        // verify that the trick is not considered full
        assertFalse(trick.checkTrickForWin());
    }

    /**
     * Verify the addCardToTrick function adds the proper card using the corresponding player number.
     */
    @Test
    void addCardToTrick() {
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Diamonds", "King", 10);
        int playerNum1 = 0;
        int playerNum2 = 1;

        trick.addCardToTrick(card1, playerNum1);
        trick.addCardToTrick(card2, playerNum2);

        // verify that both cards are added to the trick
        assertEquals(card1, trick.getCardsPlayed()[playerNum1]);
        assertEquals(card2, trick.getCardsPlayed()[playerNum2]);

        // verify that the lead card is still the first card played
        assertEquals(card1, trick.getLeadCard());
    }

    /**
     * Verify the clearTrick function sets all the elements in the array to null.
     */
    @Test
    void clearTrick() {
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Diamonds", "King", 10);
        Card card3 = new Card("Clubs", "Ace", 11);
        Card card4 = new Card("Spades", "King", 10);

        trick.addCardToTrick(card1, 0);
        trick.addCardToTrick(card2, 1);
        trick.addCardToTrick(card3, 2);
        trick.addCardToTrick(card4, 3);

        // assert that the trick is full
        assertTrue(trick.checkTrickForWin());

        trick.clearTrick();


        // verify that all elements in the Trick (cardsPlayed[]) are null
        assertNull(trick.getCardsPlayed()[0]);
        assertNull(trick.getCardsPlayed()[1]);
        assertNull(trick.getCardsPlayed()[2]);
        assertNull(trick.getCardsPlayed()[3]);
    }

    /**
     * Verify the getCardsPlayed function returns the array of cards in the trick.
     */
    @Test
    void getCardsPlayed() {
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Diamonds", "King", 10);

        trick.addCardToTrick(card1, 1);
        trick.addCardToTrick(card2, 2);

        Card[] cardsPlayed = trick.getCardsPlayed();

        // verify that the returned array contains the cards played in the trick
        assertEquals(card1, cardsPlayed[1]);
        assertEquals(card2, cardsPlayed[2]);
        assertNull(cardsPlayed[0]);
        assertNull(cardsPlayed[3]);
    }

    /**
     * Verify the setCardsPlayed function updates the current trick array.
     */
    @Test
    void setCardsPlayed() {
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Diamonds", "King", 10);


        Card[] cardsPlayed = new Card[4];
        cardsPlayed[1] = card1;
        cardsPlayed[2] = card2;

        trick.setCardsPlayed(cardsPlayed);

        // verify that the trick's cardsPlayed array is set correctly
        assertArrayEquals(cardsPlayed, trick.getCardsPlayed());
    }

    /**
     * Verify the getLeadCard function returns the first card played in the trick.
     */
    @Test
    void getLeadCard() {
        // add a card to the trick and verify it is set as the lead card
        Card card = new Card("Hearts", "Ace", 11);
        trick.addCardToTrick(card, 0);

        // verify that the lead card returned by getLeadCard() is the same as the added card
        assertEquals(card, trick.getLeadCard());
    }

    /**
     * Verify the setLeadCard function updates the lead card.
     */
    @Test
    void setLeadCard() {
        Card card = new Card("Spades", "King", 10);

        // set the lead card
        trick.setLeadCard(card);

        // verify that the lead card in the trick is set correctly
        assertEquals(card, trick.getLeadCard());
    }
}