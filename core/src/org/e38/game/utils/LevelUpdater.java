package org.e38.game.utils;

import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npcs.Cop;

/**
 * Created by ALUMNEDAM on 24/05/2016.
 */
public class LevelUpdater {
    private Level level;

    public LevelUpdater(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        if (level.getLifes() <= 0) level.fail();
        if (level.wavePointer < level.waves.size()) {
            final Wave wave = level.waves.get(level.wavePointer);
            wave.onUpdate(delta);
            if (wave.isClear()) level.wavePointer++;
        }
        for (Cop cop : level.cops) {
            cop.onUpdate(delta);
        }
    }
}

