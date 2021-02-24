package com.example.cards;

public class DiscardPile extends Pile{

    @Override
    protected int drawCard() {
        return pileOfCards.pop();
    }

    @Override
    protected void addCard(int card) {
       pileOfCards.push(card);
    }
    
}
