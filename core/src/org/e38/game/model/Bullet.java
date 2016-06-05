package org.e38.game.model;

import org.e38.game.model.npcs.Cop;
import org.e38.game.model.npcs.Criminal;

/**
 * Created by sergi on 4/20/16.
 */
public class Bullet {
    private float protecionPenetration;
    private float damage;

    public Bullet(float protecionPenetration, float damage,Cop cop) {
        this.protecionPenetration = protecionPenetration;
        this.damage = damage;
    }

    public final void fireSingle(Criminal to) {
        onSingleAtack(to);
    }

    protected void onSingleAtack(Criminal primaryTarget) {
        double hitIndex = Math.random();
        if (hitIndex > primaryTarget.getDodgeRate()) {
            float damageReduccion = primaryTarget.getProtecion() - getProtecionPenetration();
            if (damageReduccion < 0) damageReduccion = 0;
            primaryTarget.onHit(getDamage() - damageReduccion);
        } else {
            primaryTarget.onDodge();
        }
    }

    public float getProtecionPenetration() {
        return protecionPenetration;
    }

    public float getDamage() {
        return damage;
    }

    public final void fireArea(Criminal[] criminals) {
        onAreaAtack(criminals);
    }

    protected void onAreaAtack(Criminal[] targets) {
        for (Criminal target : targets) {
            onSingleAtack(target);
        }
    }
}
