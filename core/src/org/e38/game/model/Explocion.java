package org.e38.game.model;

/**
 * Created by sergi on 5/1/16.
 */
public class Explocion extends Bullet {
    public Explocion(float damage) {
        super(damage);
    }

    public Explocion(float protecionPenetration, float damage) {
        super(protecionPenetration, damage);
    }
    // TODO: 5/1/16  
}
