package org.e38.game.model.npc;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sergi on 4/20/16.
 */
public abstract class Criminal implements Hittable {
    protected State state = State.SPAWING;
    protected float hpPoints;
    /**
     * value between 0 and 1
     */
    protected float dodgeRate;
    protected float protecion;
    protected Vector2 position;
    protected Vector2 speed;
    protected Orientation orientation = Orientation.LEFT;
    protected OrientationListener listener;

    public float getProtecion() {
        return protecion;
    }

    public Criminal setProtecion(float protecion) {
        this.protecion = protecion;
        return this;
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

    @Override
    public void spawn() {
        state = State.SPAWING;
        onSpawn();
        state = State.ALIVE;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public boolean isAlive() {
        return state == State.ALIVE;
    }

    @Override
    public Vector2 getSpeed() {
        return speed;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Criminal setOrientation(Orientation orientation) {
        Orientation old = this.orientation;
        this.orientation = orientation;
        if (listener != null)  listener.onChange(old, this.orientation);
        return this;
    }

    @Override
    public NPC setOrientationListener(OrientationListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public OrientationListener getOrientationListener() {
        return listener;
    }

    public Criminal setSpeed(Vector2 speed) {
        this.speed = speed;
        return this;
    }

    public Criminal setState(State state) {
        this.state = state;
        return this;
    }

    /**
     * determines if is dead
     */
    public boolean isDead() {
        return state == State.DEAD;
    }

    public float getHpPoints() {
        return hpPoints;
    }

    public Criminal setHpPoints(float hpPoints) {
        this.hpPoints = hpPoints;
        return this;
    }

    @Override
    public float getDodgeRate() {
        return dodgeRate;
    }

    @Override
    public void onHit(float damage) {
        if (damage < 0) damage = 0;
        hpPoints -= damage;
        if (hpPoints <= 0) onDie();
    }

    public Criminal setDodgeRate(float dodgeRate) {
        this.dodgeRate = dodgeRate;
        return this;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Criminal setPosition(Vector2 position) {
        this.position = position;
        return this;
    }
}
