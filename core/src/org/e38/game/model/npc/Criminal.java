package org.e38.game.model.npc;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sergi on 4/20/16.
 */
public abstract class Criminal implements Hittable, NPC {
    protected State state = State.SPAWING;
    protected float hpPoints;
    /**
     * value between 0 and 1
     */
    protected float dodgeRate;
    protected float protecion;
    protected Vector2 position;
    protected Vector2 speed;

    public float getProtecion() {
        return protecion;
    }

    @Override
    public State getCurrentState() {
        return state;
    }

    @Override
    public Vector2 getSpeed() {
        return speed;
    }

    @Override
    public Vector2 getPosicion() {
        return position;
    }

    /**
     * get the points (Money) of Criminal
     *
     * @return the points that give on die
     */
    public abstract int getPoints();

    /**
     * determines if is dead
     */
    protected abstract boolean isDead();

    @Override
    public void onHit(float damage) {
        if (damage < 0) damage = 0;
        hpPoints -= damage;
        if (hpPoints <= 0) onDie();
    }
}
