package FallenChampions.view;

public enum FontTypes {
    JETBRAINS_MONO("JetBrainsMono/JetBrainsMono-Regular.ttf"),
    SANS_DEJA_VU("SansDejaVu/SansDejaVu.ttf"),
    BEAUTY_GLITCH("BeautyGlitch/BeautyGlitch.ttf");

    private final String fontFileName;

    FontTypes(String fontFileName) {
        this.fontFileName = fontFileName;
    }

    public String getFontFileName() {
        return fontFileName;
    }
}
