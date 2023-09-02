package FallenChampions.controller;

public class Delay {
    public static void seconds(final double seconds) {

        long milliseconds = Math.round(seconds*1000);

        // Create a new thread
        Thread thread = new Thread(() -> {
            // Delay for the specified number of seconds
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Start the thread
        thread.start();

    }
}