import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests the Game class methods.
 */
class GameTest {
    /**
     * Game class.
     */
    private Game game;

    /**
     * Creates new game before each test.
     */
    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    /**
     * This test checks if the game setup is done correctly
     * and if the players and hands are initialized properly.
     */
    @Test
    void createPlayers() {

        final Player[] players = game.getPlayers();
        final Hand[] hands = game.getHands();
        final Deck deck = game.getDeck();

        // assert that players and hands are initialized correctly
        assertNotNull(players, "Players are not null.");
        assertNotNull(hands, "Hands are not null.");
        assertEquals(4, players.length, "There are 4 players.");
        assertEquals(4, hands.length, "There are 4 hands.");

        for (int i = 0; i < 4; i++) {
            assertNotNull(players[i], "There exists a player.");
            assertNotNull(hands[i], "There exists a hand.");

            // assert that each hand has 5 cards
            assertEquals(5, hands[i].getCards().size(), "Each hand has 5 cards.");

            // assert that each player's hand is correctly assigned
            assertSame(hands[i], players[i].getHand(), "Each player's hand is correctly assigned");
        }

        // assert that kittyCard is assigned from the deck
        assertNotNull(game.getKittyCard(), "There exists a kitty card.");
        assertSame(deck.getCards().get(0), game.getKittyCard(), "Kitty card the last card in the deck.");
    }

    /**
     * This test checks if the dealer is selected randomly
     * and if the turn of the current player is correctly set after the dealer.
     */
    @Test
    void decideDealer() {

        // test multiple iterations to ensure randomness
        for (int i = 0; i < 1000; i++) {
            game.decideDealer();

            final int dealer = game.getDealer();
            final int currentPlayerTurn = game.getCurrentPlayerTurn();

            // verify that dealer is a valid player index (0 to 3)
            assertTrue(dealer >= 0 && dealer < 4, "Dealer is a valid.");

            // verify that currentPlayerTurn is the next player after the dealer
            assertEquals((dealer + 1) % 4, currentPlayerTurn,"Current players turn is the next player after the dealer");
        }
    }

    /**
     * This test checks if the scores for both teams are correctly updated.
     */
    @Test
    void scorePoints() {

        // initial scores should be zero
        assertEquals(0, game.getScores()[0], "Scores are initialized to 0.");
        assertEquals(0, game.getScores()[1], "Scores are initialized to 0.");

        // test scoring points for team 0
        game.scorePoints(0, 5);
        assertEquals(5, game.getScores()[0], "Team 0 has 5 points.");

        // test scoring points for team 1
        game.scorePoints(1, 3);
        assertEquals(3, game.getScores()[1], "Team 1 has 3 points.");

        // test adding more points to existing score
        game.scorePoints(0, 2);
        assertEquals(7, game.getScores()[0], "Score updated properly.");

        // test negative points
        game.scorePoints(1, -1);
        assertEquals(2, game.getScores()[1], "Score updated properly.");
    }

    /**
     * This test checks if the method getPlayerHand()
     * returns the correct player's hand.
     */
    @Test
    void getPlayerHand() {

        // get the player hand and verify it is not null
        final Hand playerHand = game.getPlayerHand();
        assertNotNull(playerHand, "Player's hand exists.");

        // verify that the player hand is the same as hands[0]
        assertSame(playerHand, game.getHands()[0], "User player hand is hand[0].");
    }

    /**
     * This test checks if the newHand() method correctly
     * creates new hands for all the players.
     */
    @Test
    void newHand() {
        final Player[] initialPlayers = game.getPlayers();

        // call the newHand() method
        game.newHand();

        // verify that the players array is not null and has the same length
        final Player[] newPlayers = game.getPlayers();
        assertNotNull(newPlayers, "Players not null.");
        assertEquals(initialPlayers.length, newPlayers.length, "There are 4 players.");

        // verify that each player has a new hand
        for (int i = 0; i < newPlayers.length; i++) {
            final Hand newHand = newPlayers[i].getHand();
            assertNotNull(newHand, "Hand is not null.");
            assertNotSame(initialPlayers[i].getHand(), newHand,
                    "Original hand is not the same as new hand.");
        }
    }

    /**
     * This test checks if the assignCardPoints() method correctly assigns points
     * to cards when the trump suit is not decided and the leading card is null.
     */
    @Test
    void assignCardPoints() {
        // set up the test scenario with a null leading card and "undecided" trump
        game.getTrick().setLeadCard(null);
        game.setTrump("undecided");

        // call the method to assign card points
        game.assignCardPoints();

        // iterate through all cards in the players' hands and
        // assert their point values are under 8 and 2 or more
        for (final Player player : game.getPlayers()) {
            final Hand hand = player.getHand();
            final List<Card> cards = hand.getCards();

            for (final Card card : cards) {
                assertTrue(card.getPointValue() < 8, "All card point values are under 8.");
                assertTrue(card.getPointValue() >= 2, "All card point values are over 2.");
            }
        }
    }

