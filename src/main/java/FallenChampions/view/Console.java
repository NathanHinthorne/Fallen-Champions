package FallenChampions.view;

import FallenChampions.controller.TextSpeed;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * A console that can be used to print and read text from the user.
 *
 * code from https://codereview.stackexchange.com/questions/52197/console-component-in-javafx (with modifications)
 */
public class Console extends BorderPane {
    private static Console instance;
//    private final TextArea output = new TextArea();
    private final TextFlow output = new TextFlow(); // Use TextFlow instead?
    private final TextField input = new TextField();

    private final List<String> history = new ArrayList<>();
    private int historyPointer = 0;

    private Consumer<String> onMessageReceivedHandler;

    private FontTypes defaultFontFamily;
    private int defaultFontSize;
    private Color defaultFontColor;


    private Console() {
//        output.setEditable(false);
        output.setFocusTraversable(false);
//        output.setWrapText(false);
        setCenter(output);





        // Create a ScrollPane and set the TextFlow as its content
        ScrollPane scrollPane = new ScrollPane(output);
        scrollPane.setFitToWidth(true); // Ensure the content resizes with the ScrollPane

        // Add the ScrollPane to the center of the BorderPane
        setCenter(scrollPane);





        input.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String text = input.getText();
//                    output.appendText(text + System.lineSeparator());
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
    public void disableInput(final boolean disable) {
        input.setDisable(disable);
    }

    // issue: when the thread sleeps, the screen goes white until the thread wakes up.
    // However, the thread (currently) needs to sleep to wait for input. So the screen is white forever

//    public String getInput() { // problem: user can enter multiple inputs, filling queue at a time when nothing is being read
//        requestFocus();
//        return history.get(historyPointer); // always returns the last input
//    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        input.requestFocus();
    }

    public void setOnMessageReceivedHandler(final Consumer<String> onMessageReceivedHandler) {
        this.onMessageReceivedHandler = onMessageReceivedHandler;
    }

    public void clear() {
//        GuiUtils.runSafe(() -> output.clear());
    }

    public void print(final String text) {
//        Objects.requireNonNull(text, "text");
//        GuiUtils.runSafe(() -> output.appendText(text));

        Objects.requireNonNull(text, "text");

        Text styledText = new Text(text);
        styledText.setFont(FontLoader.loadFont(defaultFontFamily, defaultFontSize));
        styledText.setFill(defaultFontColor);

        // Wrap UI update in GuiUtils.runSafe()
        GuiUtils.runSafe(() -> output.getChildren().add(styledText));
    }

    public void println(final String text) {
//        Objects.requireNonNull(text, "text");
//        GuiUtils.runSafe(() -> output.appendText(text + System.lineSeparator()));

        Objects.requireNonNull(text, "text");

        Text styledText = new Text(text + System.lineSeparator());
        styledText.setFont(FontLoader.loadFont(defaultFontFamily, defaultFontSize));
        styledText.setFill(defaultFontColor);

        // Wrap UI update in GuiUtils.runSafe()
        GuiUtils.runSafe(() -> output.getChildren().add(styledText));
    }

    public void println() {
        GuiUtils.runSafe(() -> output.getChildren().add(new Text(System.lineSeparator() + System.lineSeparator())));
    }

    public void print(final String text, FontTypes fontFamily, int fontSize, Color fontColor) {

//        Objects.requireNonNull(text, "text");
//        GuiUtils.runSafe(() -> {
//            setOutputFont(fontFamily, fontSize, fontColor);
//            System.out.println("font set to " + fontFamily + ", " + fontSize + ", " + toRgbString(fontColor));
//            output.appendText(text);
//            setOutputFont(defaultFontFamily, defaultFontSize, defaultFontColor); // Set the default font after appending text
//        });

        Objects.requireNonNull(text, "text");

        Text styledText = new Text(text);
        styledText.setFont(FontLoader.loadFont(fontFamily, fontSize));
        styledText.setFill(fontColor);

        // Wrap UI update in GuiUtils.runSafe()
        GuiUtils.runSafe(() -> output.getChildren().add(styledText));
    }

