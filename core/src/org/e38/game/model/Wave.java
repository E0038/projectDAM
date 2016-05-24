package org.e38.game.model;

import org.e38.game.model.npc.Criminal;
import org.e38.game.model.npc.NPC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * container class
 */
public class Wave {
    private List<Criminal> criminals = new ArrayList<>();
    private long gap = 0;
    private transient int spawnPointer = -1;

    private boolean isAllSpawn = false;
    private boolean isClear = false;

    public Wave() {
    }

    public Wave(Collection<? extends Criminal> c) {
        criminals.addAll(c);
    }

    public boolean isAllSpawn() {
        return isAllSpawn;
    }

    public boolean isClear() {
        return isClear;
    }

    public List<Criminal> getCriminals() {
        return criminals;
    }

    public Wave setCriminals(List<Criminal> criminals) {
        this.criminals = criminals;
        return this;
    }

    public long getGap() {
        return gap;
    }

    public Wave setGap(long gap) {
        this.gap = gap;
        return this;
    }

    public void onUpdate(float delta) {
        if (!isClear) {
            if (spawnPointer < 0) {
                spawner();
            }
            updateCriminals(delta);
        }
    }

    private void updateCriminals(float delta) {
        boolean ck = true;
        System.out.println(criminals);
        for (Criminal criminal : criminals) {
            if (criminal.getState() != NPC.State.DEAD) {
                ck = false;
                if (criminal.getState() == NPC.State.ALIVE) criminal.onUpdate(delta);
            }
        }
        isClear = ck;
    }

    public void spawner() {
        new SheludedAction(gap) {
            @Override
            public void onFinish() {
                Wave.this.onFinish();
            }
        }.start();
    }

    private void onFinish() {
        int idx = ++spawnPointer;
        if (isAllSpawn || idx < criminals.size())
            criminals.get(idx).onSpawn();
        else isAllSpawn = true;
        if (!isAllSpawn) {
            new SheludedAction(gap) {
                @Override
                public void onFinish() {
                    Wave.this.onFinish();
                }
            }.start();
        }
    }


}
