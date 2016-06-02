package org.e38.game.model.npcs.criminals;

import org.e38.game.model.npcs.Criminal;
import org.e38.game.utils.Recurses;

/**
 * Created by sergi on 5/20/16.
 */
public class LadronEscopeta extends Criminal {
    public LadronEscopeta() {
        name = Recurses.AnimatedCriminals.ladronEscopetaBueno.name();
        speed = 200;
        dodgeRate = 0.01f;
        protecion = 0.1f;
    }
}
