package com.example.cards;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.easymock.EasyMock;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DeckTest extends TestCase {

    private CardsGame cardsGame;

    public static Test suite() {
        return new TestSuite(DeckTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        cardsGame = new CardsGame(new CardsGameListOperations());
    }

    /**
     * @Given: The deck of the game
     * @When: The game is created
     * @Then: The deck has 40 cards
     */
    public void testWhenDeckIsCreatedThenItHas40Cards() {
        assertEquals(cardsGame.getDeck().size(), 40);
    }

    /**
     * @Given: The deck of the game
     * @When: The game is created
     * @Then: The deck has 4o cards and is shuffled, which means it does not follow 
     * this order: 1,2,3,4,5,6,7,8,9,10,1,2.....10 , 4 times
     */
    public void testWhenGameIsCreatedThenDeckIsShuffled() {
        List<Integer> originalDeck = cardsGame.getDeck();
        List<Integer> toShuffleDeck = new ArrayList<>(cardsGame.getDeck());
        ListOperations listOperations = EasyMock.mock(ListOperations.class);
        listOperations.doShuffle(EasyMock.anyObject());
        EasyMock.expectLastCall().andAnswer(() -> {
            List<Integer> numbersList = (List<Integer>) EasyMock.getCurrentArguments()[0];
            numbersList = numbersList.stream()
                .map(number -> (int) (Math.random() * 10))
                .collect(Collectors.toList());
            return numbersList;
        });
        EasyMock.replay(listOperations);
        List<Integer> shuffledDeck = listOperations.doShuffle(toShuffleDeck);
        EasyMock.verify();
        assertTrue("Deck does not still have 40 cards", cardsGame.getDeck().size() == 40);
        assertNotEquals(shuffledDeck, originalDeck);
    }

    /**
     * @Given: A drawpile of player one
     * @When: A player draws a card from an empty draw pile
     * @Then: The discard pile is shuffled and the draw pile is refilled with the 
     *  shuffled discard pile
     */
    public void testWhenPlayerDrawsEmptyPileThenDiscardPileIsShuffled() {
        // Now testing as if player one draws a card
        DrawPile drawPile = cardsGame.getDrawPiles()[0];
        DiscardPile discardPile = cardsGame.getDiscardPiles()[0];
        Stack<Integer> drawPileClone = (Stack<Integer>) drawPile.getPile().clone();
        // Simulating drawing until pile is empty
        while(true) {
            cardsGame.drawCardFromDrawPile();
            if(discardPile.getPile().isEmpty())
                break;
        }
        assertEquals(19, drawPile.getPile().size()); //testing 19 because of redraw of previous while loop
        drawPileClone.pop();
        assertNotEquals(drawPile.getPile(), drawPileClone);
    }

    /**
     * @Given: Two player cards
     * @When: Two players draw cards from their drawpiles
     * @Then: The bigger cards wins
     */
    public void testWhenComparingTwoCardsThenBiggerOneWins() {
        int card1 = cardsGame.drawCardFromDrawPile();
        int card2 = cardsGame.drawCardFromDrawPile();
        DiscardPile discardPilePlayerOne = cardsGame.getDiscardPiles()[0];
        DiscardPile discardPilePlayerTwo = cardsGame.getDiscardPiles()[1];
        cardsGame.compareDrawnCards(card1, card2);
        if(card1 > card2){
            assertTrue("Discardpile of player one is not greater than player two's", 
                discardPilePlayerOne.getPile().size() > discardPilePlayerTwo.getPile().size());
        }else if(card2 > card1){
            assertTrue("Discardpile of player two is not greater than player one's", 
                discardPilePlayerTwo.getPile().size() > discardPilePlayerOne.getPile().size());
        }
    }

    /**
     * @Given: Two player cards
     * @When: Two players draw same valued cards from their drawpiles
     * @Then: Winner of next round gets to save those two cards from previous
     *  drawn round
     */
    public void testWhenComparingTwoSameCardsThenNextRoundWinnerGetsFour() {
        DiscardPile discardPilePlayerOne = cardsGame.getDiscardPiles()[0];
        DiscardPile discardPilePlayerTwo = cardsGame.getDiscardPiles()[1];
        int card1 = 10;
        int card2 = 10;
        cardsGame.compareDrawnCards(card1, card2);
        int card1SecondRound = cardsGame.drawCardFromDrawPile();
        int card2SecondRound = cardsGame.drawCardFromDrawPile();
        cardsGame.compareDrawnCards(card1SecondRound, card2SecondRound);
        if(card1SecondRound > card2SecondRound){
            assertTrue("Discardpile of player one has no 4 more cards than player two's", 
                discardPilePlayerOne.getPile().size() == discardPilePlayerTwo.getPile().size() + 4);
        }else if(card2 > card1){
            assertTrue("Discardpile of player two has no 4 more cards than player ones's", 
                discardPilePlayerTwo.getPile().size() == discardPilePlayerOne.getPile().size() + 4);
        }
    }
    
}
