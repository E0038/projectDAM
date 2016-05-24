package org.e38.game.model;

import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.npc.Criminal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * container class
 */
public class Wave  implements GameObject{
    private List<Criminal> criminals=new ArrayList<>();
    private float gap=0f;

    public List<Criminal> getCriminals() {
        return criminals;
    }

    public Wave setCriminals(List<Criminal> criminals) {
        this.criminals = criminals;
        return this;
    }

    public float getGap() {
        return gap;
    }

    public Wave setGap(float gap) {
        this.gap = gap;
        return this;
    }


    public Wave() {
    }

    public Wave(Collection<? extends Criminal> c) {
       criminals.addAll(c);
    }

    @Override
    public void onUpdate(float delta) {
        //estado criminales
    }


}
