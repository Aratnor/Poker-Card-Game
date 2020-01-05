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
        canCheck = false;
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
                if(currentBetType == BetType.CHECK || isOneTurnCompleted)
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

    public void call() {
        totalBidOnTable += bidAmount;
        canCheck = false;
        check();
    }

    public void check() {
        if(totalCheck == 0) isOneTurnCompleted = false;

        if(lastBetType == BetType.CHECK && currentBetType == BetType.CALL){
            totalCheck = 1;
            canCheck =true;
        } else{
            totalCheck++;
        }

        int diff = isBidRaised ?
                totalPlayer - foldedPlayers - 1 :
                totalPlayer - foldedPlayers;
        if(totalCheck == diff) {
            totalCheck = 0;
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
    public boolean canUserCheck() {
        return canCheck;
    }


    public void allIn(int chips) {
        bidAmount = chips;
        totalBidOnTable += chips;
        totalCheck = 0;
        isBidRaised = true;
        currentBetType = BetType.ALL_IN;
    }

    public void resetBidTurn() {
        bidTurn = 0;
        bidAmount = ResourceUtils.INITIAL_BID;
    }
    public void raise(int raiseAmount) {
        bidAmount =  bidAmount + raiseAmount;
        totalBidOnTable += bidAmount;
        totalCheck = 0;
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

    public int getBidTurn() {
        return bidTurn;
    }

    public boolean isGameTurnFinished() {
        return bidTurn == 3;
    }
}
