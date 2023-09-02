package FallenChampions.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameScreen {
    private final Stage myPrimaryStage;

    public GameScreen(final Stage primaryStage) {
        this.myPrimaryStage = primaryStage;
        setupIcon();
        setupScene();
    }

    private void setupIcon() {
        // Load the icon image
        Image iconImage = loadImage("/images/icon.png");

        // Set the icon for the stage
        myPrimaryStage.getIcons().add(iconImage);
    }

    private void setupScene() {
        Console consoleWindow = Console.getInstance();

        // Set the text color and background color of the console
        consoleWindow.setInputTextColor(Color.WHITE);
        consoleWindow.setInputBackgroundColor(Color.rgb(50, 50, 50, 1)); // light grey
        consoleWindow.setOutputTextColor(Color.WHITE);
        consoleWindow.setOutputBackgroundColor(Color.rgb(30, 30, 30, 1)); // dark grey
        consoleWindow.setFont(Fonts.JETBRAINS_MONO, 14);

        // Create ImageView nodes for the left and right images
        Image leftImage = loadImage("/images/wall.png");
        ImageView leftImageView = new ImageView(leftImage);
        leftImageView.preserveRatioProperty().set(true);
        leftImageView.setFitWidth(140);

        Image rightImage = loadImage("/images/wall.png");
        ImageView rightImageView = new ImageView(rightImage);
        rightImageView.preserveRatioProperty().set(true);
        rightImageView.setFitWidth(140);

        // Create a BorderPane to hold the consoleWindow
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(leftImageView);
        borderPane.setRight(rightImageView);

        // Add the consoleWindow to the center of the BorderPane
        borderPane.setCenter(consoleWindow);

        // Create a new Scene using the borderPane as its root
        int width = 1250;
        int height = 700;
        Scene consoleScene = new Scene(borderPane, width, height);

        myPrimaryStage.setY(50); // temp
        myPrimaryStage.setX(400); // temp

        myPrimaryStage.setResizable(false);

        myPrimaryStage.setScene(consoleScene);
    }

    private Image loadImage(String path) {
        return new Image(getClass().getResource(path).toExternalForm());
    }
}
