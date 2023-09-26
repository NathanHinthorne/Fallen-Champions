package FallenChampions.view;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class will play audio when called upon by
 * another method.
 *
 * @author Brendan Smith
 * @author Nathan Hinthorne
 * @version 1.0 - 5/20/23
 */
public final class AudioManager {
    private static AudioManager audioManager; // to be used in singleton design pattern

    // Constants

    private static final double VOLUME_MODIFIER = 0.6;



    // Separate media players for music and sound effects
    private MediaPlayer musicPlayer;
    private MediaPlayer sfxPlayer;


    // BUTTON SOUNDS
    public Media mouseClick;
    public Media menu1;
    public Media menu2;
    public Media menu3;
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


    // HERO
    // (step1, step2, step3, step4 are from Valve's Team Fortress 2)
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
    public Media startUp; // find place to use this
    public Media heroBagOpen;
    public Media heroBagClose;
    public Media heroBagFull;
//    public final File heroCollectWeapon;

    // DIALOGUE
    public Media talking;
    public Media talking2;

    // FUNNY SFX
    public Media dunDunDun;
    public Media rimshot;
//    public final File bonk;

    private AudioManager() { // prevent outside instantiation
        preloadMediaFiles();
    }

    private void preloadMediaFiles() {
        // Preload music files
        ambientSong = loadMedia("/audio/music/music_ambient.wav");
        battleSong = loadMedia("/audio/music/music_battle.wav");
        startingAnewSong = loadMedia("/audio/music/music_starting_anew.wav");
        triumpantFinishSong = loadMedia("/audio/music/music_triumphant_finish.wav");
        rickRollSong = loadMedia("/audio/music/music_rickroll.wav");
        conkerSong = loadMedia("/audio/music/music_conker.wav");
        hackerSong = loadMedia("/audio/music/music_hacker.wav");
        genosThemeSong = loadMedia("/audio/music/music_genos_theme.wav");
        gasterBossSong = loadMedia("/audio/music/music_gaster_boss.wav");
        gasterAmbientSong = loadMedia("/audio/music/music_gaster_ambient.wav");

        // Preload sfx files

            // BUTTONS
        mouseClick = loadMedia("/audio/sfx/button_click.wav");
        menu1 = loadMedia("/audio/sfx/button_menu_1.wav");
        menu2 = loadMedia("/audio/sfx/button_menu_2.wav");
        menu3 = loadMedia("/audio/sfx/button_menu_3.wav");
        swishOn = loadMedia("/audio/sfx/hero_stats_on.wav");
        swishOff = loadMedia("/audio/sfx/hero_stats_off.wav");
        error = loadMedia("/audio/sfx/button_error.wav");
        beginGame = loadMedia("/audio/sfx/button_spawn.wav");
        infoPopup = loadMedia("/audio/sfx/info_popup.wav");
        titleScreen = loadMedia("/audio/sfx/title_screen.wav");
        startUp = loadMedia("/audio/sfx/button_startup.wav"); // find place to use this (starting up game or level?)
//        confirm = loadMedia("/audio/sfx/confirm.wav");

            // HERO
        step1 = loadMedia("/audio/sfx/hero_step1.wav");
        step2 = loadMedia("/audio/sfx/hero_step2.wav");
        step3 = loadMedia("/audio/sfx/hero_step3.wav");
        step4 = loadMedia("/audio/sfx/hero_step4.wav");
        lockedDoor = loadMedia("/audio/sfx/locked_door.wav");
        encounter = loadMedia("/audio/sfx/monster_encounter.wav");
        heroDrinkPotion = loadMedia("/audio/sfx/hero_drink_potion.wav");
        heroLevelUp = loadMedia("/audio/sfx/hero_level_up.wav");
        heroDefeat = loadMedia("/audio/sfx/hero_defeat.wav");
        heroOof = loadMedia("/audio/sfx/hero_oof.wav");
        heroCollectPotion = loadMedia("/audio/sfx/hero_collect_potion.wav");
        heroCollectPillar = loadMedia("/audio/sfx/hero_collect_pillar.wav");
        heroBagOpen = loadMedia("/audio/sfx/hero_bag_open.wav");
        heroBagClose = loadMedia("/audio/sfx/hero_bag_close.wav");
        heroBagFull = loadMedia("/audio/sfx/hero_bag_full.wav");

            // MONSTER
//        monsterHeal = loadMedia("/audio/sfx/monster_heal.wav");
//        monsterDefeat = loadMedia("/audio/sfx/monster_defeat.wav");

            // DIALOGUE
        talking = loadMedia("/audio/sfx/dialogue_talking.wav");
        talking2 = loadMedia("/audio/sfx/dialogue_talking2.wav");

            // FUNNY
        dunDunDun = loadMedia("/audio/sfx/funny_dun_dun_dun.wav");
        rimshot = loadMedia("/audio/sfx/funny_rimshot.wav");
//        bonk = loadMedia("/audio/sfx/funny_bonk.wav");

    }

    private Media loadMedia(final String resourcePath) {
        return new Media(getClass().getResource(resourcePath).toExternalForm()); // don't know which one works
    }
//    private Media loadMedia(String resourcePath) {
//        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
//        return new Media(inputStream.toString());
//    }

    public static AudioManager getInstance() { // to be used in singleton design pattern
        if (audioManager == null) {
            audioManager = new AudioManager();
        }
        return audioManager;
    }

    public void playSFX(final Media theSFX, final double theVolume) {
        // anonymous inner class that implements the Runnable interface
        Platform.runLater(() -> {
            sfxPlayer = new MediaPlayer(theSFX);
            sfxPlayer.setVolume(theVolume * VOLUME_MODIFIER);
            sfxPlayer.play();
        });
    }

    public void playMusic(final Media theMusic, final boolean theLoop, final double theVolume) {

        // anonymous inner class that implements the Runnable interface
        Platform.runLater(() -> {
            musicPlayer = new MediaPlayer(theMusic);
            musicPlayer.setVolume(theVolume * VOLUME_MODIFIER);

            if (theLoop) {
                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            } else {
                musicPlayer.setCycleCount(1);
            }

            musicPlayer.play();
        });
    }

    /**
     * Method to stop all audio: sounds and music alike.
     */
    public void stopAll() {
        musicPlayer.stop();
        sfxPlayer.stop();
    }

    /**
     * Method to stop all music
     */
    public void stopMusic() {
        musicPlayer.stop();
    }

    /**
     * Checks if the sfxPlayer is playing a sound effect.
     * @return true if the sfxPlayer is playing a sound effect, false otherwise
     */
    public boolean isPlayingSFX() {
        return sfxPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * Checks if the musicPlayer is playing music.
     * @return true if the musicPlayer is playing music, false otherwise
     */
    public boolean isPlayingMusic() {
        return musicPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }


}
