package game;

import model.Player;
import model.card.Deck;
import model.card.Hand;
import model.rank.Rank;
import rank.HandRank;
import rank.RankUtils;
import util.PlayerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Game {
    private int currentPlayer;
    private boolean isGameFinished;
    private int sizeOfPlayers;
    private Deal deal;
    private Deck deck;
    private Hand tableHand;
    private List<Player> playerList;
    private int playerSize;
    private int realPlayerPosition;

    public Game(int sizeOfPlayers,int realPlayerPosition) {
        currentPlayer = 0;
        isGameFinished = false;
        playerSize = sizeOfPlayers;
        this.realPlayerPosition = realPlayerPosition;
        this.sizeOfPlayers = sizeOfPlayers;
        deal = new Deal(sizeOfPlayers);
        deck = new Deck();
        initPlayers();
        initPlayerDeck();
        initTableCards();
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            if(currentPlayer == playerList.size()) currentPlayer = 0;
            if(deal.isOneTurnCompleted && deal.getBidTurn() < 3){
                tableHand.addCardToHand(deck.getCard());
                deal.setOneTurnCompleted(false);
            }
            if(deal.getBidTurn() == 3) {
                Rank bestRank =  Rank.High_Card;
                List<Hand> bestRankedHands = new ArrayList<>();
                List<Integer> bestRankedHandPositions = new ArrayList<>();
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

                for(int i = 0; i< playerList.size(); i++) {
                    Player realPlayer = playerList.get(i);
                    HandRank handRank = new HandRank(tableHand,realPlayer.getPlayerHand());
                    Rank rankPlayer = handRank.getRankOfHand();
                    if(bestRank.getValue() == rankPlayer.getValue()) {
                        Hand hand = new Hand(false);
                        hand.setHand(handRank.getRankedCards());
                        bestRankedHands.add(hand);
                        bestRankedHandPositions.add(i);
                    }
                }

                if(bestRankedHandPositions.size() == 1) {
                    System.out.println("Player : " + bestRankedHandPositions.get(0) +  " take the turn .");
                } else {
                    int bestPosition = 0;
                    Hand bestPositionRankedHand = bestRankedHands.get(bestPosition);
                    for(int i = 0;i<bestRankedHands.size();i++) {
                        int res = RankUtils.
                                compareTwoSameRankedHand(bestRank, bestPositionRankedHand, bestRankedHands.get(i));
                        if(res == -1){
                            bestPositionRankedHand = bestRankedHands.get(i);
                            bestPosition = i;
                        }
                        //TODO En iyi iki el varsa burda kontrol etmek gerekiyor
                    }
                    System.out.println("Player : " +bestPosition +  " take the turn .");
                }
                break;
            }
            if(currentPlayer == realPlayerPosition){
                Player realPlayer = playerList.get(realPlayerPosition);
                System.out.println("Table cards \t---------");
                System.out.println(tableHand.toString());
                System.out.println("Your cards \t---------");
                System.out.println(realPlayer.getPlayerHand().toString());
                System.out.println("Current bid: " + deal.getBidAmount());
                System.out.println("Current totalChips on Table : " + deal.getTotalBidOnTable());
                System.out.println("Please Enter your bet if u want to raise ... fold 1 , call 2 ,");
                int betValue = scanner.nextInt();
                switch (betValue){
                    case 1 :
                        realPlayer.foldCurrentTurn();
                        break;
                    case 2 :
                        realPlayer.call();
                        break;
                    default :
                        realPlayer.raise(betValue);
                }
                currentPlayer++;
            } else {
                System.out.println("Player : " + playerList.get(currentPlayer).toString() +  " \n Call...");
                playerList.get(currentPlayer).call();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentPlayer++;
                }
            }
        }

    public void initTableCards() {
        tableHand = new Hand(false);
        tableHand.setHand(deck.dealCardToTable());
    }

    public void initPlayerDeck() {
        PlayerUtils.providePlayersCard(playerList,deck);

    }

    public void initPlayers() {
        playerList = PlayerUtils.providePlayers(deal,sizeOfPlayers);

    }

}
