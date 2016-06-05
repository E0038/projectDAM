package org.e38.game.utils;

import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.screens.LevelScreen;

/**
 * Created by sergi on 24/05/2016.
 */
public class LevelUpdater {
    public static long updateTime = System.currentTimeMillis();
    private final LevelScreen screen;
    private Level level;

    public LevelUpdater(LevelScreen screen) {
        this.screen = screen;
        this.level = screen.getLevel();
    }

    public void update(float delta) {
        updateTime = System.currentTimeMillis();
        if (level.getLifes() <= 0) {
            level.fail();
        } else if (level.wavePointer < level.waves.size()) {

            final Wave wave = level.waves.get(level.wavePointer);
            wave.setWaveGap((long) level.waveGap);
            wave.onUpdate(delta, screen);
            if (wave.isClear()) level.wavePointer++;
        } else {
            level.setIsWined((byte) level.getLifes());
            level.onEnd();
        }


    }
}

