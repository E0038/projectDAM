package org.e38.game.hud;


import com.badlogic.gdx.scenes.scene2d.Stage;
import org.e38.game.model.Plaza;
import org.e38.game.model.npcs.Cop;

/**
 * Created by ALUMNEDAM on 26/05/2016.
 */
public interface LowerBar {

    void updateBar(int money);

    void updateBar(int money, Cop cop);

    Plaza getPlaza();

    void setPlaza(Plaza plaza);

    Stage getStage();
}
