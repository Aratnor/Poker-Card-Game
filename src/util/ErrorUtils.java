package util;

import model.bet.BetType;

public class ErrorUtils {

    public static IllegalArgumentException getIllegalDealException(BetType actionBetType,BetType currentBetType, boolean isOneTurnCompleted){
        return new IllegalArgumentException(
                "U cannot "+ actionBetType + " bet in that condition lastBetType:" +
                        currentBetType+
                        " turnFinished :" + isOneTurnCompleted);

    }
}
