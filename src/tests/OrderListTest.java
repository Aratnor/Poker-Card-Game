package tests;

import model.card.Card;
import model.card.type.Suit;
import org.junit.Assert;
import org.junit.Test;
import util.OrderList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class OrderListTest {

    @Test
    public void testSort() {
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i< 5; i++) {
            cards.add(new Card(Suit.CLUB,i));
        }
        Collections.shuffle(cards);
        OrderList.sort(cards);
        int[] array = new int[5];
        for(int i = 0;i<5;i++) {
            array[i] = cards.get(i).getValue();
        }

        Assert.assertArrayEquals(new int[]{0,1,2,3,4}, array);

    }

}