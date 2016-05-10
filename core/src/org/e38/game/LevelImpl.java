package org.e38.game;

import org.e38.game.model.Level;

/**
 * Created by sergi on 5/10/16.
 */
public class LevelImpl extends Level {
    protected LevelImpl(int initialCoins, int levelUID) {
        super(initialCoins, levelUID);
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onDestroy() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public Level fromJson() {
        return null;
    }
}
