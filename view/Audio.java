package view;

import java.io.File;
import javax.sound.sampled.*;
import view.*;

public final class Audio {

    // Separate clips so that they can both be stopped when need be
    private static Clip currentSound;
    private static Clip currentSong;

    protected static final File testSound = new File("view\\assets\\sound\\ui\\ui_testsound.wav");
    protected static final File testSong = new File("view\\assets\\sound\\music\\music_testtrack.wav");


    // UI SOUNDS
    protected static final File menuTwo = new File("view\\assets\\sound\\ui\\ui_menu_option_1.wav");
    protected static final File menuOne = new File("view\\assets\\sound\\ui\\ui_menu_option_2.wav");
    protected static final File beginGame = new File("view\\assets\\sound\\ui\\ui_spawn.wav");
    protected static final File powerUp = new File("view\\assets\\sound\\ui\\ui_powerup.wav");

    // MUSIC
    protected static final File ambientSong = new File("view\\assets\\sound\\music\\music_ambient.wav");

    // FOOTSTEPS
    protected static final File step1 = new File("view\\assets\\sound\\ui\\game_step1.wav");
    protected static final File step2 = new File("view\\assets\\sound\\ui\\game_step2.wav");
    protected static final File step3 = new File("view\\assets\\sound\\ui\\game_step3.wav");
    protected static final File step4 = new File("view\\assets\\sound\\ui\\game_step4.wav");

    // COMBAT SOUNDS
    protected static final File com_Dodge = new File("SFX\\class_dodge.wav");
    protected static final File com_Hit_Robot = new File("SFX\\class_damage_robot.wav");
    protected static final File com_Hit_Human = new File("SFX\\class_damage_human.wav");
    protected static final File specialTwo = new File("SFX\\special_player_mage.wav");
    protected static final File specialOne = new File("SFX\\special_player_droid.wav");

//    /**
//     * Method to print text letter by letter.
//     *
//     * @param theIn - The string to print.
//     */
//    public static void dialoguePrint(final String theIn) {
//
//        for (int i = 0; i < theIn.length(); i++) {
//
//            System.out.print(theIn.charAt(i));
//
//            try {
//                Thread.sleep(30);
//                AudioInputStream audioStream = AudioSystem.getAudioInputStream(textSound);
//                Clip clip = AudioSystem.getClip();
//                clip.open(audioStream);
//                clip.start();
//            } catch (Exception e) {
//                System.out.print("");
//            }
//        }
//    }

    /**
     * Method used to play simplify playing sounds
     * I got help with this method from here:
     * https://www.youtube.com/watch?v=SyZQVJiARTQ
     *
     * @param theFile - the sound to play (from this class' public fields)
     */
    public static void play(final File theFile) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(theFile);
            currentSound = AudioSystem.getClip();
            currentSound.open(audioStream);
            currentSound.start();
        } catch (Exception e) {
            System.out.println("Could not open sound " + theFile.getName());
        }
    }

    public static void playMusic(final File theFile, boolean theLoop) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(theFile);
            currentSong = AudioSystem.getClip();
            currentSong.open(audioStream);;
            FloatControl gain = (FloatControl) currentSong.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(-10);
            currentSong.start();
            if (theLoop) {
                currentSong.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.out.println("Could not open sound file!");
        }
    }

    public static void setVolume(int theVal) {
        FloatControl gain = (FloatControl) currentSong.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(theVal);
    }

    public static void stopAll() {
        currentSound.stop();
        currentSound.flush();
        currentSound.close();
        currentSong.stop();
        currentSong.flush();
        currentSong.close();
    }

}
