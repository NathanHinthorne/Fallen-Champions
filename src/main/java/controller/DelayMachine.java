package controller;

import java.io.Console;

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
    public static void delay(final int theTime) {
        try {
            Thread.sleep(theTime * 500);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    private static void shortDelay(final int theTime) {
        try {
            Thread.sleep(theTime * 10);
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public static void printDelayedText(final String theText) {
        for (int i = 0; i < theText.length(); i++) {
            System.out.print(theText.charAt(i));
            System.out.flush(); // Flush the output buffer to print the character immediately
            shortDelay(7);
        }
        System.out.println();
    }

    public static void printDelayedTextFast(final String theText) {
        for (int i = 0; i < theText.length(); i++) {
            System.out.print(theText.charAt(i));
            System.out.flush(); // Flush the output buffer to print the character immediately
            shortDelay(3);
        }
        System.out.println();
    }

//    public static void printDelayedText(final String theText, final Console theConsole) {
//        for (int i = 0; i < theText.length(); i++) {
//            theConsole.writer().print(theText.charAt(i));
//            theConsole.flush(); // Flush the output buffer to print the character immediately
//            shortDelay(5);
//        }
//        theConsole.writer().println();
//    }
//
//    public static void printDelayedTextFast(final String theText, final Console theConsole) {
//        for (int i = 0; i < theText.length(); i++) {
//            theConsole.writer().print(theText.charAt(i));
//            theConsole.flush(); // Flush the output buffer to print the character immediately
//            shortDelay(3);
//        }
//        theConsole.writer().println();
//    }
}
