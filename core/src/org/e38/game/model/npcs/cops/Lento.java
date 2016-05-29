package org.e38.game.model.npcs.cops;

import org.e38.game.utils.World;
import org.e38.game.utils.Recurses;
import org.e38.game.model.npcs.Cop;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

/**
 * Created by sergi on 4/28/16.
 */
public class Lento extends Cop {
    static {
        copLevels = new CopLevel[]{
                new CopLevel(10, 0, 25), new CopLevel(20, 0, 50), new CopLevel(40, 0, 100)
        };
    }

    private Queue<CopLevel> levels = new ArrayDeque<CopLevel>();


    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
        // TODO: 4/28/16
    }

    @Override
    public void onFire() {
        World.play(Recurses.SNIPER_SHOT);
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

    @Override
    public void onSpawn() {
        isAreaDamage = true;
        fireRate = 60 * 2; // 2s a 60fps
        Collections.addAll(levels, copLevels);
        onUpgrade();//level 1
    }

    @Override
    public String getName() {
        return Recurses.SNIPER_BUENO;
    }
}
