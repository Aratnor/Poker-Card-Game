package util;

import game.Deal;
import model.Player;
import model.card.Deck;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static List<Player> providePlayers(Deal deal, int listSize,int realPlayerPosition) {
        List<Player> gamePlayers = new ArrayList<>();
        for(int i = 0;i< listSize;i++) {
            if( i == realPlayerPosition){
                Player realPlayer = new Player(ResourceUtils.INITIAL_USER_CHIPS,deal);
                realPlayer.setRealUser(true);
                gamePlayers.add(realPlayer);
            } else {
                gamePlayers.add(new Player(ResourceUtils.INITIAL_USER_CHIPS,deal));

            }
        }
        return gamePlayers;
    }

    public static void providePlayersCard(List<Player> players, Deck deck) {
        for(Player player: players) {
            player.setHand(deck.dealCardToUser());
        }
    }
}
