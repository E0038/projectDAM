package org.e38.game.model.npc;

import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.Bullet;

import static org.e38.game.model.npc.NPC.State.*;

/**
 * Created by sergi on 4/20/16.
 */
public abstract class Cop implements NPC {

    protected static CopLevel[] copLevels;
    private final Vector2 noSpeed = new Vector2(0, 0);
    protected Vector2 posicion;
    protected int range;
    protected State state;
    /**
     * fire rate calculted with delta
     */
    protected long fireRate;
    protected CopLevel nivel;

    public abstract boolean mayFire();

    @Override
    public void onUpdate(float delta) {
// TODO: 4/28/16  
    }

    public CopLevel getNivel() {
        return nivel;
    }

    public Cop setNivel(CopLevel nivel) {
        this.nivel = nivel;
        return this;
    }

    public State getState() {
        return state;
    }

    public Cop setState(State state) {
        this.state = state;
        return this;
    }

    public int getRange() {
        return range;
    }

    public Cop setRange(int range) {
        this.range = range;
        return this;
    }

    public abstract void onFire();

    /**
     * determines if upgrade is supported
     */
    public abstract boolean isUpgradeAvailable();

    /**
     * upgrade to next level , if upgrade is not supported do nothing
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
    protected void onSell() {
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
    public State getCurrentState() {
        return state;
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
    public Vector2 getSpeed() {
        return noSpeed;
    }

    @Override
    public Vector2 getPosicion() {
        return posicion;
    }

    public Cop setPosicion(Vector2 posicion) {
        this.posicion = posicion;
        return this;
    }

    protected static class CopLevel {
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

        public Bullet newBullet() {
            return new Bullet(penetration, damage);
        }
    }
}
