package org.e38.game.model.npc.criminals;

import org.e38.game.model.npc.Criminal;

/**
 * Created by sergi on 5/3/16.
 */
public class SimpleCriminal extends Criminal {
    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public void onDodge() {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void onDie() {

    }


    @Override
    public void onSpawn() {

    }

    @Override
    public void onUpdate(float delta) {

    }
}
