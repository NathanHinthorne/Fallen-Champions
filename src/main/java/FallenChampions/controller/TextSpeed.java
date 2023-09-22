package FallenChampions.controller;

public enum TextSpeed {
    FAST(60),
    MEDIUM(75),
    SLOW(110),
    VERY_SLOW(150);

    private final int delay;

    TextSpeed(int delay) {
        this.delay = delay;
    }

    public int getDelayMillis() {
        return delay;
    }
}
