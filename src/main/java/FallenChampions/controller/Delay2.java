package FallenChampions.controller;

import java.util.Timer;
import java.util.TimerTask;

public class Delay2 {
    public static void seconds(final double seconds) {

        long milliseconds = Math.round(seconds*1000);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("The delay has expired!");
                // Do whatever comes after delay
            }
        }, milliseconds);
    }
}
