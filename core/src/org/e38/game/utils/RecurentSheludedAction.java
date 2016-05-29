package org.e38.game.utils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergi on 5/29/16.
 */
public abstract class RecurentSheludedAction  extends Thread {
    private volatile AtomicLong durationInterval = new AtomicLong();
    private volatile AtomicBoolean stop = new AtomicBoolean(false);

    public RecurentSheludedAction(long durationInterval) {

        this.durationInterval.set(durationInterval);
    }

    public synchronized long getDurationInterval() {
        return durationInterval.get();
    }

    public synchronized void setDurationInterval(long durationInterval) {
        this.durationInterval.set(durationInterval);
    }

    public synchronized boolean getStop() {
        return stop.get();
    }

    public synchronized void setStop(boolean stop) {
        this.stop.set(stop);
    }

    @Override
    public void run() {
        while (!stop.get()) {
            SheludedAction sheludedAction = new SheludedAction(durationInterval.get()) {
                @Override
                public void onFinish() {
                    onFinishInterval();
                }
            };
            sheludedAction.start();
            try {
                sheludedAction.join();
            } catch (InterruptedException ignored) {
            }
        }
    }

    public abstract void onFinishInterval();

}
