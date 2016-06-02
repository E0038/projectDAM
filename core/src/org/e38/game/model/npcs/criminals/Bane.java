package org.e38.game.model.npcs.criminals;

import org.e38.game.model.Level;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.utils.Recurses;

/**
 * Created by sergi on 5/20/16.
 */
public class Bane extends Criminal {
    public Bane() {
        init();
    }

    public Bane(Level level) {
        super(level);
        init();
    }

    public void init() {
        name = Recurses.AnimatedCriminals.bane.name();
        speed = 150;
        dodgeRate = 0.01f;
        protecion = 0.1f;
    }
}
