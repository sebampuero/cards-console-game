package com.example.cards;

import java.util.List;
import java.util.Stack;

public  abstract class Pile {

    protected Stack<Integer> pileOfCards;

    public Pile() {
        this.pileOfCards = new Stack<>();
    }

    public void setPile(Stack<Integer> newPile) {
        this.pileOfCards.clear();
        this.pileOfCards.addAll(newPile);
    }

    public void printPile() {
        System.out.println(this.pileOfCards);
    }

    protected abstract int drawCard();

    protected abstract void addCard(int card);

    public void populatePile(List<Integer> cards) {
        pileOfCards.addAll(cards);
    }

    @Override
    public String toString() {
        return "Pile [pileOfCards=" + pileOfCards + "]";
    }

    public Stack<Integer> getPile() {
        return this.pileOfCards;
    }
    
}
