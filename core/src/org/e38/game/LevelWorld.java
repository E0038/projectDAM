package org.e38.game;

import org.e38.game.model.Level;
import org.e38.game.model.npc.Cop;
import org.e38.game.model.npc.Criminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
public class LevelWorld {
    public static final String FACIL = "FACIL", NORMAL = "NORMAL", DIFICIL = "DIFICIL";
    public static float volume = 1f;
    public static float lastVolum = volume;
    public static String selecteDificultat;
    private static LevelWorld outInstance;
    //    private Level.Dificultat dificultat = Level.Dificultat.valueOf(NORMAL);//defecto normal
    private List<Cop> cops = new ArrayList<Cop>();
    private List<Criminal> aliveCriminals = new ArrayList<Criminal>();
    private Level level;
    private boolean isMuted = false;

    public LevelWorld(Level level) {
        this.level = level;
    }


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

//    public static LevelWorld getWorld(Level level) {
//        if (outInstance == null) {
//            outInstance = new LevelWorld(level);
//        } else {
//            if (outInstance.level != level) {
//                outInstance = new LevelWorld(level);
//            }
//        }
//        return outInstance;
//    }

}
