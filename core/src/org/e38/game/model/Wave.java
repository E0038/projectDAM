package org.e38.game.model;

import org.e38.game.model.npc.Criminal;

import java.util.ArrayList;
import java.util.Collection;

/**
 * container class
 */
public class Wave extends ArrayList<Criminal> {
    public Wave(int initialCapacity) {
        super(initialCapacity);
    }

    public Wave() {
    }

    public Wave(Collection<? extends Criminal> c) {
        super(c);
    }
}
