package util;

import model.Player;
import model.bet.BetType;
import model.rank.Rank;

public class AIUtils {
    public static BetType decideBet(Rank handRank, int currentTurn, Player currentAiPlayer, BetType currentBetType) {
        switch (handRank) {
            case Royal_Flush:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.DOUBLE_BID;
                } else if (currentTurn == 2) {
                    return BetType.ALL_IN;
                }
            case Straight_Flush:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.DOUBLE_BID;
                }
            case Four_of_a_kind:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    if(currentBetType == BetType.CHECK)
                        return BetType.CHECK;
                    else return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.DOUBLE_BID;
                }
            case Full_House:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    if(currentBetType == BetType.CHECK)
                        return BetType.CHECK;
                    else return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.DOUBLE_BID;
                }
            case Flush:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    if(currentBetType == BetType.CHECK)
                        return BetType.CALL;
                    else return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.CALL;
                }
            case Straight:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    if(currentBetType == BetType.CHECK)
                        return BetType.CHECK;
                    else return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.DOUBLE_BID;
                }
            case Three_of_a_kind:
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    if(currentBetType == BetType.CHECK)
                        return BetType.CHECK;
                    else return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.DOUBLE_BID;
                }
            case Two_Pair:
                if(currentBetType == BetType.ALL_IN)
                    return BetType.FOLD;
                if (currentTurn == 0 && currentAiPlayer.canUserCall()) {
                    if(currentBetType == BetType.CHECK)
                        return BetType.CHECK;
                    else return BetType.CALL;
                } else if (currentTurn == 1) {
                    return BetType.CALL;
                } else if (currentTurn == 2) {
                    return BetType.CALL;
                }
            case One_Pair:
                if(currentBetType == BetType.ALL_IN || currentBetType == BetType.DOUBLE_BID)
                    return BetType.FOLD;
                else
                    return BetType.CALL;
            case High_Card:
                if(currentBetType == BetType.ALL_IN || currentBetType == BetType.DOUBLE_BID)
                    return BetType.FOLD;
                else return BetType.CALL;
        }
        return BetType.CALL;
    }
}
