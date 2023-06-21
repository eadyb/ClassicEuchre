import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods in the Cards class.
 */
class CardTest {

    /**
     * Create a card, get its rank, and verify they are equal.
     */
    @Test
    void getRankTest() {
        // create a card with a specific rank
        final Card card = new Card("Hearts", "Ace", 11);

        // call the getRank function
        final String rank = card.getRank();

        // verify that the returned rank matches the expected rank
        assertEquals( "Ace", rank, "The card is an Ace.");
    }

    /**
     * Create a card, change its rank with the set function, and it was updated.
     */
    @Test
    void setRankTest() {
        // create a card object
        final Card card = new Card("Hearts", "Ace", 11);

        // set a new rank using the setRank function
        card.setRank("King");

        // get the updated rank using the getRank function
        final String updatedRank = card.getRank();

        // verify that the updated rank matches the expected value
        assertEquals("King", updatedRank, "The rank was updated.");
    }

    /**
     * Create a card, get its point value, and verify they are equal.
     */
    @Test
    void getPointValueTest() {
        // create a card object with a point value of 5
        final Card card = new Card("Hearts", "Ace", 5);

        // get the point value using the getPointValue function
        final int pointValue = card.getPointValue();

        // verify that the retrieved point value matches the expected value
        assertEquals(5, pointValue, "The point value is correct.");
    }

    /**
     * Create a card, set its point value, and verify that it is updated.
     */
    @Test
    void setPointValueTest() {
        // create a card object
        final Card card = new Card("Hearts", "Ace", 0);

        // set the point value to 10
        card.setPointValue(10);

        // get the point value using the getPointValue function
        final int pointValue = card.getPointValue();

        // verify that the point value is set correctly
        assertEquals(10, pointValue, "The point value is set correctly.");
    }

    /**
     * Create a card, get its suit, and verify they are equal.
     */
    @Test
    void getSuitTest() {
        // create a card object
        final Card card = new Card("Hearts", "Ace", 0);

        // get the suit using the getSuit function
        final String suit = card.getSuit();

        // verify that the suit is retrieved correctly
        assertEquals("Hearts", suit, "The suit is correct.");
    }

    /**
     * Create a card, set its suit, and verify that it was updated.
     */
    @Test
    void setSuitTest() {
        // create a card object
        final Card card = new Card("Hearts", "Ace", 0);

        // set the suit using the setSuit function
        card.setSuit("Diamonds");

        // verify that the suit is set correctly
        assertEquals("Diamonds", card.getSuit(), "The suit was updated.");
    }

    /**
     * Create a card with the constructor and assert
     * all the values are applied properly.
     */
    @Test
    void constructorTest() {
        // create a card object using the constructor
        final Card card = new Card("Hearts", "Ace", 0);

        // verify that the card properties are set correctly
        assertEquals("Hearts", card.getSuit(), "Successfully applied suit.");
        assertEquals("Ace", card.getRank(), "Successfully applied rank.");
        assertEquals(0, card.getPointValue(), "Successfully applied point value.");
    }
}