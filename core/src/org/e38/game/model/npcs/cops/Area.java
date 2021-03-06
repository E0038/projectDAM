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
public class Area extends Cop {

    static {
        copLevels = new CopLevel[]{
                new CopLevel(5, 0, 20), new CopLevel(7, 0, 40), new CopLevel(10, 0, 60)
        };
    }

    private Queue<CopLevel> levels = new ArrayDeque<>();

    @Override
    public String getName() {
        return Recurses.POLICIA_BAZOOKA;
    }

    @Override
    public void onSpawn() {
        setRange(120);
        isAreaDamage = true;
        fireRate = 3000;
        Collections.addAll(levels, copLevels);
        onUpgrade();//level 1
    }

    @Override
    public void onUpdate(float delta) {
        super.onUpdate(delta);
    }

    @Override
    public void onFire() {
        World.play(Recurses.RPG);
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
