package game;

import model.bet.BetType;
import tests.DealTest;
import util.ErrorUtils;
import util.ResourceUtils;

public class Deal {
    private int bidAmount;
    private int totalBidOnTable;
    private int totalCheck;
    private int totalPlayer;
    private int bidTurn = 0;
    private int foldedPlayers = 0;
    private BetType currentBetType;
    private BetType lastBetType;

    private boolean canCheck;
    private boolean isBidRaised;
    private boolean isOneTurnCompleted;

    public Deal(int playerSize) {
        bidAmount = ResourceUtils.INITIAL_BID;
        totalBidOnTable = 0;
        isBidRaised = false;
        totalCheck = 0;
        totalPlayer = 0;
        isOneTurnCompleted = false;
        this.totalPlayer = playerSize;
        canCheck = true;
        currentBetType = BetType.CALL;
    }

    public void makeBid(BetType betType,int chips){
        lastBetType = currentBetType;
        currentBetType = betType;
        switch (betType){
            case RAISE:
                raise(chips);
                break;
            case ALL_IN:
                allIn(chips);
                break;
                default:
                    throw ErrorUtils.getIllegalDealException(betType,currentBetType,isOneTurnCompleted);
        }
    }
    public void makeBid(BetType betType){
        lastBetType = currentBetType;
        currentBetType = betType;
        switch (betType) {
            case CHECK:
                if(canUserCheck())
                check();
                else throw ErrorUtils.getIllegalDealException(betType,currentBetType,isOneTurnCompleted);
                break;
            case DOUBLE_BID:
                doubleBid();
                break;
            case FOLD:
                fold();
                break;
            case CALL:
                call();
                break;
        }
    }

    public void setOneTurnCompleted(boolean value) {
        isOneTurnCompleted = false;
    }

    public boolean canUserCheck() {
        if ( bidTurn == 0 || isBidRaised) return false;
        else if(lastBetType == BetType.CALL && totalCheck == totalPlayer) return true;
        else return true;
    }

    public void call() {
        totalBidOnTable += bidAmount;
        check();
    }

    public void check() {
        if(totalCheck == totalPlayer) totalCheck = 0;
        if(totalCheck == 0) isOneTurnCompleted = false;

        if(lastBetType == BetType.CHECK && currentBetType == BetType.CALL){
            totalCheck = 1;
            canCheck =false;
        }
        else{
            totalCheck++;
        }

        int diff = foldedPlayers == 0 ?
                totalPlayer :
                totalPlayer - foldedPlayers;
        if(totalCheck == diff) {
            isOneTurnCompleted = true;
            bidTurn++;
            canCheck = true;
            isBidRaised = false;
        }
    }

    public void clearTotalBidOnTable() {
        totalBidOnTable = 0;
    }

    public void fold() {
        foldedPlayers++;
    }

    public int getTotalCheck() {
        return totalCheck;
    }

    public int getFoldedPlayerNumber() {
        return foldedPlayers;
    }

    public void doubleBid() {
        currentBetType = BetType.DOUBLE_BID;
        bidAmount = bidAmount * 2;
        totalBidOnTable += bidAmount;
        isBidRaised = true;
        canCheck = false;
        totalCheck = 0;
    }
    /*public boolean canUserCheck() {
        if(bidTurn != 0 && totalCheck == 0)
            return true;
        else
            return canCheck;
    }*/


    public void allIn(int chips) {
        bidAmount = chips;
        totalBidOnTable += chips;
        totalCheck = 0;
        isBidRaised = true;
        currentBetType = BetType.ALL_IN;
    }

    public void resetBidTurn() {
        bidTurn = 0;
        isBidRaised = false;
        totalCheck = 0;
        totalBidOnTable = 0;
        bidAmount = ResourceUtils.INITIAL_BID;
    }
    public void raise(int raiseAmount) {
        bidAmount =  bidAmount + raiseAmount;
        totalBidOnTable += bidAmount;
        if(!isBidRaised && totalCheck == 0)
        totalCheck++;
        else {
            totalCheck = 0;
        }
        isBidRaised = true;
        currentBetType = BetType.RAISE;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public boolean isOneTurnCompleted() {
        return isOneTurnCompleted;
    }

    public BetType getCurrentBetType() {
        return currentBetType;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public int getTotalBidOnTable() {
        return totalBidOnTable;
    }
    public void setBidTurn(int val){
        bidTurn = val;
    }

    public int getBidTurn() {
        return bidTurn;
    }

    public boolean isBidRaised() {
        return isBidRaised;
    }

    public boolean isGameTurnFinished() {
        return bidTurn == 3;
    }
}