    /**
     * This test checks if the assignCardPoints() method correctly
     * assigns points to cards when the trump suit is not decided
     * and the leading card is a Heart.
     */
    @Test
    void assignCardPoints1() {
        // set up the test scenario with a Heart leading card and "undecided" trump
        game.getTrick().setLeadCard(new Card("Hearts", "Queen", 0));
        game.setTrump("undecided");

        // call the method to assign card points
        game.assignCardPoints();

        // iterate through all cards in the players' hands
        // and assert their point values are under 14
        for (final Player player : game.getPlayers()) {
            final Hand hand = player.getHand();
            final List<Card> cards = hand.getCards();

            for (final Card card : cards) {
                assertTrue(card.getPointValue() < 14, "All card point values are under 14.");
                assertTrue(card.getPointValue() >= 2, "All card point values are over 2.");
            }
        }
    }

    /**
     * This test checks if the assignCardPoints() method correctly
     * assigns points to cards when the trump suit is Hearts and the
     * leading card is a 9 of Hearts.
     */
    @Test
    void assignCardPoints2() {
        final Card leadingCard = new Card("Hearts", "9", 0);
        game.getTrick().setLeadCard(leadingCard);
        game.setTrump("Hearts");

        game.assignCardPoints();

        // verify that the leading card's point value is set correctly
        assertEquals(14, game.getTrick().getLeadCard().getPointValue(), "Leading card's point value is set correctly.");
    }



    /**
     * This test checks if the assignCardPoints() method correctly assigns
     * points to card when the trump suit is Clubs and the leading card
     * is the Jack of Spades (Left Bower).
     */
    @Test
    void assignCardPoints3() {
        final Card leadingCard = new Card("Spades", "Jack", 0);
        game.getTrick().setLeadCard(leadingCard);
        game.setTrump("Clubs");

        game.assignCardPoints();

        // verify that the leading card's point value is set correctly
        assertEquals(19, game.getTrick().getLeadCard().getPointValue(), "Leading card's point value is set correctly.");
    }

    /**
     * This test checks if the AI correctly decides to order up trump when it has more
     * than 3 trump cards and it is not the dealer.
     */
    @Test
    void aiDecideTrump() {
        // set up the test scenario where AI has more
        // than 3 trump cards and is not the dealer
        game.setGoneOnce(false);
        game.setDealer(1);
        game.setCurrentPlayerTurn(2);
        final String trump = game.getKittyCard().getSuit();

        // create a hand with more than 3 trump cards
        final Hand hand = new Hand();
        hand.addCard(new Card(trump, "9", 14));
        hand.addCard(new Card(trump, "10", 15));
        hand.addCard(new Card(trump, "Jack", 20));
        hand.addCard(new Card("Spades", "9", 2));
        hand.addCard(new Card("Spades", "10", 3));

        // set the hand for the current player
        game.getPlayers()[game.getCurrentPlayerTurn()].setHand(hand);

        // call the method to decide trump
        game.aiDecideTrump();

        // assert that the AI ordered up trump
        assertTrue(game.isOrderedUp(), "AI ordered up trump.");
    }

    /**
     * This test checks if the AI correctly decides to pick up the
     * kitty card when it has at least 2 trump cards and it is the dealer.
     */
    @Test
    void aiDecideTrump2() {
        // set up the test scenario where AI has 2 or more trump cards and is the dealer
        game.setGoneOnce(false);
        game.setDealer(2);
        game.setCurrentPlayerTurn(2);
        final String trump = game.getKittyCard().getSuit();

        // create a hand with more than 2 trump cards
        final Hand hand = new Hand();
        hand.addCard(new Card(trump, "9", 14));
        hand.addCard(new Card(trump, "10", 15));
        hand.addCard(new Card("blank", "Jack", 20));
        hand.addCard(new Card("blank", "10", 3));
        hand.addCard(new Card("blank", "Jack", 4));

        // set the hand for the current player
        game.getPlayers()[game.getCurrentPlayerTurn()].setHand(hand);

        // call the method to decide trump
        game.aiDecideTrump();

        // assert that the AI picked up the kitty card
        assertTrue(game.isPickedUp(), "AI picked up kitty card.");
    }

