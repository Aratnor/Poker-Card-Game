package tests;

import game.Deal;
import model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DealTest {
    List<Player> players;
    Deal deal;
    Player testPlayer;
    Player testPlayer2;

    @Before
    public void preparePlayers() {
        players = new ArrayList<>();
        deal = new Deal(5);
        for(int i = 0;i<5;i++) {
            players.add(new Player(500,deal));
        }
        testPlayer = players.get(0);
        testPlayer2 = players.get(1);
    }

    @Test
    public void testFold() {
        testPlayer.foldCurrentTurn();
        testPlayer2.foldCurrentTurn();

        Assert.assertEquals(2, deal.getFoldedPlayerNumber());
    }

    @Test
    public void testCheck() {
        deal.setBidTurn(1);
        testPlayer.check();
        testPlayer2.check();

        Assert.assertEquals(2, deal.getTotalCheck());
    }

    @Test
    public void testCallTotalBidOnTable() {
        deal.setBidAmount(50);
        long totalChipP1 = testPlayer.getUserChips();
        long totalChipP2 = testPlayer2.getUserChips();

        testPlayer.call();
        testPlayer2.call();

        Assert.assertEquals(100,deal.getTotalBidOnTable());
    }

    @Test
    public void testCallUserTotalChips() {
        deal.setBidAmount(50);
        long totalChipP1 = testPlayer.getUserChips();
        long totalChipP2 = testPlayer2.getUserChips();

        testPlayer.call();
        testPlayer2.call();

        Assert.assertEquals(450, testPlayer.getUserChips());
        Assert.assertEquals(450, testPlayer2.getUserChips());
    }

    @Test
    public void testRaiseWithFinishedTurn() {
        deal = new Deal(2);
        deal.setBidAmount(50);
        testPlayer = new Player(500,deal);
        testPlayer2 = new Player(500,deal);
        testPlayer.raise(50);
        testPlayer2.call();

        Assert.assertEquals(2,deal.getTotalCheck());
        Assert.assertEquals(200,deal.getTotalBidOnTable());
        Assert.assertEquals(400,testPlayer.getUserChips());
        Assert.assertEquals(100,deal.getBidAmount());
        Assert.assertTrue(deal.isOneTurnCompleted());
        Assert.assertEquals(1,deal.getBidTurn());
    }

    @Test
    public void testRaiseWithoutFinish() {
        deal = new Deal(2);
        deal.setBidAmount(50);
        testPlayer = new Player(500,deal);
        testPlayer2 = new Player(500,deal);
        testPlayer.raise(50);
        testPlayer2.raise(100);

        Assert.assertEquals(0,deal.getTotalCheck());
        Assert.assertEquals(300,deal.getTotalBidOnTable());
        Assert.assertEquals(200,deal.getBidAmount());
        Assert.assertFalse(deal.isOneTurnCompleted());
        Assert.assertEquals(0,deal.getBidTurn());
    }

    @Test
    public void testAllCallInOneTurn() {
        boolean isTurCompleted = deal.isOneTurnCompleted();

        Assert.assertFalse(isTurCompleted);
        for(Player player : players){
            player.call();
        }
        isTurCompleted = deal.isOneTurnCompleted();
        Assert.assertTrue(isTurCompleted);
        for(int i = 0;i<players.size()-1;i++) {
            players.get(i).call();
            isTurCompleted = deal.isOneTurnCompleted();
            Assert.assertFalse(isTurCompleted);
        }
        Assert.assertFalse(isTurCompleted);
        players.get(players.size() - 1).call();
        isTurCompleted = deal.isOneTurnCompleted();
        Assert.assertTrue(isTurCompleted);
    }

    @Test
    public void testAllCheckInOneTurn() {
        deal.setBidTurn(1);
        boolean isTurnFinished = deal.isOneTurnCompleted();
        Assert.assertFalse(isTurnFinished);
        for(int i = 0;i<players.size();i++){
            isTurnFinished = deal.isOneTurnCompleted();
            Assert.assertFalse(isTurnFinished);
            players.get(i).check();
        }
        isTurnFinished = deal.isOneTurnCompleted();
        Assert.assertTrue(isTurnFinished);
    }


     //case ->5 user ,1 check ,4 call,turn should not finish
    @Test
    public void testOneCheckOtherCallLoop() {
        deal.setBidTurn(1);
        boolean isTurnFinished = deal.isOneTurnCompleted();
        Assert.assertFalse(isTurnFinished);
        for(int i = 0;i<players.size();i++) {
            if( i == 0 ){
                players.get(i).check();
            } else{
                players.get(i).call();
                int totalCheck = deal.getTotalCheck();
                System.out.println("Total check :" + totalCheck + "i :" + i);
            }
            isTurnFinished = deal.isOneTurnCompleted();
            Assert.assertFalse(isTurnFinished);
        }
        isTurnFinished = deal.isOneTurnCompleted();
        Assert.assertFalse(isTurnFinished);
        players.get(0).check();
        isTurnFinished = deal.isOneTurnCompleted();
        Assert.assertTrue(isTurnFinished);
    }

    @Test
    public void testCheckAfterCall(){
        for(Player player : players){
            player.call();
        }

        boolean canCheck = deal.canUserCheck();
        boolean isOneTurnFinished = deal.isOneTurnCompleted();
        Assert.assertTrue(canCheck);
        Assert.assertTrue(isOneTurnFinished);


    }

    @Test
    public void testCheckAfterOneTurnFinished(){
        for(Player player : players){
            player.call();
        }
        players.get(0).call();
        int totalCheck = deal.getTotalCheck();
        Assert.assertEquals(1,totalCheck);
    }

    @Test
    public void testTotalCheckOnTurnFinished(){
        for(Player player : players){
            player.call();
        }
        int totalCheck = deal.getTotalCheck();
        Assert.assertEquals(5,totalCheck);
    }

}