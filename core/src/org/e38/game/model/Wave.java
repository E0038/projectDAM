package org.e38.game.model;

import com.badlogic.gdx.maps.MapObject;
import org.e38.game.model.npcs.Cop;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.model.npcs.NPC;
import org.e38.game.screens.LevelScreen;
import org.e38.game.utils.SheludedAction;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * container class
 */
public class Wave {
    private List<Criminal> criminals = new ArrayList<>();
    private long gap = 0;
    private transient volatile AtomicInteger spawnPointer = new AtomicInteger(-1);

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

    public void onUpdate(float delta, LevelScreen screen) {
        if (!isClear) {
            if (spawnPointer.get() < 0) {
                spawner();
            }
            updateCriminals(delta, screen);
        }
    }

    private void updateCriminals(float delta, LevelScreen screen) {
        boolean ck = true;
//        System.out.println(criminals);
        Map<Cop, List<Criminal>> fireMapping = new HashMap<>();
        for (Criminal criminal : criminals) {
            if (criminal.getState() != NPC.State.DEAD) {
                ck = false;
                if (criminal.getState() == NPC.State.ALIVE) {
                    criminal.onUpdate(delta);
                    MapObject object = screen.getLevel().getPath().get(criminal.getPathPointer());
                    float x = (float) object.getProperties().get("x");
                    float y = (float) object.getProperties().get("y");
                    for (Cop cop : getCops(screen)) {
                        if (cop.isFireReady() && cop.getCircle().contains(x, y)) {
                            if (!fireMapping.containsKey(cop)) {
                                ArrayList<Criminal> auxCriminals = new ArrayList<>();
                                auxCriminals.add(criminal);
                                fireMapping.put(cop, auxCriminals);
                            } else {
                                fireMapping.get(cop).add(criminal);
                            }
                        }
                    }
                }
            }
        }
        for (Cop cop : fireMapping.keySet()) {
            if (cop.isAreaDamage())
                cop.fire(fireMapping.get(cop).toArray(new Criminal[1]));
            else cop.fire(fireMapping.get(cop).get(0));
        }
        isClear = ck;
    }

    private List<Cop> getCops(LevelScreen screen) {
        List<Cop> cops = new ArrayList<>();
        for (Plaza plaza :
                screen.getPlazas()) {
            if (plaza.isOcupada()) cops.add(plaza.getCop());
        }
        return cops;
    }

    public void spawner() {
        new SheludedAction(gap) {
            @Override
            public void onFinish() {
                Wave.this.launchWave();
            }
        }.start();
    }

    private void launchWave() {
        int idx = spawnPointer.incrementAndGet();
        if (isAllSpawn || idx < criminals.size())
            criminals.get(idx).onSpawn();
        else isAllSpawn = false;
        if (!isAllSpawn) {
            new SheludedAction(gap) {
                @Override
                public void onFinish() {
                    Wave.this.launchWave();
                }
            }.start();
        }
    }
}
