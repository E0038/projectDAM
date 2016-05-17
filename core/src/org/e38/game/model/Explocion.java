package org.e38.game.model;

import org.e38.game.Recurses;
import org.e38.game.World;
import org.e38.game.model.npc.Criminal;

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

    @Override
    protected void onAreaAtack(Criminal[] targets) {
        super.onAreaAtack(targets);
        World.play(Recurses.EXPLOCION);
    }
}
