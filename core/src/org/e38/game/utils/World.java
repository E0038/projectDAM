package org.e38.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import org.e38.game.model.Level;
import org.e38.game.persistance.ProfileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
@SuppressWarnings("UtilityClassCanBeEnum")
public final class World {
//    public static final String FACIL = "EASY", NORMAL = "NORMAL", DIFICIL = "HARD";
    public static final float MAX_SPEED = 3.0f;
    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 600;
    /**
     * valueOf Enum dificultat the enum will be restored with Level.Dificultat.valueOf();
     * default: NORMAL
     */
    public static String selecteDificultat = Level.Dificultat.NORMAL.name();
    public static float speed = 1.0f;
    public static List<Level> levels = new ArrayList<>();
    private static float volume = 0.5f;
    public static float lastVolum = volume;
    private static Recurses recurses;

    private World() {
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

    public static boolean isMuted() {
        return volume == 0f;
    }

    public static float getVolume() {
        return volume;
    }

    public static Recurses getRecurses() {
        return recurses;
    }

    public static void setRecurses(Recurses recurses) {
        World.recurses = recurses;
    }

    public static void exit() {
        try {
            ProfileManager.getInstance().stopAutoSave();
            ProfileManager.getInstance().persistentSave();
        } catch (IOException e) {
            Gdx.app.log(ProfileManager.class.getName(), "save failed", e);
        }
        Gdx.app.exit();
    }

    /**
     * toggle speed increment by 1 unit, if reach limit reset to initial.
     */
    public static void changeSpeed() {
        if (speed < MAX_SPEED) {
            speed++;
        } else {
            speed = 1f;
        }
    }
}
