package model;

import game.Deal;
import model.bet.BetType;
import model.card.Card;
import model.card.Hand;

import java.util.List;

public class Player {
    private Hand userHand;
    private boolean isFold;
    private long userChips;
    private Deal deal;

    public Player(long userChips,Deal deal) {
        userHand = new Hand(true);
        isFold = false;
        this.userChips = userChips;
        this.deal = deal;
    }

    public boolean canUserCall() {
        return userChips >= deal.getBidAmount();
    }

    public boolean canUserBetDoubleBit() {
        int doubleBit = deal.getBidAmount() * 2;
        return userChips >= doubleBit;
    }

    public void userDoubleBid() {
        int doubleBid = deal.getBidAmount() * 2;
        userChips = userChips - doubleBid;
        deal.makeBid(BetType.DOUBLE_BID);
    }

    public void foldCurrentTurn() {
        deal.makeBid(BetType.FOLD);
        isFold = true;
    }

    public void takeAllChipsOnTable() {
        userChips = userChips + deal.getTotalBidOnTable();
        deal.clearTotalBidOnTable();
    }

    public Hand getPlayerHand() {
        return userHand;
    }
    public void check() {
        deal.makeBid(BetType.CHECK);
    }
    public void call() {
        if(canUserCall()){
            userChips = userChips - deal.getBidAmount();
            deal.makeBid(BetType.CALL);
        } else
            deal.makeBid(BetType.FOLD);
    }
    public void addChipsToUser(int newChips) {
        userChips += newChips;
    }

    public boolean canUserCheck() {
        return deal.canUserCheck();
    }

    public boolean canUserRaise(int amount) {
        int totalChips = amount + deal.getBidAmount();
        return userChips >= totalChips;
    }


    public void raise(int amount) {
        userChips = userChips - amount - deal.getBidAmount();
        deal.makeBid(BetType.RAISE,amount);
    }

    public boolean isFold() {
        return isFold;
    }

    public void setHand(List<Card> cards) {
        userHand.setHand(cards);
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }

    public long getUserChips() {
        return userChips;
    }

    public void setUserChips(long userChips) {
        this.userChips = userChips;
    }
}
