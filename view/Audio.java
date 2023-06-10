package view;

import java.io.File;
import javax.sound.sampled.*;
import view.*;

/**
 * This class will play audio when called upon by
 * another method.
 *
 * @author Brendan Smith
 * @version 1.0 - 5/20/23
 */
public final class Audio {

    // Separate clips so that they can both be stopped when need be
    private static Clip currentSound;
    private static Clip currentSong;

    public static final File testSound = new File("view\\assets\\sound\\ui\\ui_testsound.wav");


    // UI SOUNDS
    public static final File menuTwo = new File("view\\assets\\sound\\ui\\ui_menu_option_1.wav");
    public static final File menuOne = new File("view\\assets\\sound\\ui\\ui_menu_option_2.wav");
    public static final File beginGame = new File("view\\assets\\sound\\ui\\ui_spawn.wav");
    public static final File powerUp = new File("view\\assets\\sound\\ui\\ui_powerup.wav");

    // MUSIC
    // Made by Nathan
    public static final File ambientSong = new File("view\\assets\\sound\\music\\music_ambient.wav");
    // Made by Brendan
    public static final File testSong = new File("view\\assets\\sound\\music\\music_testtrack.wav");

    // FOOTSTEPS (assets from Valve's Team Fortress 2
    public static final File step1 = new File("view\\assets\\sound\\ui\\game_step1.wav");
    public static final File step2 = new File("view\\assets\\sound\\ui\\game_step2.wav");
    public static final File step3 = new File("view\\assets\\sound\\ui\\game_step3.wav");
    public static final File step4 = new File("view\\assets\\sound\\ui\\game_step4.wav");

    /**
     * Method used to play simplify playing sounds
     * I got help with this method from here:
     * https://www.youtube.com/watch?v=SyZQVJiARTQ
     *
     * @param theFile The sound to play (from this class' public fields)
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

    /**
     * Method used to play simplify playing music
     * (its a separate method because it needs a differnt
     * AudioInputStream to be controlled by the volume slider
     * in the GUI)
     * I got help with this method from here:
     * https://www.youtube.com/watch?v=SyZQVJiARTQ
     *
     * @param theFile The sound to play (from this class' public fields)
     */
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

    /**
     * Method called by the volume slider to change clip
     * volume
     * (had help from here:
     * https://stackoverflow.com/questions/953598/audio-volume-control-increase-or-decrease-in-java)
     *
     * @param theVal The input volume
     */
    public static void setVolume(int theVal) {
        FloatControl gain = (FloatControl) currentSong.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(theVal);
    }

    /**
     * Method to stop all audio: sounds and music alike.
     */
    public static void stopAll() {
        currentSound.stop();
        currentSound.flush();
        currentSound.close();
        currentSong.stop();
        currentSong.flush();
        currentSong.close();
    }

}
