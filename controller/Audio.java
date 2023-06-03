package controller;

import java.io.File;
import javax.sound.sampled.*;

public final class Audio {

    // UI SOUNDS
    protected static final File textSound = new File("SFX\\ui_dialogue.wav");
    protected static final File menuOne = new File("SFX\\ui_menu_1.wav");
    protected static final File menuTwo = new File("SFX\\ui_menu_2.wav");
    protected static final File beginGame = new File("SFX\\ui_gamestart.wav");

    protected static final File test = new File("assets\\Retro_-_Chip_Power.wav");

    // COMBAT SOUNDS
    protected static final File com_Dodge = new File("SFX\\class_dodge.wav");
    protected static final File com_Hit_Robot = new File("SFX\\class_damage_robot.wav");
    protected static final File com_Hit_Human = new File("SFX\\class_damage_human.wav");
    protected static final File specialTwo = new File("SFX\\special_player_mage.wav");
    protected static final File specialOne = new File("SFX\\special_player_droid.wav");

    /**
     * Method to print text letter by letter.
     *
     * @param theIn - The string to print.
     */
    public static void dialoguePrint(final String theIn) {

        for (int i = 0; i < theIn.length(); i++) {

            System.out.print(theIn.charAt(i));

            try {
                Thread.sleep(30);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(textSound);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception e) {
                System.out.print("");
            }
        }
    }

    /**
     * Method used to play simplify playing sounds (CREDIT FOR THIS METHOD
     * IN README.TXT)
     *
     * @param theFile - the sound to play (from this class' public fields)
     */
    public static void play(final File theFile) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(theFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.print("");
        }
    }
}
