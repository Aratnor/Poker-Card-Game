package game;

import model.bet.BetType;
import util.BidUtils;

public class Deal {
    int bidAmount;
    int totalBidOnTable;
    int totalCheck;
    int totalPlayer;
    int bidTurn = 0;
    int foldedPlayers = 0;
    BetType currentBetType;

    boolean canCheck;
    boolean isBidRaised;
    boolean isOneTurnCompleted;

    public Deal(int playerSize) {
        bidAmount = BidUtils.INITIAL_BID;
        totalBidOnTable = 0;
        isBidRaised = false;
        totalCheck = 0;
        totalPlayer = 0;
        isOneTurnCompleted = false;
        this.totalPlayer = playerSize;
        canCheck = false;
        currentBetType = BetType.CALL;
    }

    public void call() {
        totalBidOnTable += bidAmount;
        canCheck = false;
        currentBetType = BetType.CALL;
        check();
    }

    public void setOneTurnCompleted(boolean value) {
        isOneTurnCompleted = false;
    }

    public void check() {
        totalCheck++;
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

    public void checkBid() {
        check();
        currentBetType = BetType.CHECK;
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
        bidAmount = BidUtils.INITIAL_BID;
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
