package util;

import game.Deal;
import model.Player;
import model.card.Deck;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static List<Player> providePlayers(Deal deal, int listSize) {
        List<Player> gamePlayers = new ArrayList<>();
        for(int i = 0;i< listSize;i++) {
            gamePlayers.add(new Player(500,deal));
        }
        return gamePlayers;
    }

    public static void providePlayersCard(List<Player> players, Deck deck) {
        for(Player player: players) {
            player.setHand(deck.dealCardToUser());
        }
    }
}
