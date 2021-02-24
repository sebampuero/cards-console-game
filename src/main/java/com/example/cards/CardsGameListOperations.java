package com.example.cards;

import java.util.List;
import java.util.Random;

public class CardsGameListOperations implements ListOperations {

    @Override
    public List<Integer> doShuffle(List<Integer> list) {
        int n = list.size();
        Random r = new Random(); 
          
        for (int i = n-1; i > 0; i--) { 
            int j = r.nextInt(i+1); 
            int temp = list.get(i); 
            list.set(i, (list.get(j))); 
            list.set(j, temp); 
        } 
        return list;
    }
    
}
