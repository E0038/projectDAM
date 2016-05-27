package org.e38.game.model.npc.cops;

import org.e38.game.Recurses;
import org.e38.game.World;
import org.e38.game.model.npc.Cop;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

/**
 * Created by sergi on 4/28/16.
 */
public class DamageOverTime extends Cop {
    static {
        copLevels = new CopLevel[]{
                new CopLevel(5, 0, 30), new CopLevel(10, 0, 50), new CopLevel(20, 0, 60)
        };
    }
    @Override
    public String getName() {
        return Recurses.POLICIA_ESCOPETA;
    }

    private Queue<CopLevel> levels = new ArrayDeque<CopLevel>();

    @Override
    public void onSpawn() {
        isAreaDamage = true;
        fireRate = 100; // 0.5s
        Collections.addAll(levels, copLevels);
        onUpgrade();//level 1
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        // TODO: 4/28/16
    }

    @Override
    public void onFire() {
        World.play(Recurses.SHOTGUN);
    }

    @Override
    public boolean isUpgradeAvailable() {
        return !levels.isEmpty();
    }

    @Override
    public void onUpgrade() {
        if (isUpgradeAvailable())
            nivel = levels.poll();
    }
}
