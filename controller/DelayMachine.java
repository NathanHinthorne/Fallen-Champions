package controller;

/**
 * A class used to streamline time delays for battles and audio.
 */
public abstract class DelayMachine {

    /**
     * a timed delay to make the game flow better.
     *
     * @param inTime - amount of time to delay.
     *               (int 2 is 1 second, 1 is half a second)
     */
    public static void delay(int inTime) {
        try {
            Thread.sleep(inTime * 500);
        } catch (Exception e) {
            System.out.print("");
        }
    }
}
