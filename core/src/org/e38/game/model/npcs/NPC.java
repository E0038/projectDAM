package org.e38.game.model.npcs;

import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.GameObject;

import java.util.List;


public interface NPC extends GameObject {

    /**
     * current posicion in pixels
     */
    Vector2 getPosition();

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
     * speed of npc in milis
     */
    long getSpeed();

    Orientation getOrientation();

    NPC setOrientation(Orientation orientation);

    List<OrientationListener> getOrientationListener();

    void addOrientationListener(OrientationListener listener);

    enum State {
        ALIVE, DEAD, DYING, SPAWING, DESPAWNING, DESPAWNED
    }

    enum Orientation {
        LEFT, RIGHT, TOP, DOWN
    }

    interface OrientationListener {
        void onChange(Orientation old, Orientation nueva);
    }

}