    public void printAnimation(final String textToType, final TextSpeed textSpeed) {
        char[] charactersToType = textToType.toCharArray();
        AtomicInteger currentIndex = new AtomicInteger(0); // AtomicInteger is thread-safe, meaning only one thread can access it at a time
        int speedMillis = textSpeed.getDelayMillis();

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(speedMillis), // Typing speed
                        event -> {
                            int index = currentIndex.get();
                            if (index < charactersToType.length) {
                                Platform.runLater(() -> {
//                                    output.appendText(String.valueOf(charactersToType[index]));
                                    Text styledText = new Text(String.valueOf(charactersToType[index]));
                                    styledText.setFont(FontLoader.loadFont(defaultFontFamily, defaultFontSize));
                                    styledText.setFill(defaultFontColor);
                                    output.getChildren().add(styledText);
                                });
                                currentIndex.incrementAndGet();
                            }
                        }
                )
        );
        timeline.setCycleCount(charactersToType.length); // Repeat for each character

        timeline.play();
    }

    public void setDefaultFont(FontTypes fontFamily, int fontSize, Color fontColor) {
        defaultFontColor = fontColor;
        defaultFontFamily = fontFamily;
        defaultFontSize = fontSize;
        setInputFont(fontFamily, fontSize, fontColor);
        setOutputFont(fontFamily, fontSize, fontColor);
    }
    private void setInputFont(FontTypes fontFamily, int fontSize, Color fontColor) {
        // set font family and size
        Font loadedFont = FontLoader.loadFont(fontFamily, fontSize);
        input.setFont(loadedFont);

        // set font color
        String rgbColor = toRgbString(fontColor);
        input.setStyle(input.getStyle() + "-fx-text-fill: " + rgbColor + ";");
        input.setStyle(input.getStyle() + "-fx-prompt-text-fill: " + rgbColor + ";");
    }

    private void setOutputFont(FontTypes fontFamily, int fontSize, Color fontColor) {

//        // Set font family and size
//        Font loadedFont = FontLoader.loadFont(fontFamily, fontSize);
//        output.setFont(loadedFont);
//
//        // Get the existing styles
//        String existingStyles = output.getStyle();
//
//        // Set font color
//        String rgbColor = toRgbString(fontColor);
//
//        // Check if the -fx-text-fill property already exists
//        if (existingStyles.contains("-fx-text-fill")) {
//            // Replace the existing -fx-text-fill property with the new one
//            existingStyles = existingStyles.replaceAll("-fx-text-fill:[^;]*;", "-fx-text-fill: " + rgbColor + ";");
//        } else {
//            // If it doesn't exist, just append it
//            existingStyles += "-fx-text-fill: " + rgbColor + ";";
//        }
//
//        // Apply the modified styles
//        output.setStyle(existingStyles);
//
//        System.out.println("output style: " + output.getStyle());

    }


    public void setFallbackFont(FontTypes fontFamily, int fontSize) {
        Font loadedFont = FontLoader.loadFont(fontFamily, fontSize);
        output.setStyle(output.getStyle() + "-fx-font-family: 'LoadedFont', 'FallbackFont';");
    }

    public void setOutputBackgroundColor(Color color) {
//        output.setStyle(output.getStyle() + "-fx-control-inner-background: " + toRgbString(color) + ";");
        output.setStyle(output.getStyle() + "-fx-background-color: " + toRgbString(color) + ";");
    }

    public void setInputBackgroundColor(Color color) {
        input.setStyle(input.getStyle() + "-fx-control-inner-background: " + toRgbString(color) + ";");
    }

    public void setLineSpacing(double spacing) {
        output.setStyle(output.getStyle() + "-fx-line-spacing: " + spacing + "px;"); // not working
    }

    private String toRgbString(Color color) {
        return "rgb(" +
                (int) (color.getRed() * 255) + "," +
                (int) (color.getGreen() * 255) + "," +
                (int) (color.getBlue() * 255) +
                ")";
    }

    public static Console getInstance() {
        if (instance == null) {
            instance = new Console();
        }
        return instance;
    }

}