package org.e38.game.persistance;

import org.e38.game.model.Level;

/**
 * Created by sergi on 5/5/16.
 */
public class Configuration {
    public float volume = 0.5f;
    /**
     * One of EASY , NORMAL , HARD
     */
    public String selecteDificultat = Level.Dificultat.NORMAL.name();
    public float speed = 1.0f;
    //etc
}
