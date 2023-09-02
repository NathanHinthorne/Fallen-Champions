package FallenChampions.view;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A console that can be used to print and read text from the user.
 *
 * code from https://codereview.stackexchange.com/questions/52197/console-component-in-javafx (with modifications)
 */
public class Console extends BorderPane {
    private static Console instance;
    protected final TextArea output = new TextArea();
    protected final TextField input = new TextField();

    protected final List<String> history = new ArrayList<>();
    protected int historyPointer = 0;

    private Consumer<String> onMessageReceivedHandler;

    public Console() {
        output.setEditable(false);
        output.setFocusTraversable(false);
        output.setWrapText(false);
        setCenter(output);

        input.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String text = input.getText();
                    output.appendText(text + System.lineSeparator());
                    history.add(text);
                    historyPointer++;
                    if (onMessageReceivedHandler != null) {
                        onMessageReceivedHandler.accept(text);
                    }
                    input.clear();
                    break;
                case UP:
                    if (historyPointer == 0) {
                        break;
                    }
                    historyPointer--;
                    GuiUtils.runSafe(() -> {
                        input.setText(history.get(historyPointer));
                        input.selectAll();
                    });
                    break;
                case DOWN:
                    if (historyPointer == history.size() - 1) {
                        break;
                    }
                    historyPointer++;
                    GuiUtils.runSafe(() -> {
                        input.setText(history.get(historyPointer));
                        input.selectAll();
                    });
                    break;
                default:
                    break;
            }
        });
        setBottom(input);
    }

    public void setHintText(String text) {
        input.setPromptText(text);
    }

    // issue: when the thread sleeps, the screen goes white until the thread wakes up.
    // However, the thread (currently) needs to sleep to wait for input. So the screen is white forever

    public String getInput() { // problem: user can enter multiple inputs, filling queue at a time when nothing is being read
        requestFocus();
        return history.get(historyPointer); // always returns the last input
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        input.requestFocus();
    }

    public void setOnMessageReceivedHandler(final Consumer<String> onMessageReceivedHandler) {
        this.onMessageReceivedHandler = onMessageReceivedHandler;
    }

    public void clear() {
        GuiUtils.runSafe(() -> output.clear());
    }

    public void print(final String text) {
        Objects.requireNonNull(text, "text");
        GuiUtils.runSafe(() -> output.appendText(text));
    }

    public void println(final String text) {
        Objects.requireNonNull(text, "text");
        GuiUtils.runSafe(() -> output.appendText(text + System.lineSeparator()));
    }

    public void println() {
        GuiUtils.runSafe(() -> output.appendText(System.lineSeparator()));
    }

    public void setInputTextColor(Color color) {
        input.setStyle("-fx-text-fill: " + toRgbString(color) + ";");
        input.setStyle("-fx-prompt-text-fill: " + toRgbString(color) + ";"); // not working
    }

    public void setOutputTextColor(Color color) {
        output.setStyle("-fx-text-fill: " + toRgbString(color) + ";");
    }

    public void setOutputBackgroundColor(Color color) {
        output.setStyle("-fx-control-inner-background: " + toRgbString(color) + ";");
    }

    public void setInputBackgroundColor(Color color) {
        input.setStyle("-fx-control-inner-background: " + toRgbString(color) + ";");
    }

    private String toRgbString(Color color) {
        return "rgb(" +
                (int) (color.getRed() * 255) + "," +
                (int) (color.getGreen() * 255) + "," +
                (int) (color.getBlue() * 255) +
                ")";
    }

    public void setFont(Fonts fontFamily, int fontSize) {
        Font loadedFont = null;

        if (fontFamily == Fonts.JETBRAINS_MONO) {
            loadedFont = Font.loadFont(getClass().getResourceAsStream("/styling/JetBrains_Mono/JetBrainsMono-Regular.ttf"), fontSize);

        } else if (fontFamily == Fonts.BEAUTY_GLITCH) {
            loadedFont = Font.loadFont(getClass().getResourceAsStream("/styling/BeautyGlitch/BeautyGlitch-Regular.ttf"), fontSize);

        }

        output.setFont(loadedFont);
        input.setFont(loadedFont);
    }

    public static Console getInstance() {
        if (instance == null) {
            instance = new Console();
        }
        return instance;
    }
}