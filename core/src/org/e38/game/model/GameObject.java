package org.e38.game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sergi on 4/20/16.
 */
public interface GameObject {

    void onUpdate(float delta);

    /**
     * current posicion in pixels
     */
    Vector2 getPosicion();
}
