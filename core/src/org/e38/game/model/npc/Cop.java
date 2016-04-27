package org.e38.game.model.npc;

import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.Bullet;

/**
 * Created by sergi on 4/20/16.
 */
public abstract class Cop implements NPC {


    private final Vector2 noSpeed = new Vector2(0, 0);
    protected Vector2 position;

    protected abstract void onFireStart();

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
    public abstract int sell();

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
        return position;
    }

    protected class UpgradeLevel {
        private float damage, penetration;

        UpgradeLevel(float damage, float penetration) {
            this.damage = damage;
            this.penetration = penetration;
        }

        public Bullet newBullet() {
            return new Bullet(penetration, damage);
        }
    }

}
