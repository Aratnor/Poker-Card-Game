package rank;

import model.card.Card;
import model.card.Hand;
import model.card.type.Suit;
import model.rank.Rank;
import util.OrderList;

import java.util.*;

public class RankUtils {
    private List<Card> hand;
    private HashMap<Integer, List<Card>> valueMap;
    private HashMap<Suit, List<Card>> suitMap;
    private List<Card> rankedCards;

    public RankUtils(HashMap<Integer, List<Card>> valueMap, HashMap<Suit, List<Card>> suitMap, List<Card> hand) {
        rankedCards = new ArrayList<>();
        this.valueMap = valueMap;
        this.suitMap = suitMap;
        this.hand = hand;
    }

    public Rank getRankOfHand() {
        if(isRoyalFlush()) {
            return Rank.Royal_Flush;
        } else if(isStraightFlush()) {
            return  Rank.Straight_Flush;
        } else if(isFourOfAKind()) {
            return Rank.Four_of_a_kind;
        } else if(isFullHouse()) {
            return Rank.Full_House;
        } else if(isFlush()) {
            return Rank.Flush;
        } else if(isStraight()) {
            return Rank.Straight;
        } else if(isThreeOfAKind()) {
            return Rank.Three_of_a_kind;
        } else if(isTwoPair()) {
            return Rank.Two_Pair;
        } else if(isOnePair()) {
            return Rank.One_Pair;
        }
        else return Rank.High_Card;
    }
    public static int compareTwoRank(Rank rank1, Rank rank2) {
        return RankCompare.compareTwoRank(rank1, rank2);
    }

    // int dönüş değerleri : 1 -> ranked1 daha üstün, 0 -> ikisinin değeride eşit, -1 -> ranked 2 daha üstün
    public static int compareTwoSameRankedHand(Rank sameRank,Hand ranked1, Hand ranked2) {
        switch (sameRank) {
            case Royal_Flush:
                return 0;
            case Straight_Flush:
                return RankCompare.compareStraightFlush(ranked1, ranked2);
            case Four_of_a_kind:
                return RankCompare.compareFourOfAKind(ranked1, ranked2);
            case Full_House:
                return RankCompare.compareFullHouse(ranked1, ranked2);
            case Flush:
                return RankCompare.compareFlush(ranked1, ranked2);
            case Straight:
                return RankCompare.compareStraight(ranked1, ranked2);
            case Three_of_a_kind:
                return RankCompare.compareThreeOfAKind(ranked1, ranked2);
            case Two_Pair:
                return RankCompare.compareTwoPair(ranked1, ranked2);
            case One_Pair:
                return RankCompare.compareOnePair(ranked1, ranked2);
            case High_Card:
                return RankCompare.compareHighestCard(ranked1, ranked2);
        }
        return 0;
    }


    private boolean isRoyalFlush() {
        Set<Suit> suitKeys = suitMap.keySet();
        for(Suit key : suitKeys) {
            List<Card> suitCards = suitMap.get(key);
            if(suitCards.size() == 5) {
                boolean[] orderedSuit = new boolean[5];
                Card [] rankedCards = new Card[5];
                for(Card card : suitCards) {
                    switch (card.getValue()) {
                        case 13 :
                            orderedSuit[0] = true;
                            rankedCards[0] = card;
                            break;
                        case 12 :
                            orderedSuit[1] = true;
                            rankedCards[1] = card;
                            break;
                        case 11 :
                            orderedSuit[2] = true;
                            rankedCards[2] = card;
                            break;
                        case 10 :
                            orderedSuit[3] = true;
                            rankedCards[3] = card;
                            break;
                        case 9 :
                            orderedSuit[4] = true;
                            rankedCards[4] = card;
                            break;
                    }
                }
               for(boolean val : orderedSuit) if(!val) return false;
               this.rankedCards.clear();
               this.rankedCards.addAll(Arrays.asList(rankedCards));
               return true;
            }
        }
        return false;
    }

