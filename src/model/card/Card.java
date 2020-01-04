package model.card;

import model.card.type.Suit;

public class Card {
    private int value;
    private Suit suit;

    public Card(Suit suit, int value) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }
}
