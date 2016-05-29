package org.e38.game.model.npcs.criminals;

import org.e38.game.utils.Recurses;
import org.e38.game.model.npcs.Criminal;

/**
 * Created by sergi on 5/20/16.
 */
public class Bicicleta extends Criminal {
    public Bicicleta() {
        super.name = Recurses.AnimatedCriminals.bicicletaFinal.name();
    }
}
