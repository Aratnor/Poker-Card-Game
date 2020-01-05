package game;

import ai.AIMovement;
import model.Player;
import model.card.Deck;
import model.card.Hand;
import model.rank.Rank;
import rank.HandRank;
import rank.RankUtils;
import util.GameUtils;
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
            addCardToTable();
            if(deal.getBidTurn() == 3) {
                Rank bestRank = GameUtils.getBestRank(playerList, tableHand);
                List<Hand> bestRankedHands = new ArrayList<>();
                List<Integer> bestRankedHandPositions = new ArrayList<>();

                setBestHands(bestRank, bestRankedHandPositions, bestRankedHands);

                if(bestRankedHandPositions.size() == 1) {
                    Player winner = playerList.get(bestRankedHandPositions.get(0));
                    winner.takeAllChipsOnTable();
                    GameUtils.setWinnerWindow(bestRankedHandPositions,bestRankedHands,playerList,bestRank,tableHand);
                    String res = scanner.next();
                    if(res.equals("n")) break;
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
                    int pos = bestRankedHandPositions.get(bestPosition);
                    Player winner = playerList.get(pos);
                    winner.setUserChips(deal.getTotalBidOnTable());
                    winner.takeAllChipsOnTable();
                    GameUtils.setWinnerWindow(pos, winner, tableHand, bestPosition, bestRankedHands, bestRank);
                    String res = scanner.next();
                    if(res.equals("n")) break;

                }
                deal.resetBidTurn();
                setNewHands();
            }
            if(currentPlayer == realPlayerPosition && !playerList.get(realPlayerPosition).isFold()){
                Player realPlayer = playerList.get(realPlayerPosition);
                GameUtils.userBetScreen(deal, tableHand, realPlayer);
                int betValue = scanner.nextInt();
                switch (betValue){
                    case 1 :
                        realPlayer.foldCurrentTurn();
                        break;
                    case 2 :
                        realPlayer.call();
                        break;
                    case 3:
                        if(realPlayer.canUserCheck()){
                            realPlayer.check();
                        } else {
                            realPlayer.call();
                        }
                    default :
                        realPlayer.raise(betValue);
                }
                currentPlayer++;
            } else {
                if(playerList.get(currentPlayer).isFold()){
                    currentPlayer++;
                    continue;
                }
                Player currentAiPlayer = playerList.get(currentPlayer);
                HandRank handRanker = new HandRank(tableHand, currentAiPlayer.getPlayerHand());
                Rank handRank = handRanker.getRankOfHand();
                Hand rankedHand = new Hand(false);
                rankedHand.setHand(handRanker.getRankedCards());
                AIMovement aiMovement = new AIMovement(currentAiPlayer,handRank,rankedHand,deal.getBidAmount(),deal.getBidTurn(),deal.getCurrentBetType());
                aiMovement.decideMove();
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

    public void setNewHands() {
        tableHand.clearHand();
        for(Player player : playerList){
            player.setFold(false);
            player.getPlayerHand().clearHand();
        }
        initPlayerDeck();
        initTableCards();
    }

    private void setBestHands(Rank bestRank, List<Integer > bestRankedHandPositions, List<Hand> bestRankedHands) {
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
    }

    private void addCardToTable(){
        if(deal.isOneTurnCompleted() && deal.getBidTurn() < 3){
            tableHand.addCardToHand(deck.getCard());
            deal.setOneTurnCompleted(false);
        }
    }

    public void initPlayerDeck() {
        PlayerUtils.providePlayersCard(playerList,deck);

    }

    public void initPlayers() {
        playerList = PlayerUtils.providePlayers(deal,sizeOfPlayers);

    }

}
