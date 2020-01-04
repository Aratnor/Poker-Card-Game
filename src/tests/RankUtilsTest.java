package tests;

import model.card.Card;
import model.card.Hand;
import model.card.type.Suit;
import org.junit.Assert;
import org.junit.Test;
import rank.RankCompare;
import rank.RankUtils;
import util.OrderList;

import java.util.*;

import static org.junit.Assert.*;

public class RankUtilsTest {
    HashMap<Suit,List<Card>> suitListHashMap;


    @Test
    public void testIsRoyalFlush() {
        HashMap<Suit,List<Card>> suitListHashMap = new HashMap<>();
        List<Card> cards = new ArrayList<>();
        for(int i = 9; i <= 13; i++) {
            cards.add(new Card(Suit.CLUB,i));
        }
        suitListHashMap.put(Suit.CLUB,cards);

        Assert.assertEquals(true, isRoyalFlush(suitListHashMap));

        suitListHashMap.clear();

        cards = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            cards.add(new Card(Suit.CLUB,i));
        }
        suitListHashMap.put(Suit.CLUB,cards);

        Assert.assertEquals(false, isRoyalFlush(suitListHashMap));

    }

    @Test
    public void testIsStraightFlush() {
        HashMap<Suit,List<Card>> suitListHashMap = new HashMap<>();
        List<Card> cards = new ArrayList<>();
        for(int i = 9; i <= 13; i++) {
            cards.add(new Card(Suit.CLUB,i));
        }
        suitListHashMap.put(Suit.CLUB,cards);

        Assert.assertEquals( true, isStraightFlush(suitListHashMap));

    }

    @Test
    public void testIsFourOfAKind() {
        HashMap<Integer,List<Card>> cards = new HashMap<>();

        List<Card> cardList = new ArrayList<>();

        for(int i = 0; i< 5; i++) {
            switch (i) {
                case 0 :
                    cardList.add(new Card(Suit.DIAMOND,1));
                    break;
                case 1 :
                    cardList.add(new Card(Suit.CLUB,1));
                    break;
                case 2 :
                    cardList.add(new Card(Suit.HEART,1));
                    break;
                case 3 :
                    cardList.add(new Card(Suit.SPADE, 1));
            }
        }
        cards.put(1,cardList);

        Assert.assertEquals(true,isFourOfAKind(cards));
    }

    @Test
    public void testIsFullHouse() {
        HashMap<Integer,List<Card>> valueMap = new HashMap<>();
        List<Card> twoList = new ArrayList<>();
        List<Card> threeList = new ArrayList<>();
        for(int i = 0; i<5;i++) {
            if(i >= 2)
                threeList.add(new Card(Suit.SPADE,2));
            else
                twoList.add(new Card(Suit.SPADE,1));
        }
        valueMap.put(2,threeList);
        valueMap.put(1,twoList);

        Assert.assertEquals(true,isFullHouse(valueMap));
    }


    public boolean isRoyalFlush(HashMap<Suit,List<Card>> suitMap) {
        Set<Suit> suitKeys = suitMap.keySet();
        for(Suit key : suitKeys) {
            List<Card> suitCards = suitMap.get(key);
            if(suitCards.size() == 5) {
                boolean[] orderedSuit = new boolean[5];
                for(Card card : suitCards) {
                    switch (card.getValue()) {
                        case 13 :
                            orderedSuit[0] = true;
                            break;
                        case 12 :
                            orderedSuit[1] = true;
                            break;
                        case 11 :
                            orderedSuit[2] = true;
                            break;
                        case 10 :
                            orderedSuit[3] = true;
                        case 9 :
                            orderedSuit[4] = true;
                    }
                }
                for(boolean val : orderedSuit) if(!val) return false;
                return true;
            }
        }
        return false;
    }

    private boolean isStraightFlush(HashMap<Suit, List<Card>> suitMap) {
        Set<Suit> cardListBySuit = suitMap.keySet();
        for(Suit key : cardListBySuit) {
            List<Card> cards = suitMap.get(key);
            if( cards.size() == 5 ) {
                OrderList.sort(cards);
                int diff = cards.get(cards.size() - 1).getValue() - cards.get(0).getValue();
                if(diff == 4) return  true;
            }
        }
        return false;
    }

    private boolean isFourOfAKind(HashMap<Integer, List<Card>> valueMap) {
        Set<Integer> keysByValue =  valueMap.keySet();

        for(Integer key : keysByValue) {
            List<Card> cards = valueMap.get(key);
            if(cards.size() == 4) return true;
        }
        return false;
    }

    private boolean isFullHouse(HashMap<Integer,List<Card>> valueMap) {
        Set<Integer> valueKeys = valueMap.keySet();
        boolean[] doesFullHouse = new boolean[2];

        for(Integer key : valueKeys) {
            List<Card> cards = valueMap.get(key);
            switch (cards.size()) {
                case 2 :
                    doesFullHouse[0] = true;
                    break;
                case 3 :
                    doesFullHouse[1] = true;
                    break;
            }
        }
        for(boolean val : doesFullHouse) if(!val) return false;
        return true;
    }

}