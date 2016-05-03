package org.e38.game;

import com.badlogic.gdx.Gdx;
import org.e38.game.model.Level;
import org.e38.game.model.npc.Cop;
import org.e38.game.model.npc.Criminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
public class World {
    public static final String FACIL = "FACIL", NORMAL = "NORMAL", DIFICIL = "DIFICIL";
    public static final float MAX_SPEED = 3.0f;
    public static float volume = 1f;
    public static float lastVolum = volume;
    /**
     * valueOf Enum dificultat the enum will be restored with Level.Dificultat.valueOf();
     * default: NORMAL
     */
    public static String selecteDificultat = NORMAL;
    public float speed = 1.0f;
    private Level level;

    public World(Level level) {
        this.level = level;

    }


    public static void onSwichMuteUnMute() {
        if (volume == 0f) onUnMute();
        else onMute();
    }

    public static void onUnMute() {
        volume = lastVolum;
    }

    public static void onMute() {
        lastVolum = volume;//save last audio level, to restore when unmute
        volume = 0f;
    }

    public void exit() {
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
}
