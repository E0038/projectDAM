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
public class Rapido extends Cop {
    static {
        copLevels = new CopLevel[]{
                new CopLevel(1, 0, 10), new CopLevel(3, 0, 30), new CopLevel(6, 0, 60)
        };
    }

    private Queue<CopLevel> levels = new ArrayDeque<>();

    @Override
    public String getName() {
        return Recurses.POLICIA_BUENO;
    }

    @Override
    public void onSpawn() {
        isAreaDamage = true;
        fireRate = 30; // 0.5s
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
        World.play(Recurses.GUN);
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
