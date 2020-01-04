package rank;

import model.card.Card;
import model.card.Hand;
import model.card.type.Suit;
import model.rank.Rank;
import util.OrderList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandRank {
    private List<Card> hand;
    private HashMap<Integer,List<Card>> valueMap;
    private HashMap<Suit,List<Card>> suitMap;
    private RankUtils rankUtils;
    public HandRank(Hand tableHand, Hand userHand) {
        hand = new ArrayList<>();
        hand.addAll(tableHand.getHand());
        hand.addAll(userHand.getHand());
        valueMap = new HashMap<>();
        suitMap = new HashMap<>();
        setSuitMap();
        setValueMap();
        OrderList.sort(hand);
        rankUtils = new RankUtils(valueMap,suitMap,hand);
    }

    public Rank getRankOfHand() {
        return rankUtils.getRankOfHand();
    }

    public void setHand(Hand tableHand, Hand userHand) {
        hand.clear();
        suitMap.clear();
        valueMap.clear();
        List<Card> hand = new ArrayList<>();
        hand.addAll(tableHand.getHand());
        hand.addAll(userHand.getHand());
        OrderList.sort(hand);
        setSuitMap();
        setValueMap();
    }

    private void setSuitMap() {
        for(Card card : hand) {
            if(suitMap.containsKey(card.getSuit())) {
                List<Card> cards = suitMap.get(card.getSuit());
                cards.add(card);
                suitMap.put(card.getSuit(),cards);
            } else {
                List<Card> cards = new ArrayList<>();
                cards.add(card);
                suitMap.put(card.getSuit(),cards);
            }
        }
    }

    private void setValueMap() {
        for(Card card : hand) {
            if(valueMap.containsKey(card.getValue())) {
                List<Card> cards = valueMap.get(card.getValue());
                cards.add(card);
                valueMap.put(card.getValue(),cards);
            } else {
                List<Card> cards = new ArrayList<>();
                cards.add(card);
                valueMap.put(card.getValue(),cards);
            }
        }
    }
    public List<Card> getRankedCards() {
        return rankUtils.getRankedCards();
    }


    private boolean isRoyalFlush(Hand tableHand, Hand userHand) {

        return false;
    }
}
