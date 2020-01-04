package game;

import util.BidUtils;

public class Deal {
    int bidAmount;
    int totalBidOnTable;
    int totalCheck;
    int totalPlayer;
    int bidTurn = 0;
    int foldedPlayers = 0;

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
    }

    public void call() {
        totalBidOnTable += bidAmount;
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
            bidAmount = BidUtils.INITIAL_BID;
            isBidRaised = false;
        }
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
        bidAmount = bidAmount * 2;
        totalBidOnTable += bidAmount;
        isBidRaised = true;
        totalCheck = 0;

    }
    public void raise(int raiseAmount) {
        bidAmount =  bidAmount + raiseAmount;
        totalBidOnTable += bidAmount;
        totalCheck = 0;
        isBidRaised = true;
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