    /**
     * This test checks if the AI correctly decides to follow suit when
     * it has a higher trump card but the leading card is not a trump card.
     */
    @Test
    void aiDecideCard() {
        // set up the test scenario where AI has a
        // higher trump card but has to follow suit
        final String trump = game.getKittyCard().getSuit();
        game.setCurrentPlayerTurn(1);

        // create a trick with a lead card and a higher card played by another player
        final Trick trick = new Trick();
        trick.setLeadCard(new Card("Hearts", "9", 8));
        trick.addCardToTrick(new Card("blank", "10", 13), 1);

        // create a hand with a higher trump card
        final Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "10", 9));
        hand.addCard(new Card(trump, "Queen", 16));

        // set the trick and hand for the current player
        game.setTrick(trick);
        game.getPlayers()[game.getCurrentPlayerTurn()].setHand(hand);

        // call the method to decide what card to play
        final Card cardToPlay = game.aiDecideCard();

        // assert that the AI decided to follow suit
        assertEquals("Hearts", cardToPlay.getSuit(), "AI followed suit.");
    }


    /**
     * This test checks if the AI correctly decides what card to
     * discard when it is the dealer and has picked up the kitty card.
     */
    @Test
    void aiDecideDiscard() {
        // AI player's hand before discard
        final Hand aiPlayerHandBefore = game.getPlayers()[game.getDealer()].getHand();
        // AI player's hand size before discard
        final int handSizeBefore = aiPlayerHandBefore.getCards().size();

        // call the aiDecideDiscard() function
        game.aiDecideDiscard();

        // get the AI player's hand after discard
        final Hand aiPlayerHandAfter = game.getPlayers()[game.getDealer()].getHand();
        // get the AI player's hand size after discard
        final int handSizeAfter = aiPlayerHandAfter.getCards().size();

        // assert that the hand size has hasn't changed
        assertEquals(handSizeBefore, handSizeAfter, "Hand size has hasn't changed.");

        // assert that the kitty card is in the AI player's hand
        assertTrue(aiPlayerHandAfter.getCards().contains(game.getKittyCard()), "Kitty card is in the AI player's hand.");
    }

    /**
     * This test checks if the method awardTrickPoints() correctly
     * assigns points to the player who won the trick.
     */
    @Test
    void awardTrickPoints() {
        // set up the trick with cards
        Card[] cardsPlayed = new Card[4];
        cardsPlayed[0] = new Card("Hearts", "9", 14);
        cardsPlayed[1] = new Card("Diamonds", "Ace", 18);
        cardsPlayed[2] = new Card("Clubs", "10", 15);
        cardsPlayed[3] = new Card("Spades", "King", 17);
        game.getTrick().setCardsPlayed(cardsPlayed);

        // call the awardTrickPoints() function
        final int playerWonTrick = game.awardTrickPoints();

        // assert that the player who won the trick is the correct player
        assertEquals(1, playerWonTrick, "Correct player won trick.");
        // assert that the winning team's tricks won count has been updated
        assertEquals(1, game.getTricksWon()[1], "Winning team's trick count updated.");
    }

    /**
     * This test checks if the method awardTrickPoints() throws
     * NullPointerException when one of the cards in the trick is null.
     */
    @Test
    void awardTrickPoints2() {
        // set up the trick with cards, where one card is null
        Card[] cardsPlayed = new Card[4];
        cardsPlayed[0] = new Card("Hearts", "9", 14);
        cardsPlayed[1] = null;
        cardsPlayed[2] = new Card("Clubs", "10", 15);
        cardsPlayed[3] = new Card("Spades", "King", 17);
        game.getTrick().setCardsPlayed(cardsPlayed);

        // call the awardTrickPoints() function, expecting a NullPointerException
        assertThrows(IllegalArgumentException.class, () -> game.awardTrickPoints(), "Throws exception.");
    }

    /**
     * This test checks if the method awardTeamPoints() correctly
     * assigns points to the team who won the round and if the
     * team has won all 5 tricks.
     */
    @Test
    void awardTeamPoints() {
        // set up the test scenario where team 0 wins all 5 tricks
        game.setTricksWon(new int[]{5, 0});

        // call the awardTeamPoints() method
        final int winningTeam = game.awardTeamPoints();

        // assert that team 0 receives 2 points and is the winning team
        assertEquals(2, game.getScores()[0],"Team 0 receives 2 points.");
        assertEquals(0, winningTeam, "Team 0 is the winning team.");
    }

    /**
     * This test checks if the method awardTeamPoints() correctly
     * assigns points to the team who won the round and if the
     * trump was decided by a player from team 1.
     */
    @Test
    void awardTeamPoints1() {
        // set up the test scenario where team 1 decides trump and team 2 wins
        game.setTricksWon(new int[]{2, 3});
        game.setPlayerDecidedTrump(0);

        // call the awardTeamPoints() method
        final int winningTeam = game.awardTeamPoints();

        // assert that team 0 receives 2 points and is the winning team
        assertEquals(2, game.getScores()[1], "Team 1 has 2 points.");
        assertEquals(1, winningTeam, "Team 1 won the hand.");
    }

    @Test
    void awardTeamPoints2() {
        // set up the test scenario where team 1 wins all 5 tricks
        game.setTricksWon(new int[]{0, 5});

        // call the awardTeamPoints() method
        final int winningTeam = game.awardTeamPoints();

        // assert that team 1 receives 2 points and is the winning team
        assertEquals(2, game.getScores()[1], "Team 1 has 2 points.");
        assertEquals(1, winningTeam, "Team 1 won the hand.");
    }

}
