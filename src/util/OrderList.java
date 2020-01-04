package util;

import model.card.Card;

import java.util.List;

public class OrderList {
    public static void sort(List<Card> cards) {
        for(int i = 0;i<cards.size() -1;i++) {
            int position = i;
            for(int j = i+1;j<cards.size();j++) {
                if(cards.get(position).getValue() > cards.get(j).getValue())
                    position = j;
            }
            Card temp = cards.get(i);
            cards.set(i,cards.get(position));
            cards.set(position,temp);
        }
    }

}
