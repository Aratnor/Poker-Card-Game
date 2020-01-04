package ai;

import model.Player;
import model.bet.BetType;
import model.card.Hand;
import model.rank.Rank;
import util.AIUtils;

import java.util.ArrayList;
import java.util.List;

public class AIMovement {
    private Rank handRank;
    private Hand currentRankedHand;
    private int currentTurn;
    private Player currentAiPlayer;
    private BetType currentBetType;
    private List<Integer> bids;

    public AIMovement(Player aiPlayer,Rank handRank, Hand currentRankedHand,int currentBid ,int currentTurn, BetType currentBetType) {
        bids = new ArrayList<>();
        bids.add(currentBid);
        this.handRank = handRank;
        this.currentRankedHand = currentRankedHand;
        this.currentTurn = currentTurn;
        this.currentAiPlayer = aiPlayer;
        this.currentBetType = currentBetType;
    }

    public void decideMove(){
        BetType betType = AIUtils.decideBet(handRank,currentTurn, currentAiPlayer, currentBetType);
        switch (betType) {
            case FOLD:
                currentAiPlayer.foldCurrentTurn();
                break;
            case CALL:
                if(currentAiPlayer.canUserCall()) {
                    currentAiPlayer.call();
                    betType = BetType.CALL;
                }
                else {
                    currentAiPlayer.foldCurrentTurn();
                    betType = BetType.FOLD;
                }
                break;
            case DOUBLE_BID:
                if(currentAiPlayer.canUserBetDoubleBit()) {
                    currentAiPlayer.userDoubleBid();
                    betType = BetType.DOUBLE_BID;
                }
                else if(currentAiPlayer.canUserCall()) {
                    currentAiPlayer.call();
                    betType = BetType.CALL;
                }
                else {
                    currentAiPlayer.foldCurrentTurn();
                    betType = BetType.FOLD;
                }
                break;
            case CHECK:
                if(currentAiPlayer.canUserCheck()) {
                    currentAiPlayer.check();
                    betType = BetType.CHECK;
                }
                else if(currentAiPlayer.canUserCall()) {
                    currentAiPlayer.call();
                    betType = BetType.CALL;
                }
                else {
                    currentAiPlayer.foldCurrentTurn();
                    betType = BetType.FOLD;
                }
                break;
            case RAISE:
                long userChips = currentAiPlayer.getUserChips();
                int raiseAmount = bids.get(bids.size() - 1);
                while((100 * (double)(raiseAmount / userChips)>= 70))
                    raiseAmount++;
                if(currentAiPlayer.canUserRaise(raiseAmount)){
                    currentAiPlayer.raise(raiseAmount);
                    betType = BetType.RAISE;
                } else if(currentAiPlayer.canUserBetDoubleBit()){
                    currentAiPlayer.userDoubleBid();
                    betType = BetType.DOUBLE_BID;
                }  else if(currentAiPlayer.canUserCall()) {
                    currentAiPlayer.call();
                    betType = BetType.CALL;
                } else {
                    currentAiPlayer.foldCurrentTurn();
                    betType = BetType.FOLD;
                }
                break;
        }
        System.out.println("Player : " +currentAiPlayer.toString() +  " \n"+betType +" ...");
    }

}
