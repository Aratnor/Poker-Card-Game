package rank;

import model.card.Hand;
import model.rank.Rank;

public class RankCompare {

    public static int compareFullHouse(Hand ranked1, Hand ranked2) {
        int [] values = getFullHouseRankedCardValues(ranked1, ranked2);
        if(values[0] > values[2]) return 1;
        else if(values[0] < values[2]) return -1;
        else if(values[1] > values[3]) return 1;
        else if(values[1] < values[3]) return -1;
        else return 0;
    }

    public static int compareFourOfAKind(Hand ranked1, Hand ranked2) {
        return Integer.compare(ranked1.getHand().get(0).getValue(), ranked2.getHand().get(0).getValue());
    }

    public static int compareTwoPair(Hand ranked1, Hand ranked2) {
        int val = Integer.compare(ranked1.getHand().get(3).getValue(),ranked2.getHand().get(3).getValue());
        return  val != 0 ? val : Integer.compare(ranked1.getHand().get(0).getValue(),ranked2.getHand().get(0).getValue());
    }

    public static int compareThreeOfAKind(Hand ranked1, Hand ranked2) {
        return Integer.compare(ranked1.getHand().get(0).getValue(), ranked2.getHand().get(0).getValue());
    }

    public static int compareFlush(Hand ranked1, Hand ranked2) {
        for(int i = ranked1.getHand().size() -1; i>= 0;i--) {
            if(ranked1.getHand().get(i).getValue() > ranked2.getHand().get(i).getValue())return 1;
            else if(ranked1.getHand().get(i).getValue() < ranked2.getHand().get(i).getValue())return -1;
        }
        return 0;
    }
    public static int compareTwoRank(Rank rank1, Rank rank2) {
        return Integer.compare(rank1.getValue(), rank2.getValue());
    }

    public static int compareOnePair(Hand ranked1, Hand ranked2) {
        return Integer.compare(ranked1.getHand().get(0).getValue(),ranked2.getHand().get(0).getValue());
    }

    public static int compareHighestCard(Hand ranked1, Hand ranked2) {
        return Integer.compare(ranked1.getHand().get(0).getValue(),ranked2.getHand().get(0).getValue());
    }

    public static int compareStraight(Hand ranked1, Hand ranked2) {
        for(int i = ranked1.getHand().size() -1; i>= 0;i--) {
            if(ranked1.getHand().get(i).getValue() > ranked2.getHand().get(i).getValue())return 1;
            else if(ranked1.getHand().get(i).getValue() < ranked2.getHand().get(i).getValue())return -1;
        }
        return 0;
    }

    public static int compareStraightFlush(Hand ranked1, Hand ranked2) {
        return Integer.compare(ranked1.getHand().get(0).getValue(), ranked2.getHand().get(0).getValue());
    }

    //dönüş değeri index -> 0(ilk el 3lünün değeri), 1(ilk el 2linin değeri), 2(ikinci el 3lünün değeri), 3(ikinci el 2linin değeri)
    private static int[] getFullHouseRankedCardValues(Hand ranked1, Hand ranked2) {
        int tripleCardValue1;
        int dualCardValue1;
        int tripleCardValue2;
        int dualCardValue2;
        if(ranked1.getHand().get(0).getValue() == ranked1.getHand().get(2).getValue()){
            tripleCardValue1 = ranked1.getHand().get(0).getValue();
            dualCardValue1 = ranked1.getHand().get(3).getValue();
        } else {
            tripleCardValue1 = ranked1.getHand().get(3).getValue();
            dualCardValue1 = ranked1.getHand().get(0).getValue();
        }
        if(ranked2.getHand().get(0).getValue() == ranked2.getHand().get(2).getValue()){
            tripleCardValue2 = ranked2.getHand().get(0).getValue();
            dualCardValue2 = ranked2.getHand().get(3).getValue();
        } else {
            tripleCardValue2 = ranked2.getHand().get(3).getValue();
            dualCardValue2 = ranked2.getHand().get(0).getValue();
        }
        return new int[]{tripleCardValue1,dualCardValue1,tripleCardValue2,dualCardValue2};
    }
}
