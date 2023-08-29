package FallenChampions.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class TitleScreen {

    private final Stage myPrimaryStage;
    private ImageView myImage;
    private ImageView startButton; // not a button object because it needs to have customized look
    private EventHandler<ActionEvent> startButtonListener;

    public TitleScreen(final Stage primaryStage) {
        this.myPrimaryStage = primaryStage;
        setupImage();
        setupButton();
        setupIcon();
        setupScene();
        intializeCSS();
    }

    private void intializeCSS() {
        // Load the custom CSS file
        myPrimaryStage.getScene().getStylesheets().add(getClass().getResource("/styling/button.css").toExternalForm());
    }

    private void setupImage() {
        // Load the original image
        Image originalImage = loadImage("/images/title_screen.png");

        // Create an ImageView with the resized image
        myImage = new ImageView(originalImage);
        myImage.setPreserveRatio(true); // Maintain the aspect ratio

        // shrink image to 600 width and 700 height
        myImage.setFitWidth(600);
    }

    private void setupButton() {
        // Load button images
        Image normalImage = loadImage("/images/button_normal.png");
        Image shadedImage = loadImage("/images/button_shaded.png");

        // Create an ImageView with the normal image
        startButton = new ImageView(normalImage);

        // Set the size of the button
        double buttonWidth = normalImage.getWidth() / 2;
        double buttonHeight = normalImage.getHeight() / 2;
        startButton.setFitWidth(buttonWidth);
        startButton.setFitHeight(buttonHeight);

        // Apply custom CSS style to the ImageView (optional)
        startButton.getStyleClass().add("cool-image-button");

        try {
            setupButtonListeners(normalImage, shadedImage);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        applyHoverEffect();
    }

    private void setupButtonListeners(Image theNormalImage, Image theShadedImage) throws URISyntaxException {
        AudioManager audio = AudioManager.getInstance();

        startButton.setOnMousePressed(event -> {
            // Change the image to the shaded image when pressed
            startButton.setImage(theShadedImage);

            // Play press sound
            audio.playSFX(audio.mouseClick, 100);

            // Shift the button's position slightly down and to the right
            double shiftAmount = 3; // Adjust this value as needed
            startButton.setTranslateX(startButton.getTranslateX() + shiftAmount);
            startButton.setTranslateY(startButton.getTranslateY() + shiftAmount);
        });

        startButton.setOnMouseReleased(event -> {

            // Change the image back to normal when released
            startButton.setImage(theNormalImage);

            if (startButtonListener != null) {
                ActionEvent actionEvent = new ActionEvent(startButton, null);
                startButtonListener.handle(actionEvent);
            } else {
                System.out.println("No listener for start button!");
            }

            // Reset the button's position
            startButton.setTranslateX(0);
            startButton.setTranslateY(0);
        });
    }

    private void applyHoverEffect() {
        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(javafx.scene.paint.Color.rgb(50, 50, 50, 0.2));
        glowEffect.setRadius(6);
        glowEffect.setSpread(1);

        startButton.setOnMouseEntered(event -> startButton.setEffect(glowEffect));
        startButton.setOnMouseExited(event -> startButton.setEffect(null));
    }

    private void setupIcon() {
        // Load the icon image
        Image iconImage = loadImage("/images/icon.png");

        // Set the icon of the stage
        myPrimaryStage.getIcons().add(iconImage);
    }

    private void setupScene() {
        StackPane root = new StackPane();

        // Set the alignment of the button to be at the bottom center
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);

        // Set a margin of 200 pixels for the button from the bottom
        StackPane.setMargin(startButton, new Insets(0, 0, 150, 0));

        // Add the nodes to the root
        root.getChildren().addAll(myImage, startButton);

        // Create a scene with the root
        Scene scene = new Scene(root);

        // Make the window not resizable
        myPrimaryStage.setResizable(false);

        // Set the scene to the primary stage
        myPrimaryStage.setScene(scene);
    }

    public void hide() {
        myPrimaryStage.hide();
    }

    private Image loadImage(String path) {
        return new Image(getClass().getResource(path).toExternalForm());
    }

    public void setStartButtonListener(EventHandler<ActionEvent> listener) {
        this.startButtonListener = listener;
    }
}
