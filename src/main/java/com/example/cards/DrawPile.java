package com.example.cards;

public class DrawPile extends Pile {

    @Override
    protected int drawCard() {
        return pileOfCards.pop();
    }

    @Override
    protected void addCard(int card) {
        pileOfCards.push(card);
    }
    
}
