package org.e38.game.model.npcs.criminals;

import org.e38.game.model.npcs.Criminal;
import org.e38.game.utils.Recurses;

/**
 * Created by sergi on 5/20/16.
 */
public class Espadon extends Criminal {
    public Espadon() {

        name = Recurses.AnimatedCriminals.enemigoEspadon.name();
        speed = 200;
        dodgeRate = 0.01f;
        protecion = 0.1f;
    }
}
