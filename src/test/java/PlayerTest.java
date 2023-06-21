import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * Verify the getHand function returns the hand containing specific preset cards.
     */
    @Test
    void getHand() {
        // create a hand object
        Hand hand = new Hand();
        // add cards to the hand
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Spades", "King", 10);
        Card card3 = new Card("Diamonds", "Queen", 10);
        hand.addCard(card1);
        hand.addCard(card2);
        hand.addCard(card3);

        // create a player object with the hand
        Player player = new Player(hand);

        // verify that the player's hand is returned correctly
        assertEquals(hand, player.getHand());
    }

    /**
     * Verify the setHand function sets a players hand to the appropriate list of cards.
     */
    @Test
    void setHand() {
        // create a hand object
        Hand hand1 = new Hand();
        // add cards to the hand
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Spades", "King", 10);
        Card card3 = new Card("Diamonds", "Queen", 10);
        hand1.addCard(card1);
        hand1.addCard(card2);
        hand1.addCard(card3);

        // create another hand object
        Hand hand2 = new Hand();
        // add cards to the new hand
        Card card4 = new Card("Clubs", "Jack", 10);
        Card card5 = new Card("Spades", "Ten", 10);
        hand2.addCard(card4);
        hand2.addCard(card5);

        // create a new player with the original hand of 3 cards
        Player player = new Player(hand1);

        // set the player's hand to the new hand (hand2)
        player.setHand(hand2);

        // verify that the player's hand is updated correctly
        assertEquals(hand2, player.getHand());
    }

}