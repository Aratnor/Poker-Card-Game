package model.card;

import model.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> hand;
    private boolean isUserHand;
    private int cardLimit;
    public Hand(boolean isUserHand) {
        this.isUserHand = isUserHand;
        if(isUserHand) cardLimit = 2;
        else cardLimit = 5;
        hand = new ArrayList<>(cardLimit);
    }

    public boolean addCardToHand(Card card) {
        if( hand.size() == cardLimit ) return false;
        hand.add(card);
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Card card : hand){
            builder.append(card.getValue()).append(" ----- ").append(card.getSuit()).append("\n");
        }
        return builder.toString();
    }

    public boolean doesHandFullCard() {
        return hand.size() == cardLimit;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public boolean isUserHand() {
        return isUserHand;
    }

    public void setUserHand(boolean userHand) {
        isUserHand = userHand;
    }
}
