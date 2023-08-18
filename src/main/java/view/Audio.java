package view;

import controller.DelayMachine;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

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

    // Constants
    private static final double DEFAULT_VOLUME = 0.5;


    // Separate media players for music and sound effects
    private MediaPlayer musicPlayer;
    private MediaPlayer sfxPlayer;


    // BUTTON SOUNDS
    public Media menuOne;
    public Media menuTwo;
    public Media menuThree;
    public Media infoPopup;
    public Media swishOn;
    public Media swishOff;
    public Media error;
    public Media beginGame;
    public Media titleScreen;


    // MUSIC (Composed by Nathan Hinthorne)
    public Media ambientSong;
    public Media battleSong;
    public Media startingAnewSong;

    // MUSIC
    public Media triumpantFinishSong;
    public Media rickRollSong;
    public Media conkerSong;
    public Media hackerSong;
    public Media genosThemeSong;
    public Media gasterBossSong;
    public Media gasterAmbientSong;


    // MONSTER
    public Media encounter; // source: Undertale by Toby Fox
//    public final File monsterHeal;


    // HERO (step1, step2, step3, step4 are from Valve's Team Fortress 2)
    public Media step1; // source: Valve's Team Fortress 2
    public Media step2; // source: Valve's Team Fortress 2
    public Media step3; // source: Valve's Team Fortress 2
    public Media step4; // source: Valve's Team Fortress 2
    public Media lockedDoor;
    public Media heroDrinkPotion;
    public Media heroLevelUp;
    public Media heroWinBattle;
    public Media heroDefeat;
    public Media heroOof;
    public Media heroCollectPotion;
    public Media heroCollectPillar;
    public Media heroPowerUp; // find place to use this
    public Media heroBagOpen;
    public Media heroBagClose;
    public Media heroBagFull;


//    public final File heroCollectWeapon;
    // FUNNY SFX
    public Media dunDunDun;
    public Media rimshot;
//    public final File bonk;

    private Audio() { // prevent outside instantiation

        // Initialize media players
//        musicPlayer = new MediaPlayer(loadMedia("/sound/music/music_ambient.wav"));
//        sfxPlayer = new MediaPlayer(loadMedia("/sound/sfx/button_menu_option_1.wav"));

        // Set default volumes
//        musicPlayer.setVolume(DEFAULT_VOLUME);
//        sfxPlayer.setVolume(DEFAULT_VOLUME);

        // Preload and initialize all media files
        preloadMediaFiles();
    }

    private void preloadMediaFiles() {
        // Preload music files
        ambientSong = loadMedia("/sound/music/music_ambient.wav");
        battleSong = loadMedia("/sound/music/music_battle.wav");
        startingAnewSong = loadMedia("/sound/music/music_starting_anew.wav");
        triumpantFinishSong = loadMedia("/sound/music/music_triumphant_finish.wav");
        rickRollSong = loadMedia("/sound/music/music_rickroll.wav");
        conkerSong = loadMedia("/sound/music/music_conker.wav");
        hackerSong = loadMedia("/sound/music/music_hacker.wav");
        genosThemeSong = loadMedia("/sound/music/music_genos_theme.wav");
        gasterBossSong = loadMedia("/sound/music/music_gaster_boss.wav");
        gasterAmbientSong = loadMedia("/sound/music/music_gaster_ambient.wav");

        // Preload sfx files

            // BUTTONS
        menuOne = loadMedia("/sound/sfx/menu_one.wav");
        menuTwo = loadMedia("/sound/sfx/menu_two.wav");
        menuThree = loadMedia("/sound/sfx/menu_three.wav");
        error = loadMedia("/sound/sfx/error.wav");
        beginGame = loadMedia("/sound/sfx/button_spawn.wav");
        heroPowerUp = loadMedia("/sound/sfx/button_powerup.wav");
        infoPopup = loadMedia("/sound/sfx/info_popup.wav");
        titleScreen = loadMedia("/sound/sfx/title_screen.wav");
//        confirm = loadMedia("/sound/sfx/confirm.wav");

            // HERO
        step1 = loadMedia("/sound/sfx/hero_step1.wav");
        step2 = loadMedia("/sound/sfx/hero_step2.wav");
        step3 = loadMedia("/sound/sfx/hero_step3.wav");
        step4 = loadMedia("/sound/sfx/hero_step4.wav");
        lockedDoor = loadMedia("/sound/sfx/locked_door.wav");
        encounter = loadMedia("/sound/sfx/monster_encounter.wav");
        heroDrinkPotion = loadMedia("/sound/sfx/hero_drink_potion.wav");
        heroLevelUp = loadMedia("/sound/sfx/hero_level_up.wav");
        heroDefeat = loadMedia("/sound/sfx/hero_defeat.wav");
        heroOof = loadMedia("/sound/sfx/hero_oof.wav");
        heroCollectPotion = loadMedia("/sound/sfx/hero_collect_potion.wav");
        heroCollectPillar = loadMedia("/sound/sfx/hero_collect_pillar.wav");
        heroBagOpen = loadMedia("/sound/sfx/hero_bag_open.wav");
        heroBagClose = loadMedia("/sound/sfx/hero_bag_close.wav");
        heroBagFull = loadMedia("/sound/sfx/hero_bag_full.wav");

            // MONSTER
//        monsterHeal = loadMedia("/sound/sfx/monster_heal.wav");
//        monsterDefeat = loadMedia("/sound/sfx/monster_defeat.wav");


            // FUNNY
        dunDunDun = loadMedia("/sound/sfx/funny_dun_dun_dun.wav");
        rimshot = loadMedia("/sound/sfx/funny_rimshot.wav");
//        bonk = loadMedia("/sound/sfx/funny_bonk.wav");

    }

    private Media loadMedia(String resourcePath) {
        return new Media(getClass().getResource(resourcePath).toExternalForm()); // don't know which one works
    }
//    private Media loadMedia(String resourcePath) {
//        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
//        return new Media(inputStream.toString());
//    }


    public static Audio getInstance() throws URISyntaxException { // to be used in singleton design pattern
        if (audio == null) {
            audio = new Audio();
        }
        return audio;
    }

    public void playSFX(final Media theSFX, final int theVolume) {
        sfxPlayer = new MediaPlayer(theSFX);
        sfxPlayer.setVolume(theVolume);
        sfxPlayer.play();
    }

    public void playMusic(final Media theMusic, final boolean theLoop, final int theVolume) {
        musicPlayer = new MediaPlayer(theMusic);
        musicPlayer.setVolume(theVolume);

        if (theLoop) {
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } else {
            musicPlayer.setCycleCount(1);
        }

        musicPlayer.play();
    }

    /**
     * Method to stop all audio: sounds and music alike.
     */
    public void stopAll() {
        musicPlayer.stop();
        sfxPlayer.stop();
    }

    public void stopMusic() {
        musicPlayer.stop();
    }

    public boolean isPlayingSFX() {
        return sfxPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public boolean isPlayingMusic() {
        return musicPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

//    public static void waitUntilSFXStops() {
//        while (isPlayingSFX()) {
//            DelayMachine.delay(1);
//        }
//    }
}
