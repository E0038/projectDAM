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
    public static String selecteDificultat;
    public float speed = 1.0f;
//    private static World outInstance;
    //    private Level.Dificultat dificultat = Level.Dificultat.valueOf(NORMAL);//defecto normal
    private List<Cop> cops = new ArrayList<Cop>();
    private List<Criminal> aliveCriminals = new ArrayList<Criminal>();
    private Level level;
    private boolean isMuted = false;

    public World(Level level) {
        this.level = level;
    }

//    public static World getWorld(Level level) {
//        if (outInstance == null) {
//            outInstance = new World(level);
//        } else {
//            if (outInstance.level != level) {
//                outInstance.level = level;
//            }
//        }
//        return outInstance;
//    }

    public void onSwichMuteUnMute() {
        if (volume == 0f) onUnMute();
        else onMute();
    }

    public void onUnMute() {
        volume = lastVolum;
    }

    public void onMute() {
        lastVolum = volume;
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
