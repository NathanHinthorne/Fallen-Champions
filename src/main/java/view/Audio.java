package view;

import controller.DelayMachine;

import java.io.File;
import java.net.URISyntaxException;
import javax.sound.sampled.*;

/**
 * This class will play audio when called upon by
 * another method.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0 - 5/20/23
 */
public final class Audio {
    private static Audio audio; // to be used in singleton design pattern


    // Separate clips so that they can both be stopped when need be
    private static Clip currentSFX;
    private static Clip currentMusic;
    private static int currentMusicVolume;

    // BUTTON SOUNDS
    public final File menuOne;
    public final File menuTwo;
    public final File menuThree;
    public final File infoPopup;
    public final File error;
    public final File beginGame;


    // MUSIC (Composed by Nathan Hinthorne)
    public final File ambientSong;
    public final File battleSong;
    public final File startingAnewSong;

    // MUSIC
    public final File triumpantFinishSong;
    public final File rickRollSong;


    // ENVIRONMENT (step1, step2, step3, step4 are from Valve's Team Fortress 2)


    // MONSTER
    public final File encounter; // source: Undertale by Toby Fox
//    public final File monsterHeal;


    // HERO (step1, step2, step3, step4 are from Valve's Team Fortress 2)
    public final File step1; // source: Valve's Team Fortress 2
    public final File step2; // source: Valve's Team Fortress 2
    public final File step3; // source: Valve's Team Fortress 2
    public final File step4; // source: Valve's Team Fortress 2
//    public final File heroHeal;
    public final File heroDrinkPotion;
    public final File heroLevelUp;
    public final File heroWinBattle;
    public final File heroDefeat;
    public final File heroOof;
    public final File heroCollectPotion;
    public final File heroCollectPillar;
//    public final File heroCollectWeapon;
//    public final File heroCriticalHit;
    public final File heroPowerUp; // find place to use this
    public final File heroBagOpen;
    public final File heroBagClose;
    public final File heroBagFull;
    public final File heroStatsOn;
    public final File heroStatsOff;


//    public final File sfx;

    // FUNNY SFX
    public final File dunDunDun;
    public final File rimshot;
//    public final File bonk;

    private Audio() throws URISyntaxException { // prevent outside instantiation

        // initialize music files
        menuOne = new File(getClass().getResource("/sound/sfx/button_menu_option_1.wav").toURI());
        menuTwo = new File(getClass().getResource("/sound/sfx/button_menu_option_2.wav").toURI());
        menuThree = new File(getClass().getResource("/sound/sfx/button_menu_option_3.wav").toURI());
        error = new File(getClass().getResource("/sound/sfx/button_error.wav").toURI());
        beginGame = new File(getClass().getResource("/sound/sfx/button_spawn.wav").toURI());
        heroPowerUp = new File(getClass().getResource("/sound/sfx/button_powerup.wav").toURI());
        infoPopup = new File(getClass().getResource("/sound/sfx/info_popup.wav").toURI());

        ambientSong = new File(getClass().getResource("/sound/music/music_ambient.wav").toURI());
        battleSong = new File(getClass().getResource("/sound/music/music_battle.wav").toURI());
        startingAnewSong = new File(getClass().getResource("/sound/music/music_starting_anew.wav").toURI());
        triumpantFinishSong = new File(getClass().getResource("/sound/music/music_triumphant_finish.wav").toURI());
        rickRollSong = new File(getClass().getResource("/sound/music/music_rickroll.wav").toURI());

        // initialize sfx files
        step1 = new File(getClass().getResource("/sound/sfx/hero_step1.wav").toURI());
        step2 = new File(getClass().getResource("/sound/sfx/hero_step2.wav").toURI());
        step3 = new File(getClass().getResource("/sound/sfx/hero_step3.wav").toURI());
        step4 = new File(getClass().getResource("/sound/sfx/hero_step4.wav").toURI());
        encounter = new File(getClass().getResource("/sound/sfx/monster_encounter.wav").toURI());

//        monsterHeal = new File(getClass().getResource("/sound/sfx/monster_heal.wav").toURI());
//        heroHeal = new File(getClass().getResource("/sound/sfx/hero_heal.wav").toURI());
        heroDrinkPotion = new File(getClass().getResource("/sound/sfx/hero_drink_potion.wav").toURI());
        heroLevelUp = new File(getClass().getResource("/sound/sfx/hero_level_up.wav").toURI());
//        heroVictory = new File(getClass().getResource("/sound/sfx/hero_victory.wav").toURI());
        heroDefeat = new File(getClass().getResource("/sound/sfx/hero_defeat.wav").toURI());
        heroOof = new File(getClass().getResource("/sound/sfx/hero_oof.wav").toURI());
        heroCollectPotion = new File(getClass().getResource("/sound/sfx/hero_collect_potion.wav").toURI());
        heroCollectPillar = new File(getClass().getResource("/sound/sfx/hero_collect_pillar.wav").toURI());
        heroBagOpen = new File(getClass().getResource("/sound/sfx/hero_bag_open.wav").toURI());
        heroBagClose = new File(getClass().getResource("/sound/sfx/hero_bag_close.wav").toURI());
        heroBagFull = new File(getClass().getResource("/sound/sfx/hero_bag_full.wav").toURI());
        heroStatsOn = new File(getClass().getResource("/sound/sfx/hero_stats_on.wav").toURI());
        heroStatsOff = new File(getClass().getResource("/sound/sfx/hero_stats_off.wav").toURI());
        heroWinBattle = new File(getClass().getResource("/sound/sfx/hero_win_battle.wav").toURI());

//        bonk = new File(getClass().getResource("/sound/sfx/bonk.wav").toURI());
//        heroSpecialSlice = new File(getClass().getResource("/sound/sfx/hero_special_slice.wav").toURI());
//        heroSpecialSmash = new File(getClass().getResource("/sound/sfx/hero_special_smash.wav").toURI());
//        heroSpecialStab = new File(getClass().getResource("/sound/sfx/hero_special_stab.wav").toURI());

        dunDunDun = new File(getClass().getResource("/sound/sfx/funny_dun_dun_dun.wav").toURI());
        rimshot = new File(getClass().getResource("/sound/sfx/funny_rimshot.wav").toURI());

//        sfx = new File(getClass().getResource("/sound/sfx/bonk.wav").toURI());
    }

