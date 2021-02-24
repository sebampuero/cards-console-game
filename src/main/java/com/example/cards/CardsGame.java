package com.example.cards;

import java.util.ArrayList;
import java.util.List;

public class CardsGame {

    private List<Integer> deck;
    private DrawPile pilePlayerOne = new DrawPile();
    private DrawPile pilePlayerTwo = new DrawPile();
    private DiscardPile discardPilePlayerOne = new DiscardPile();
    private DiscardPile discardPilePlayerTwo = new DiscardPile();
    private int currentTurn;
    private int winner;
    private ArrayList<Integer> cardsFromPreviousRound = new ArrayList<>();
    private boolean drawFromPreviousRound = false;
    private final int playerOne = 1;
    private final int playerTwo = 2;
    private ListOperations listOperations;

    public CardsGame(ListOperations listOperations) {
        initializeDeck();
        this.listOperations = listOperations;
        doShuffle(this.deck);
        this.pilePlayerOne.populatePile(this.deck.subList(0, 20));
        this.pilePlayerTwo.populatePile(this.deck.subList(20, 40));
        this.discardPilePlayerOne.populatePile(this.deck.subList(0, 20));
        this.discardPilePlayerTwo.populatePile(this.deck.subList(20, 40));
        this.currentTurn = playerOne;
    }

    private void doShuffle(List<Integer> list) {
        this.listOperations.doShuffle(list);
    }

    private void initializeDeck() {
        this.deck = new ArrayList<>();
        for(int i = 1; i<5; i++) {
            for(int j = 1; j < 11; j++) {
                this.deck.add(j);
            }
        }
    }

    private void changeTurn() {
        this.currentTurn = (playerOne == currentTurn) ? playerTwo : playerOne;
    }

    public int drawCardFromDrawPile() {
        if(isThereWinner()){
            printWinner();
            return -1;
        }
        repopulateDrawPile();
        int drawnCard;
        if(currentTurn == playerOne){
            drawnCard = pilePlayerOne.drawCard();
        }else{
            drawnCard = pilePlayerTwo.drawCard();
        }
        printMove(drawnCard);
        changeTurn();
        return drawnCard;
    }

    public void compareDrawnCards(int cardP1, int cardP2) {
        if(cardP1 > cardP2) {
            discardPilePlayerOne.addCard(cardP1);
            discardPilePlayerOne.addCard(cardP2);
            if(drawFromPreviousRound){
                populateDiscardPileWithPreviousCards(discardPilePlayerOne);
            }
            printWinnerPlayer(playerOne);
        }else if(cardP2 > cardP1) {
            discardPilePlayerTwo.addCard(cardP1);
            discardPilePlayerTwo.addCard(cardP2);
            if(drawFromPreviousRound){
                populateDiscardPileWithPreviousCards(discardPilePlayerTwo);
            }
            printWinnerPlayer(playerTwo);
        }else {
            drawFromPreviousRound = true;
            cardsFromPreviousRound.add(cardP1);
            cardsFromPreviousRound.add(cardP2);
            printNoWinnerThisRound();
        }
    }

    private void populateDiscardPileWithPreviousCards(DiscardPile discardPile) {
        for(Integer i : cardsFromPreviousRound){
            discardPile.addCard(i);
        }
        drawFromPreviousRound = false;
        cardsFromPreviousRound.clear();
    }

    private void repopulateDrawPile() {
        if(currentTurn == playerOne) {
            if(pilePlayerOne.getPile().empty()){
                doShuffle(discardPilePlayerOne.getPile());
                pilePlayerOne.setPile(discardPilePlayerOne.getPile());
                discardPilePlayerOne.getPile().clear();
            }
        }else{
            if(pilePlayerTwo.getPile().empty()){
                doShuffle(discardPilePlayerTwo.getPile());
                pilePlayerTwo.setPile(discardPilePlayerTwo.getPile());
                discardPilePlayerTwo.getPile().clear();
            }
        }
    }

    private boolean isThereWinner() {
        if(currentTurn == playerOne){
            if(pilePlayerOne.getPile().empty() && discardPilePlayerOne.getPile().empty()){
                this.winner = playerTwo;
                return true;
            }
        }else{
            if(pilePlayerTwo.getPile().empty() && discardPilePlayerTwo.getPile().empty()){
                this.winner = playerOne;
                return true;
            }
        }
        return false;
    }

    public List<Integer> getDeck() {
        return this.deck;
    }

    public DiscardPile[] getDiscardPiles() {
        return new DiscardPile[]{this.discardPilePlayerOne, this.discardPilePlayerTwo};
    }

    public DrawPile[] getDrawPiles() {
        return new DrawPile[]{this.pilePlayerOne, this.pilePlayerTwo};
    }

    private void printMove(int drawnCard) {
        int sumOfCardsOfPlayer;
        if(currentTurn == playerOne){
            sumOfCardsOfPlayer = (pilePlayerOne.getPile().size() + discardPilePlayerOne.getPile().size())/2;
            System.out.println("Player " + playerOne + " (" + sumOfCardsOfPlayer + "): " + drawnCard);
        }
        else{
            sumOfCardsOfPlayer = (pilePlayerTwo.getPile().size() + discardPilePlayerTwo.getPile().size())/2;
            System.out.println("Player " + playerTwo + " (" + sumOfCardsOfPlayer + "): " + drawnCard);
        }
    }

    private void printWinnerPlayer(int player) {
        System.out.println("Player " + player + " wins this round.");
    }

    private void printNoWinnerThisRound() {
        System.out.println("No winner this round");
    }

    private void printWinner() {
        System.out.println("Player " +  winner + " wins the game!");
    }
    
}
