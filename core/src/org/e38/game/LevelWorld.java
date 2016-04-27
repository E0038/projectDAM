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
    public static String selecteDificultat;
    private static LevelWorld outInstance;
    //    private Level.Dificultat dificultat = Level.Dificultat.valueOf(NORMAL);//defecto normal
    private List<Cop> cops = new ArrayList<Cop>();
    private List<Criminal> aliveCriminals = new ArrayList<Criminal>();
    private Level level;

    public LevelWorld(Level level) {
        this.level = level;
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
