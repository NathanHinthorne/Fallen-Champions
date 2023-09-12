package FallenChampions.view;

import javafx.scene.text.Font;

public class FontLoader {

    public static Font loadFont(final FontTypes theFont, final int theSize) {
        try {
            // Load the font using JavaFX or any other suitable method
            String fontFilePath = "/styling/" + theFont.getFontFileName();
            Font font = Font.loadFont(FontLoader.class.getResourceAsStream(fontFilePath), theSize);
            if (font == null) {
                throw new Exception("Font not found");
            }
            return font;
        } catch (Exception e) {
            // Handle font loading errors here
            System.out.println("Error loading font: " + theFont.getFontFileName());
            e.printStackTrace();
            return Font.getDefault(); // Return a default font on error
        }
    }
}