    private boolean isStraightFlush() {
       Set<Suit> cardListBySuit = suitMap.keySet();
       for(Suit key : cardListBySuit) {
           List<Card> cards = suitMap.get(key);
           if( cards.size() == 5 ) {
               OrderList.sort(cards);
               return isOrdered(cards);
           }
       }
        return false;
    }

    private boolean isFourOfAKind() {
        Set<Integer> keysByValue =  valueMap.keySet();

        for(Integer key : keysByValue) {
            List<Card> cards = valueMap.get(key);
            if(cards.size() == 4) {
                this.rankedCards.clear();
                this.rankedCards.addAll(cards);
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse() {
        Set<Integer> valueKeys = valueMap.keySet();
        boolean[] doesFullHouse = new boolean[2];
        Card[] rankedCards = new Card[5];

        for(Integer key : valueKeys) {
            List<Card> cards = valueMap.get(key);
            switch (cards.size()) {
                case 2 :
                    doesFullHouse[0] = true;
                    rankedCards[0] = cards.get(0);
                    rankedCards[1] = cards.get(1);
                    break;
                case 3 :
                    doesFullHouse[1] = true;
                    rankedCards[2] = cards.get(0);
                    rankedCards[3] = cards.get(1);
                    rankedCards[4] = cards.get(2);
                    break;
            }
        }
        for(boolean val : doesFullHouse) if(!val) return false;
        this.rankedCards.clear();
        this.rankedCards.addAll(Arrays.asList(rankedCards));
        return true;
    }

    private boolean isFlush() {
        Set<Suit> suitKeys = suitMap.keySet();
        for(Suit suit : suitKeys) {
            List<Card> cards = suitMap.get(suit);
            if(cards.size() == 5) {
                this.rankedCards.clear();
                this.rankedCards.addAll(cards);
                return true;
            }
        }
        return false;
    }
    private boolean isOrdered(List<Card> cards) {
        int count = 0;
        Card[] rankedCards = new Card[5];
        for(int i = 0;i<cards.size()-1;i++) {
            if(count == 5) return true;
            int diff =cards.get(i + 1).getValue() - cards.get(i).getValue();
            if(diff == 1){
                rankedCards[count] = cards.get(i);
                count++;
                if (i == cards.size() - 2) {
                    rankedCards[count] = cards.get(cards.size() - 1);
                    count++;
                }
            } else
                count = 0;
        }

        if(count == 5) {
            this.rankedCards.clear();
            this.rankedCards.addAll(Arrays.asList(rankedCards));
            return true;
        }
        else return false;
    }

    private boolean isStraight() {
        if(hand.size() == 5) {
            return isOrdered(hand);
        }
        return isOrdered(hand);
    }

    private boolean isThreeOfAKind() {
        Set<Integer> valueKeys = valueMap.keySet();
        for(Integer key : valueKeys) {
            List<Card> cards = valueMap.get(key);
            if(cards.size() == 3){
                this.rankedCards.clear();
                this.rankedCards.addAll(cards);
                return true;
            }
        }
        return false;
    }

    private boolean isTwoPair() {
        Set<Integer> valueKeys = valueMap.keySet();
        boolean[] isTwoPair = new boolean[2];
        Card[] rankedCards = new Card[4];
        int pos = 0;
        int index = 0;
        for(Integer key : valueKeys) {
            List<Card> cards = valueMap.get(key);
            if(cards.size() == 2 && index <= 1) {
                rankedCards[pos++] = cards.get(0);
                rankedCards[pos++] = cards.get(1);
                isTwoPair[index] = true;
                index++;
            }
        }
        for(boolean val : isTwoPair) if(!val) return false;
        this.rankedCards.clear();
        this.rankedCards.addAll(Arrays.asList(rankedCards));
        return true;
    }

    public List<Card> getRankedCards(){
        OrderList.sort(rankedCards);
        return rankedCards;
    }

    private boolean isOnePair() {
        Set<Integer> vals = valueMap.keySet();
        for(Integer key : vals) {
            List<Card> cards = valueMap.get(key);
            if(cards.size() == 2) {
                this.rankedCards.clear();
                this.rankedCards.addAll(cards);
                return true;
            }
        }
        return false;
    }

}
