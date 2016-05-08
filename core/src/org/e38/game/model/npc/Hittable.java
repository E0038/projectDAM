package org.e38.game.model.npc;

/**
 * Created by sergi on 4/20/16.
 */
public interface Hittable extends NPC {
    /**
     * @return a float between 0 and 1
     */
    float getDodgeRate();

    /**
     * called when was hitted
     *
     * @param damage damage that Hit does. miss, dodge and damage reduccion is already calculated  NOT DO AGAIN!!
     */
    void onHit(float damage);

    /**
     * called when dodge a hit
     */
    void onDodge();

}
