package org.e38.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import org.e38.game.model.Level;
import org.e38.game.screens.LevelScreen;

import java.util.Random;

/**
 * Created by ALUMNEDAM on 24/05/2016.
 */
public class GameUpdater {
    private Level level;
    private LevelScreen levelScreen;
    private boolean canSpawn = true;
    private float timeLast = 0;

    public GameUpdater(Level level) {
        this.level = level;
    }

    //TODO
    private void renderCriminals(Batch batch) {
        MapObject spawn = level.getLayer().getObjects().get("spawn");
        float x = (float) spawn.getProperties().get("x");
        float y = (float) spawn.getProperties().get("y");

        if (canSpawn) {
//            aliveCriminals.add(level.waves.get(waveCount).get(criminalCount));
            x += new Random().nextInt(60);

//            int idx = aliveCriminals.get(aliveCriminals.size()).getPathPointer();
//            level.getPath().get(idx);
            canSpawn = false;
        } else {
            if (timeLast > level.waves.get(levelScreen.getWaveCount()).getGap()) {
                timeLast = 0;
                canSpawn = true;
            }
            timeLast += Gdx.graphics.getDeltaTime();
        }

    }
}
