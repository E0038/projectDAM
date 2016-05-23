package org.e38.game.model.npc;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import org.e38.game.Recurses;
import org.e38.game.model.Level;

/**
 * Created by sergi on 4/20/16.
 */
public class Criminal implements Hittable {
    protected State state = State.SPAWING;
    protected float hpPoints;
    /**
     * value between 0 and 1
     */
    protected float dodgeRate;
    protected float protecion;
    protected Vector2 speed;
    protected Orientation orientation = Orientation.LEFT;
    protected OrientationListener listener;
    protected int pathPointer = 0;
    protected Level level;
    protected float totalHpPoins = 10f;
    protected String name = Recurses.AnimatedCriminals.bane.name();

    public Criminal() {
    }

    public Criminal(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public Criminal setLevel(Level level) {
        this.level = level;
        return this;
    }

    public OrientationListener getListener() {
        return listener;
    }

    public Criminal setListener(OrientationListener listener) {
        this.listener = listener;
        return this;
    }

    public float getProtecion() {
        return protecion;
    }

    public Criminal setProtecion(float protecion) {
        this.protecion = protecion;
        return this;
    }

    public void nextPosition() {
        pathPointer++;
    }


    /**
     * get the points (Money) of Criminal
     *
     * @return the points that give on die
     */
    public int getPoints() {
        //// TODO: 5/23/16 find poins ideal criteria formula
        return (int) (totalHpPoins * dodgeRate * (speed.x + speed.y));
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

    @Override
    public void onDodge() {

    }

    public Criminal setDodgeRate(float dodgeRate) {
        this.dodgeRate = dodgeRate;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onDie() {

    }

    @Override
    public void spawn() {
        state = State.SPAWING;
        onSpawn();
        state = State.ALIVE;
    }

    @Override
    public void onSpawn() {
        state = State.ALIVE;
        pathPointer = 0;
        hpPoints = totalHpPoins;
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
        if (listener != null) listener.onChange(old, this.orientation);
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

    @Override
    public void onUpdate(float delta) {
        //// TODO: 5/23/16
    }

    @Override
    public Vector2 getPosition() {
        return getPositionRetativeTo(level);
    }

    protected Vector2 getPositionRetativeTo(Level level) {
        float x, y;
        MapProperties camino = level.getPath().get(pathPointer).getProperties();
        x = (float) camino.get("x");
        y = (float) camino.get("y");
        return new Vector2(x, y);
    }


}
