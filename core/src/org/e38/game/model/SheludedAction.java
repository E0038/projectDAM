package org.e38.game.model;

/**
 * Created by sergi on 5/24/16.
 */
public abstract class SheludedAction {
    public static final int MILLIS = 1000 / 60;
    private long duration;

    public SheludedAction(long duration) {
        this.duration = duration;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long init = System.currentTimeMillis();
                while ((System.currentTimeMillis() - init) < duration) {
                    try {
                        Thread.sleep(MILLIS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                onFinish();
            }
        }).start();
    }

    public abstract void onFinish();

}
