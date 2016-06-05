package org.e38.game.model.npcs.cops;

import org.e38.game.utils.Recurses;
import org.e38.game.utils.World;
import org.e38.game.model.npcs.Cop;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

/**
 * Created by sergi on 4/28/16.
 */
public class DamageOverTime extends Cop {
    static {
        copLevels = new CopLevel[]{
                new CopLevel(7, 1, 30), new CopLevel(14, 2, 50), new CopLevel(30, 5, 60)
        };
    }
    @Override
    public String getName() {
        return Recurses.POLICIA_ESCOPETA;
    }

    private Queue<CopLevel> levels = new ArrayDeque<>();

    @Override
    public void onSpawn() {
        setRange(100);
        isAreaDamage = true;
        fireRate = 2000;
        Collections.addAll(levels, copLevels);
        onUpgrade();//level 1
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
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
