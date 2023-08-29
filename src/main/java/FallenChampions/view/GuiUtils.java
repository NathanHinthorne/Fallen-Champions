package FallenChampions.view;

import javafx.application.Platform;

import java.util.Objects;

/**
 * Utility class for JavaFX GUIs.
 *
 * code from https://codereview.stackexchange.com/questions/52197/console-component-in-javafx
 */
public final class GuiUtils {
    private GuiUtils() {
        throw new UnsupportedOperationException();
    }

    public static void runSafe(final Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable");
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        }
        else {
            Platform.runLater(runnable);
        }
    }
}