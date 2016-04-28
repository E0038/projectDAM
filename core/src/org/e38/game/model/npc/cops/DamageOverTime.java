package org.e38.game.model.npc.cops;

import org.e38.game.model.npc.Cop;

/**
 * Created by sergi on 4/28/16.
 */
public class DamageOverTime extends Cop {
    @Override
    public void onFire() {

    }

    // TODO: 4/28/16 all
    @Override
    public boolean mayFire() {
        return false;
    }

    @Override
    public boolean isUpgradeAvailable() {
        return false;
    }

    @Override
    public void onUpgrade() {

    }
}
