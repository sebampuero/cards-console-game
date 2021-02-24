package com.example.cards;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        CardsGame game = new CardsGame(new CardsGameListOperations());
        int card1, card2;
        while(true) {
            System.out.println("Player 1 draw a card:");
            card1 = game.drawCardFromDrawPile();
            if(card1 == -1){
                System.out.println("Player 1 has no more cards!");
                break;
            }
            System.out.println("Player 2 draw a card:");
            card2 = game.drawCardFromDrawPile();
            if(card2 == -1){
                System.out.println("Player 2 has no more cards!");
                break;
            }
            game.compareDrawnCards(card1, card2);
        }
    }
}
