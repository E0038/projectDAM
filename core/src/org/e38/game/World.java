package org.e38.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.e38.game.grafics.Recurses;
import org.e38.game.model.Level;
import org.e38.game.persistance.ProfileManager;

import java.io.IOException;

/**
 * Created by sergi on 4/20/16.
 */
public class World {
    public static final String FACIL = "FACIL", NORMAL = "NORMAL", DIFICIL = "DIFICIL";
    public static final float MAX_SPEED = 3.0f;
    /**
     * valueOf Enum dificultat the enum will be restored with Level.Dificultat.valueOf();
     * default: NORMAL
     */
    public static String selecteDificultat = NORMAL;
    private static float volume = 0.5f;
    public static float lastVolum = volume;
    public static float speed = 1.0f;
    private Level level;
    private static Recurses recurses;

    public World(Level level) {
        this.level = level;

    }

    public World() {
    }

    public static void onSwichMuteUnMute() {
        if (volume == 0f) onUnMute();
        else onMute();
    }

    public static void onUnMute() {
        volume = lastVolum != 0f ? lastVolum : 0.5f;
    }

    public static void onMute() {
        lastVolum = volume;//save last audio level, to restore when unmute
        volume = 0f;
    }

    public static void play(Sound sound) {
        sound.play(volume);
    }

    public static void play(Music music) {
        music.setVolume(volume);
        music.play();
    }

    public static void volumeChange(float volumne) {
        if (volumne > 1f) {
            volumne = 1f;
        } else if (volumne < 0f) {
            volumne = 0f;
        }
        World.volume = volumne;
    }

    public static void setRecurses(Recurses recurses) {
        World.recurses = recurses;
    }
    public static float getVolume() {
        return volume;
    }

    public void exit() {
        try {
            ProfileManager.getProfile().persistSave();
        } catch (IOException e) {
            Gdx.app.log(ProfileManager.class.getName(), "save failed", e);
        }
        Gdx.app.exit();
    }

    /**
     * toggle speed increment by 1 unit, if reach limit reset to initial.
     */
    public void changeSpeed() {
        if (speed < MAX_SPEED) {
            speed++;
        } else {
            speed = 1f;
        }
    }

    public static Recurses getRecurses() {
        return recurses;
    }
}
