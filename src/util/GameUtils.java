package util;

import game.Deal;
import model.Player;
import model.card.Hand;
import model.rank.Rank;
import rank.HandRank;

import java.util.List;

public class GameUtils {

    public static Rank getBestRank(List<Player> playerList, Hand tableHand) {
        Rank bestRank =  Rank.High_Card;
        for(int i = 0; i < playerList.size(); i++) {
            Player realPlayer = playerList.get(i);
            HandRank handRank = new HandRank(tableHand, realPlayer.getPlayerHand());
            Rank rankPlayer = handRank.getRankOfHand();
            if(i == 0){
                bestRank = rankPlayer;
            }
            else if(rankPlayer.getValue() > bestRank.getValue()) {
                bestRank = rankPlayer;
            }
        }
        return bestRank;
    }

    public static void setWinnerWindow(List<Integer> bestRankedHandPositions, List<Hand> bestRankedHands, List<Player> playerList, Rank bestRank,Hand tableHand) {
        System.out.println("Player : " + bestRankedHandPositions.get(0) +  " take the turn .");
        System.out.println("His hand : \n" + playerList.get(bestRankedHandPositions.get(0)).getPlayerHand().toString() + " \ntable :\n" + tableHand.toString());
        System.out.println("Winner cards : ---------- \n" + bestRankedHands.get(0).toString());
        System.out.println("\nBest hand rank : " + bestRank);
        if(bestRankedHandPositions.get(0) == ResourceUtils.REAL_USER_POSITION){
            System.out.println("You win the turn !!!");
        }
        System.out.println("Do u want to continue playing ? y/n");
    }

    public static void setWinnerWindow(int pos,Player winner, Hand tableHand,int bestPosition,List<Hand> bestRankedHands,Rank bestRank){
        System.out.println("Player : " + pos +  " take the turn .");
        System.out.println("His hand : \n" + winner.getPlayerHand().toString() + " \ntable :\n" + tableHand.toString());
        System.out.println("Winner cards : ---------- \n" + bestRankedHands.get(bestPosition).toString());
        System.out.println("\nBest hand rank : " + bestRank);
        if(winner.isRealUser()){
            System.out.println("You win the turn !!!");
        }
        System.out.println("Do u want to continue playing ? y/n");
    }
    public static void userBetScreen(Deal deal, Hand tableHand, Player realPlayer) {
        System.out.println("Table cards \t---------");
        System.out.println(tableHand.toString());
        System.out.println("Your cards \t---------");
        System.out.println(realPlayer.getPlayerHand().toString());
        if(realPlayer.getLastBidAmount() < deal.getBidAmount() && deal.isBidRaised()){
            int diff = realPlayer.getLastBidAmount() - deal.getBidAmount();
            System.out.println("Bid raised : " +  diff + " chips");
        }
        System.out.println("Current bid: " + deal.getBidAmount());
        System.out.println("Current totalChips on Table : " + deal.getTotalBidOnTable());
        System.out.println("Please Enter your bet if u want to raise ...press for : fold: '1' , call: '2' , check: '3' ");
    }
}
