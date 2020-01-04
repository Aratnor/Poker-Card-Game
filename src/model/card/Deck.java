package model.card;

import model.card.type.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck;
    private int lastCardLocation;

    public Deck() {
        deck = new ArrayList<>();
        prepareDeck();
        shuffleDeck();
        lastCardLocation = deck.size() - 1;
    }

    private void prepareDeck() {
        for(int i = 1;i<=13;i++) {
            deck.add(new Card(Suit.CLUB,i));
            deck.add(new Card(Suit.DIAMOND,i));
            deck.add(new Card(Suit.HEART,i));
            deck.add(new Card(Suit.SPADE,i));
        }
    }

    public List<Card> dealCardToUser() {
        List<Card> cards = new ArrayList<>();
        cards.add(getCard());
        cards.add(getCard());
        return cards;
    }

    public List<Card> dealCardToTable() {
        List<Card> cards = new ArrayList<>();
        for(int i = 0;i < 3;i++) {
            cards.add(getCard());
        }
        return cards;
    }

    public void shuffleDeck() {
        if(deck != null && !deck.isEmpty())
        Collections.shuffle(deck);
    }

    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    public boolean doesDeckHaveTwoCard() {
        return deck.size() == 2;
    }

    public Card getCard() {
        if(deck.isEmpty())  new Exception("Deck is empty");
        Card card = deck.get(lastCardLocation);
        deck.remove(lastCardLocation);
        lastCardLocation--;
        return card;
    }

}
