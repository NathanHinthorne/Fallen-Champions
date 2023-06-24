package controller;

/**
 * A class used to streamline time delays for battles and audio.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0 - 5/12/23
 */
public abstract class DelayMachine {

    /**
     * a timed delay to make the game flow better.
     * (Help with this method from:
     * https://www.youtube.com/watch?v=NIciP9yyDPw)
     * @param theTime - amount of time to delay.
     *               (int 2 is 1 second, 1 is half a second)
     */
    public static void delay(int theTime) {
        try {
            Thread.sleep(theTime * 500);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    private static void shortDelay(int theTime) {
        try {
            Thread.sleep(theTime * 10);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public static void printDelayedText(String theText) {
        for (int i = 0; i < theText.length(); i++) {
            System.out.print(theText.charAt(i));
            System.out.flush(); // Flush the output buffer to print the character immediately
            shortDelay(5);
        }
        System.out.println();
    }

    public static void printDelayedTextFast(String theText) {
        for (int i = 0; i < theText.length(); i++) {
            System.out.print(theText.charAt(i));
            System.out.flush(); // Flush the output buffer to print the character immediately
            shortDelay(3);
        }
        System.out.println();
    }
}
