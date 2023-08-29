package FallenChampions.view;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
        setCenter(output);
        output.setWrapText(false);

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

    public String getInput() {
        return history.get(historyPointer);
    }

    public String getInput(String hintText) {
        input.setPromptText(hintText); // first msg - "Type here..." other stuff for diff msgs
        return history.get(historyPointer);
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

    public void setTextColor(Color color) {
        output.setStyle("-fx-text-fill: " + toRgbString(color) + ";");
        input.setStyle("-fx-text-fill: " + toRgbString(color) + ";");
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