package org.e38.game;

import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Criminal;
import org.e38.game.screens.LevelScreen;

/**
 * Created by ALUMNEDAM on 24/05/2016.
 */
public class GameUpdater {
    private Level level;

    public GameUpdater(Level level) {
        this.level = level;
    }

    public void update(float delta) {
        if (level.wavePointer < level.waves.size()) {
            final Wave wave = level.waves.get(level.wavePointer);
            wave.onUpdate(delta);
            if (wave.isClear()) level.wavePointer++;
        }
    }

}

