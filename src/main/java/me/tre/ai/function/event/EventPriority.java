package me.tre.ai.function.event;

public enum EventPriority {
    FIRST(0),
    LOWEST(1),
    LOW(2),
    NORMAL(3),
    HIGH(4),
    HIGHEST(5),
    MONITOR(6),
    FINAL(7);

    private final int slot;

    EventPriority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public static final EventPriority[] VALUE_ARRAY;

    static {
        VALUE_ARRAY = new EventPriority[] { MONITOR, FINAL, HIGHEST, HIGH, NORMAL, LOW, LOWEST, FIRST};
    }


}
