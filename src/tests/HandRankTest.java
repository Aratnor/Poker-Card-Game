package tests;

import model.card.Card;
import model.card.Deck;
import model.card.Hand;
import model.card.type.Suit;
import model.rank.Rank;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rank.HandRank;
import rank.RankUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HandRankTest {
    Deck deck;
    Hand userHand;
    Hand aiHand;
    Hand tableHand;


    @Before
    public void prepareCards() {
    }

    @Test
    public void testHandRanks() {
        HandRank userRankUtils = new HandRank(userHand,tableHand);
        HandRank aiRankUtils = new HandRank(aiHand,tableHand);
        Rank userHandRank = userRankUtils.getRankOfHand();
        Rank aiHandRank = aiRankUtils.getRankOfHand();
        System.out.println("\nUser Hand Rank : "+ userHandRank.toString() + " " +userHandRank.getValue() +
                "\nAI Hand Rank :"+aiHandRank.toString()+ " " + aiHandRank.getValue() );

    }

    @Test
    public void testRanksWithStraightFlushRoyalFlush() {
        Hand userHand = new Hand(false);
        userHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,9),
                new Card(Suit.SPADE,10),
                new Card(Suit.SPADE,11),
                new Card(Suit.SPADE,12),
                new Card(Suit.SPADE,13))));
        Hand aiHand = new Hand(false);
        aiHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,6),
                new Card(Suit.SPADE,7),
                new Card(Suit.SPADE,8),
                new Card(Suit.SPADE,9))));
        HandRank handRank= new HandRank(new Hand(false),userHand);
        HandRank aiHandRank = new HandRank(new Hand(false),aiHand);

        Rank userRank = handRank.getRankOfHand();
        Rank aiRank = aiHandRank.getRankOfHand();

        List<Card> userRankedCards = handRank.getRankedCards();
        System.out.println("\nUser Ranked Cards : -----------------");
        for(Card card : userRankedCards) {
            System.out.print("suit: " + card.getSuit().toString() + " value: " + card.getValue());
        }
        System.out.println("\n------------------");

        System.out.println("User rank : " + userRank.toString() + " " + userRank.getValue());
        System.out.println("Ai rank : " + aiRank.toString() + " " + aiRank.getValue());

        Assert.assertEquals(Rank.Royal_Flush,userRank);
        Assert.assertEquals(Rank.Straight_Flush,aiRank);

    }

    @Test
    public void testRanksWithFourKindFullHouse() {
        Hand userHand = new Hand(false);
        userHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.HEART,9),
                new Card(Suit.DIAMOND,9),
                new Card(Suit.CLUB,9),
                new Card(Suit.SPADE,9),
                new Card(Suit.SPADE,13))));
        Hand aiHand = new Hand(false);
        aiHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.HEART,5),
                new Card(Suit.DIAMOND,5),
                new Card(Suit.CLUB,5),
                new Card(Suit.HEART,3),
                new Card(Suit.SPADE,3))));
        HandRank handRank= new HandRank(new Hand(false),userHand);
        HandRank aiHandRank = new HandRank(new Hand(false),aiHand);

        Rank userRank = handRank.getRankOfHand();
        Rank aiRank = aiHandRank.getRankOfHand();

        System.out.println("User rank : " + userRank.toString() + " " + userRank.getValue());
        System.out.println("Ai rank : " + aiRank.toString() + " " + aiRank.getValue());

        Assert.assertEquals(Rank.Four_of_a_kind,userRank);
        Assert.assertEquals(Rank.Full_House,aiRank);
    }

    @Test
    public void testRankWithFlushStraight() {
        Hand userHand = new Hand(false);
        userHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.SPADE,8),
                new Card(Suit.SPADE,9),
                new Card(Suit.SPADE,7),
                new Card(Suit.CLUB,4),
                new Card(Suit.SPADE,2))));
        Hand aiHand = new Hand(false);
        aiHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.CLUB,5),
                new Card(Suit.DIAMOND,6),
                new Card(Suit.SPADE,7),
                new Card(Suit.SPADE,8),
                new Card(Suit.CLUB,4),
                new Card(Suit.SPADE,2),
                new Card(Suit.SPADE,9))));
        HandRank handRank= new HandRank(new Hand(false),userHand);
        HandRank aiHandRank = new HandRank(new Hand(false),aiHand);

        Rank userRank = handRank.getRankOfHand();
        Rank aiRank = aiHandRank.getRankOfHand();

        System.out.println("User rank : " + userRank.toString() + " " + userRank.getValue());
        System.out.println("Ai rank : " + aiRank.toString() + " " + aiRank.getValue());

        Assert.assertEquals(Rank.Flush,userRank);
        Assert.assertEquals(Rank.Straight,aiRank);
    }

    @Test
    public void testThreeKindTwoPair() {
        Hand userHand = new Hand(false);
        userHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.DIAMOND,5),
                new Card(Suit.CLUB,5),
                new Card(Suit.SPADE,7),
                new Card(Suit.CLUB,4),
                new Card(Suit.SPADE,2))));
        Hand aiHand = new Hand(false);
        aiHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.CLUB,4),
                new Card(Suit.DIAMOND,6),
                new Card(Suit.SPADE,5),
                new Card(Suit.CLUB,6),
                new Card(Suit.CLUB,4),
                new Card(Suit.HEART,2),
                new Card(Suit.SPADE,9))));
        HandRank handRank= new HandRank(new Hand(false),userHand);
        HandRank aiHandRank = new HandRank(new Hand(false),aiHand);

        Rank userRank = handRank.getRankOfHand();
        Rank aiRank = aiHandRank.getRankOfHand();

        System.out.println("User rank : " + userRank.toString() + " " + userRank.getValue());
        System.out.println("Ai rank : " + aiRank.toString() + " " + aiRank.getValue());

        Assert.assertEquals(Rank.Three_of_a_kind,userRank);
        Assert.assertEquals(Rank.Two_Pair,aiRank);
    }

    @Test
    public void testOnePairHighCard() {
        Hand userHand = new Hand(false);
        userHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.SPADE,5),
                new Card(Suit.DIAMOND,5),
                new Card(Suit.CLUB,1),
                new Card(Suit.SPADE,7),
                new Card(Suit.CLUB,4),
                new Card(Suit.SPADE,2))));
        Hand aiHand = new Hand(false);
        aiHand.setHand(new ArrayList<>(Arrays.asList(
                new Card(Suit.CLUB,3),
                new Card(Suit.DIAMOND,7),
                new Card(Suit.SPADE,1),
                new Card(Suit.CLUB,10),
                new Card(Suit.CLUB,9),
                new Card(Suit.HEART,2),
                new Card(Suit.SPADE,4))));
        HandRank handRank= new HandRank(new Hand(false),userHand);
        HandRank aiHandRank = new HandRank(new Hand(false),aiHand);

        Rank userRank = handRank.getRankOfHand();
        Rank aiRank = aiHandRank.getRankOfHand();

        System.out.println("User rank : " + userRank.toString() + " " + userRank.getValue());
        System.out.println("Ai rank : " + aiRank.toString() + " " + aiRank.getValue());

        Assert.assertEquals(Rank.One_Pair,userRank);
        Assert.assertEquals(Rank.High_Card,aiRank);
    }

}