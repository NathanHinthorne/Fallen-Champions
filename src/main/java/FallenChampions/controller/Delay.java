//package FallenChampions.controller;
//
//public class Delay {
//    public static void seconds(final double seconds) {
//
//        long milliseconds = Math.round(seconds*1000);
//
//        // Create a new thread
//        Thread thread = new Thread(() -> {
//            // Delay for the specified number of seconds
//            try {
//                Thread.sleep(milliseconds);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        System.out.println("The delay has expired!");
//        // Start the thread
//        thread.start();
//
//    }
//}

//package FallenChampions.controller;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class Delay {
//    public static void seconds(final double seconds) {
//
//        long milliseconds = Math.round(seconds*1000);
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("The delay has expired!");
//                // Do whatever comes after delay
//            }
//        }, milliseconds);
//    }
//}

package FallenChampions.controller;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.concurrent.CompletableFuture;

public class Delay {
    public static CompletableFuture<Void> pause(double seconds) {
        // Create a CompletableFuture to signal completion
        CompletableFuture<Void> completionIndicator = new CompletableFuture<>();

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(seconds));
        pauseTransition.setOnFinished(event -> {
            // Complete the CompletableFuture when the sequentialTransition finishes
            completionIndicator.complete(null);
        });

        // Start playing the sequentialTransition
        pauseTransition.play();

        // Return the CompletableFuture to the caller
        return completionIndicator;
    }

//    public static void delayAndExecute(final double seconds, final Runnable action) {
//
//        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(seconds));
//        pauseTransition.setOnFinished(event -> action.run());
//        pauseTransition.play();
//    }
//
//    public static void fakeDelay(final double seconds) {
//
//        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(seconds));
//        pauseTransition.play();
//    }
}
