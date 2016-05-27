package org.e38.game.hud;


import com.badlogic.gdx.scenes.scene2d.Stage;
import org.e38.game.model.npc.Cop;

/**
 * Created by ALUMNEDAM on 26/05/2016.
 */
public interface Bar {

    public void updateBar(int money);
    public void updateBar(int money, Cop cop);

    Stage getStage();
}
