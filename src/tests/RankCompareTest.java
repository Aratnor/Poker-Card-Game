package tests;

import model.card.Card;
import model.card.Hand;
import model.card.type.Suit;
import model.rank.Rank;
import org.junit.Assert;
import org.junit.Test;
import rank.RankCompare;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RankCompareTest {


    @Test
    public void compareStraightFlush() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,3),
                new Card(Suit.SPADE,4),
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,6),
                new Card(Suit.SPADE,7)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,8),
                new Card(Suit.SPADE,9),
                new Card(Suit.SPADE,10),
                new Card(Suit.SPADE,11),
                new Card(Suit.SPADE,12)
        )));

        Assert.assertEquals(-1,RankCompare.compareStraightFlush(ranked1, ranked2));
    }

    @Test
    public void compareFourOfAKind() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,4),
                new Card(Suit.SPADE,4),
                new Card(Suit.SPADE,4),
                new Card(Suit.SPADE,4)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,5)
        )));

        Assert.assertEquals(-1,RankCompare.compareFourOfAKind(ranked1,ranked2));
    }

    @Test
    public void compareFullHouse() {
        Hand userHand = new Hand(false);
        userHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,4),
                new Card(Suit.SPADE,4),
                new Card(Suit.SPADE,8),
                new Card(Suit.SPADE,8),
                new Card(Suit.SPADE,8)
        )));
        Hand aiHand = new Hand(false);
        aiHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,7),
                new Card(Suit.SPADE,7),
                new Card(Suit.SPADE,7),
                new Card(Suit.SPADE,9),
                new Card(Suit.SPADE,9)
        )));
        Assert.assertEquals(1, RankCompare.compareFullHouse(userHand,aiHand));
    }

    @Test
    public void compareFlush() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,3),
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,9),
                new Card(Suit.SPADE,10),
                new Card(Suit.SPADE,12)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,3),
                new Card(Suit.SPADE,6),
                new Card(Suit.SPADE,8),
                new Card(Suit.SPADE,10),
                new Card(Suit.SPADE,12)
        )));

        Assert.assertEquals(1,RankCompare.compareFlush(ranked1, ranked2));
    }

    @Test
    public void compareStraight() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,4),
                new Card(Suit.DIAMOND,5),
                new Card(Suit.CLUB,6),
                new Card(Suit.SPADE,7),
                new Card(Suit.SPADE,8)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,6),
                new Card(Suit.SPADE,7),
                new Card(Suit.DIAMOND,8),
                new Card(Suit.HEART,9),
                new Card(Suit.CLUB,10)
        )));

        Assert.assertEquals(-1,RankCompare.compareStraight(ranked1,ranked2));
    }

    @Test
    public void compareThreeOfAKind() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,3),
                new Card(Suit.DIAMOND,3),
                new Card(Suit.CLUB,3)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,5),
                new Card(Suit.DIAMOND,5)
        )));

        Assert.assertEquals(-1,RankCompare.compareThreeOfAKind(ranked1,ranked2));
    }

    @Test
    public void compareTwoPair() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,2),
                new Card(Suit.DIAMOND,2),
                new Card(Suit.CLUB,4),
                new Card(Suit.SPADE,4)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,3),
                new Card(Suit.DIAMOND,3),
                new Card(Suit.CLUB,7),
                new Card(Suit.SPADE,7)
        )));

        Assert.assertEquals(-1,RankCompare.compareTwoPair(ranked1, ranked2));
    }

    @Test
    public void compareOnePair() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,3),
                new Card(Suit.DIAMOND,3)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,5)
        )));

        Assert.assertEquals(-1, RankCompare.compareOnePair(ranked1, ranked2));
    }

    @Test
    public void compareHighestCard() {
        Hand ranked1 = new Hand(false);
        ranked1.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5)
        )));
        Hand ranked2 = new Hand(false);
        ranked2.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,12)
        )));

        Assert.assertEquals(-1, RankCompare.compareHighestCard(ranked1, ranked2));
    }

    @Test
    public void compareTwoRank() {
        Assert.assertEquals(-1,RankCompare.compareTwoRank(Rank.Full_House,Rank.Four_of_a_kind));
    }
}