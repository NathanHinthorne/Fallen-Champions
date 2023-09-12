package FallenChampions.view;

import FallenChampions.controller.TextSpeed;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

    private static final String PADDING = "  ";

    private static Console instance;

//    private final TextArea output = new TextArea();
    private ScrollPane scrollPane = new ScrollPane();
    private boolean doScroll = true;

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

        // Create a ScrollPane
        scrollPane.setFitToWidth(true); // Ensure the content resizes with the ScrollPane

        // Create a VBox and add the TextFlow to it
        VBox vbox = new VBox(output);
        vbox.setFillWidth(true); // Make sure the VBox stretches to fill available width

        scrollPane.setContent(vbox);

        // Set an initial size for the TextFlow
        int width = 1225;
        int height = 674;
        output.setPrefSize(width, height);

        // Add the ScrollPane to the center of the BorderPane
        setCenter(scrollPane);

        input.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER:
                    String text = input.getText();
//                    output.appendText(text + System.lineSeparator());
                    Text styledText = applyDefaultTextStyle(PADDING + text + System.lineSeparator());
                    output.getChildren().add(styledText);
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

    private void scrollToBottom() {
        double scrollMax = scrollPane.getVmax();
        scrollPane.setVvalue(scrollMax);
    }

    private Text applyDefaultTextStyle(String text) {
        Text styledText = new Text(text);
        styledText.setFont(FontLoader.loadFont(defaultFontFamily, defaultFontSize));
        styledText.setFill(defaultFontColor);
        return styledText;
    }

    private Text applyTextStyle(String text, FontTypes fontFamily, int fontSize, Color fontColor) {
        Text styledText = new Text(text);
        styledText.setFont(FontLoader.loadFont(fontFamily, fontSize));
        styledText.setFill(fontColor);
        return styledText;
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

    public void println(final String text) {
//        Objects.requireNonNull(text, "text");
//        GuiUtils.runSafe(() -> output.appendText(text + System.lineSeparator()));

        Objects.requireNonNull(text, "text");

        Text styledText = applyDefaultTextStyle(PADDING + text + System.lineSeparator());

        // Wrap UI update in GuiUtils.runSafe()
        GuiUtils.runSafe(() -> output.getChildren().add(styledText));

        if (doScroll) {
            // Scroll to the bottom with a small delay
            Platform.runLater(this::scrollToBottom);
        }
    }

    public void println() {
        GuiUtils.runSafe(() -> output.getChildren().add(new Text(System.lineSeparator())));

        if (doScroll) {
            // Scroll to the bottom with a small delay
            Platform.runLater(this::scrollToBottom);
        }
    }

    public void print(final String text) {
        Objects.requireNonNull(text, "text");

        Text styledText = applyDefaultTextStyle(PADDING + text);

        // Wrap UI update in GuiUtils.runSafe()
        GuiUtils.runSafe(() -> output.getChildren().add(styledText));

        if (doScroll) {
            // Scroll to the bottom with a small delay
            Platform.runLater(this::scrollToBottom);
        }
    }

    public void print(final String text, FontTypes fontFamily, int fontSize, Color fontColor) {
        Objects.requireNonNull(text, "text");

        Text styledText = applyTextStyle(PADDING + text, fontFamily, fontSize, fontColor);

        // Wrap UI update in GuiUtils.runSafe()
        GuiUtils.runSafe(() -> output.getChildren().add(styledText));

        if (doScroll) {
            // Scroll to the bottom with a small delay
            Platform.runLater(this::scrollToBottom);
        }
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
                                    Text styledText = applyDefaultTextStyle(String.valueOf(charactersToType[index]));
                                    output.getChildren().add(styledText);
                                });
                                currentIndex.incrementAndGet();
                            }
                        }
                )
        );
        timeline.setCycleCount(charactersToType.length); // Repeat for each character

        timeline.play();

        timeline.setOnFinished(event -> {
            if (doScroll) {
                // Scroll to the bottom with a small delay
                Platform.runLater(this::scrollToBottom);
            }
        });
    }

    public void setDefaultFont(FontTypes fontFamily, int fontSize, Color fontColor) {
        defaultFontColor = fontColor;
        defaultFontFamily = fontFamily;
        defaultFontSize = fontSize;
    }

    public void setFallbackFont(FontTypes fontFamily, int fontSize) {
        Font loadedFont = FontLoader.loadFont(fontFamily, fontSize);
        output.setStyle(output.getStyle() + "-fx-font-family: 'LoadedFont', 'FallbackFont';");
    }

    public void setOutputBackgroundColor(Color color) {
        output.setStyle(output.getStyle() + "-fx-background-color: " + toRgbString(color) + ";"); // simple way, but adds on to existing styles instead of replacing them
//        String existingStyles = output.getStyle();
//
//        String rgbColor = toRgbString(color);
//
//        // Check if the -fx-text-fill property already exists
//        if (existingStyles.contains("-fx-background-color")) {
//            // Replace the existing -fx-text-fill property with the new one
//            existingStyles = existingStyles.replaceAll("-fx-background-color:[^;]*;", "-fx-background-color: " + rgbColor + ";");
//        } else {
//            // If it doesn't exist, just append it
//            existingStyles += "-fx-background-color: " + rgbColor + ";";
//        }
//
//        // Apply the modified styles
//        output.setStyle(existingStyles);
//        System.out.println("output style: " + output.getStyle());
    }

    public void setInputBackgroundColor(Color color) {
        input.setStyle(input.getStyle() + "-fx-control-inner-background: " + toRgbString(color) + ";");
//        String existingStyles = input.getStyle();
//
//        String rgbColor = toRgbString(color);
//
//        // Check if the -fx-text-fill property already exists
//        if (existingStyles.contains("-fx-background-color")) {
//            // Replace the existing -fx-text-fill property with the new one
//            existingStyles = existingStyles.replaceAll("-fx-background-color:[^;]*;", "-fx-background-color: " + rgbColor + ";");
//        } else {
//            // If it doesn't exist, just append it
//            existingStyles += "-fx-background-color: " + rgbColor + ";";
//        }
//
//        // Apply the modified styles
//        input.setStyle(existingStyles);
//        System.out.println("output style: " + input.getStyle());
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