    public static Audio getInstance() throws URISyntaxException { // to be used in singleton design pattern
        if (audio == null) {
            audio = new Audio();
        }
        return audio;
    }

    /**
     * Method used to play simplify playing sounds
     * I got help with this method from here:
     * https://www.youtube.com/watch?v=SyZQVJiARTQ
     *
     * @param theFile The sound to play (from this class' public fields)
     */
    public static void playSFX(final File theFile, final int theVolume) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(theFile);
            currentSFX = AudioSystem.getClip();
            currentSFX.open(audioStream);
            setSFXVolume(theVolume);

            currentSFX.start();

        } catch (Exception e) {
            System.out.println("Could not open sound " + theFile.getName());
        }
    }

    public static void playSFX(final File theFile, final int theVolume, boolean deafenMusic) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(theFile);
            currentSFX = AudioSystem.getClip();
            currentSFX.open(audioStream);
            setSFXVolume(theVolume);

            if (deafenMusic) {
                setMusicVolume(currentMusicVolume-10);
            }

            currentSFX.start();

            waitUntilSFXStops(); //?

            if (deafenMusic) {
                setMusicVolume(currentMusicVolume+10);
            }
        } catch (Exception e) {
            System.out.println("Could not open sound " + theFile.getName());
        }
    }

    /**
     * Method used to play simplify playing music
     * (its a separate method because it needs a different
     * AudioInputStream to be controlled by the volume slider
     * in the GUI)
     * I got help with this method from here:
     * https://www.youtube.com/watch?v=SyZQVJiARTQ
     *
     * @param theFile The sound to play (from this class' public fields)
     */
    public static void playMusic(final File theFile, boolean theLoop, int theVolume) {

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(theFile);
            currentMusic = AudioSystem.getClip();
            currentMusic.open(audioStream);
            setMusicVolume(theVolume);
            currentMusic.start();
            if (theLoop) {
                currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
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
     * @param theVolume The input volume
     */
    private static void setMusicVolume(int theVolume) {
        currentMusicVolume = theVolume;
        FloatControl gain = (FloatControl) currentMusic.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(theVolume);
    }

    private static void setSFXVolume(int theVolume) {
        FloatControl gain = (FloatControl) currentSFX.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(theVolume);
    }

    /**
     * Method to stop all audio: sounds and music alike.
     */
    public static void stopAll() {
        currentSFX.stop();
        currentSFX.flush();
        currentSFX.close();

        currentMusic.stop();
        currentMusic.flush();
        currentMusic.close();
    }

    public static void stopMusic() {
        currentMusic.stop();
        currentMusic.flush();
        currentMusic.close();
    }

    public static void stopSFX() {
        currentSFX.stop();
        currentSFX.flush();
        currentSFX.close();
    }

    public static boolean isPlayingSFX() {
        return currentSFX.isActive();
    }

    public static boolean isPlayingMusic() {
        return currentMusic.isActive();
    }

    public static void waitUntilSFXStops() {
        while (isPlayingSFX()) {
            DelayMachine.delay(1);
        }
    }
}
