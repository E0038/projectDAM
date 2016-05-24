package org.e38.game;

import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.screens.LevelScreen;

/**
 * Created by ALUMNEDAM on 24/05/2016.
 */
public class GameUpdater {
    private Level level;
    private LevelScreen levelScreen;
    private float timeLast = 0;

    public GameUpdater(Level level) {
        this.level = level;
    }

    //TODO
    public void update(float delta) {
        if (level.wavePointer < level.waves.size()) {
            System.out.println(level.wavePointer);
            final Wave wave = level.waves.get(level.wavePointer);
            wave.onUpdate(delta);
            if (wave.isClear()) level.wavePointer++;
        }
    }

}

