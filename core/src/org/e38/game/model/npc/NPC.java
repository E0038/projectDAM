package org.e38.game.model.npc;

import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.GameObject;


public interface NPC extends GameObject {
    /**
     * nombre interno del npc
     */
    String getName();

    /**
     * called when npc god 0 HhPoins
     */
    void onDie();

    /**
     * called after object constructor
     */
    void spawn();

    /**
     * event
     */
    void onSpawn();

    /**
     * gets State of npc
     *
     * @return NPC State
     */
    State getState();

    /**
     * determines if npc still alive
     */
    boolean isAlive();

    /**
     * speed of npc
     */
    Vector2 getSpeed();

    Orientation getOrientation();

    NPC setOrientation(Orientation orientation);

    NPC setOrientationListener(OrientationListener listener);

    OrientationListener getOrientationListener();

    enum State {
        ALIVE, DEAD, DYING, SPAWING, DESPAWNING, DESPAWNED;
    }

    enum Orientation {
        LEFT, RIGHT, TOP, DOWN
    }

    interface OrientationListener {
        void onChange(Orientation old, Orientation nueva);
    }

}
