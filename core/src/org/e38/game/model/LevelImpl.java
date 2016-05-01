package org.e38.game.model;

/**
 * Created by sergi on 5/1/16.
 */
public class LevelImpl extends Level {

    public LevelImpl(){
        super(100);
    }

    protected LevelImpl(int initialCoins) {
        super(initialCoins);
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onDestroy() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onRestart() {

    }
}
