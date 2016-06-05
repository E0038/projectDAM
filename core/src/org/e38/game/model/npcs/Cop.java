package org.e38.game.model.npcs;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.Bullet;
import org.e38.game.utils.LevelUpdater;
import org.e38.game.utils.Recurses;

import java.util.ArrayList;
import java.util.List;

import static org.e38.game.model.npcs.NPC.State.*;

/**
 * Created by sergi on 4/20/16.
 */
public abstract class Cop implements NPC {

    protected static CopLevel[] copLevels;
    protected Vector2 posicion = new Vector2(0, 0);
    protected float range;
    protected State state;
    protected long lastFire = 0L;
    protected volatile boolean isAreaDamage = false;
    /**
     * fire rate calculted with delta
     */
    protected long fireRate;
    protected CopLevel nivel;
    protected Orientation orientation = Orientation.RIGHT;
    protected List<OrientationListener> orientationListeners = new ArrayList<>();
    private Circle circle;

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public long getFireRate() {
        return fireRate;
    }

    public Cop setFireRate(long fireRate) {
        this.fireRate = fireRate;
        return this;
    }

    public boolean isAreaDamage() {
        return isAreaDamage;
    }

    public Cop setAreaDamage(boolean areaDamage) {
        isAreaDamage = areaDamage;
        return this;
    }

    /**
     * default impl, override for extra updates
     */
    @Override
    public void onUpdate(float delta) {
    }

    @Override
    public Vector2 getPosition() {
        return posicion;
    }

    @Override
    public String getName() {
        return Recurses.POLICIA_BUENO;
    }

    /**
     * default impl ,this is called when cop was removed
     */
    @Override
    public void onDie() {
        state = DEAD;
    }

    @Override
    public void spawn() {
        circle = new Circle(posicion, range);
        state = SPAWING;
        onSpawn();
        state = ALIVE;
    }

    /**
     * default impl does nothing ,override to initialize
     */
    @Override
    public void onSpawn() {
    }

    @Override
    public State getState() {
        return state;
    }

    public Cop setState(State state) {
        this.state = state;
        return this;
    }

    @Override
    public boolean isAlive() {
        return state == ALIVE;
    }

    /**
     * all are in Static places
     *
     * @return 0
     */
    @Override
    public long getSpeed() {
        return -1;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Cop setOrientation(Orientation orientation) {
        Orientation old = this.orientation;
        this.orientation = orientation;
        for (OrientationListener listener : orientationListeners) {
            listener.onChange(old, this.orientation);
        }
        return this;
    }

    @Override
    public List<OrientationListener> getOrientationListener() {
        return orientationListeners;
    }

    @Override
    public void addOrientationListener(OrientationListener listener) {
        orientationListeners.add(listener);
    }

    public Cop setPosicion(Vector2 posicion) {
        this.posicion = posicion;
        setRange(range);// circle
        return this;
    }

    public void setRange(float range) {
        this.range = range;
        circle = new Circle(posicion, this.range);
    }

    public CopLevel getNivel() {
        return nivel;
    }

    public Cop setNivel(CopLevel nivel) {
        this.nivel = nivel;
        return this;
    }

    public void fire(Criminal... criminal) {
        lastFire = LevelUpdater.updateTime;
        if (criminal.length == 0) return;
        Bullet bullet = new Bullet(nivel.penetration, nivel.damage, this);
        onFire();
        if (isAreaDamage) {
            bullet.fireArea(criminal);
        } else {
            bullet.fireSingle(criminal[0]);
        }
    }

    public abstract void onFire();

    /**
     * determines if upgrade is supported
     */
    public abstract boolean isUpgradeAvailable();

    /**
     * upgrade to nextPosition level , if upgrade is not supported do nothing
     */
    public abstract void onUpgrade();

    /**
     * Sell a Cop
     *
     * @return the value of Cop
     */
    public float dismiss() {
        onSell();
        onDie();//eliminar
        return nivel == null ? 0f : nivel.getPrecioVenta();
    }

    /**
     * default impl does nothing , override to play sell animations
     */
    public void onSell() {
    }

    public boolean isFireReady() {
        return LevelUpdater.updateTime - lastFire > fireRate;
    }

    public static class CopLevel {
        /**
         * el 25% del precio de compra
         */
        public static final float RESTRICION_VENTA = 0.25f;
        private float damage, penetration, precioCompra;

        public CopLevel(float damage, float penetration, float precioCompra) {
            this.damage = damage;
            this.penetration = penetration;
            this.precioCompra = precioCompra;
        }

        public float getDamage() {
            return damage;
        }

        public float getPenetration() {
            return penetration;
        }

        public float getPrecioCompra() {
            return precioCompra;
        }

        public float getPrecioVenta() {
            return precioCompra * RESTRICION_VENTA;
        }

//        public Bullet newBullet() {
//            return new Bullet(penetration, damage, );
//        }
    }
}
