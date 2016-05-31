package org.e38.game.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by sergi on 5/24/16.
 */
public abstract class SheludedAction extends Thread {
    public static final int MILLIS = 1000 / 60;
    private static int count;
    protected long duration;

    public SheludedAction(long duration) {
        this.duration = duration;
        setName("SheludedAction-" + count++);
    }

    @Override
    public void run() {
        long init = System.currentTimeMillis();
        while ((System.currentTimeMillis() - init) < duration) {
            try {
                Thread.sleep(MILLIS);
            } catch (InterruptedException e) {
                Gdx.app.error(getClass().getName(), e.getMessage(), e);
            }
        }
        onFinish();
    }

    public abstract void onFinish();

}
