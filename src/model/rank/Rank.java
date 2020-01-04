package model.rank;

public enum  Rank {
    Royal_Flush(9),
    Straight_Flush(8),
    Four_of_a_kind(7),
    Full_House(6),
    Flush(5),
    Straight(4),
    Three_of_a_kind(3),
    Two_Pair(2),
    One_Pair(1),
    High_Card(0);

    private final int value;

    Rank(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
