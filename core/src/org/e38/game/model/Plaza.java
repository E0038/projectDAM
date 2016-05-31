package org.e38.game.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import org.e38.game.model.npcs.Cop;
import org.e38.game.screens.LevelScreen;

import java.util.Iterator;

/**
 * Created by ALUMNEDAM on 25/05/2016.
 */
// TODO: 5/31/16 pintar range Circles
// TODO: 5/31/16 move Cop Circles to Plaza Circles
public class Plaza extends Actor {
    //    public MapObject object;
    public boolean isSelected = false;
    LevelScreen levelScreen;
    private Cop cop;

    public Plaza(final MapObject object, LevelScreen levelScreen) {
        this.levelScreen = levelScreen;

        setBounds((float) object.getProperties().get("x"), (float) object.getProperties().get("y"), (float) object.getProperties().get("width"), (float) object.getProperties().get("height"));
        setTouchable(Touchable.enabled);
    }

    public void onClick() {
        for (Plaza plaza1 : levelScreen.getPlazas()) {//unselect all
            plaza1.isSelected = false;
        }
        this.isSelected = true;
    }

    public boolean isOcupada() {
        return cop != null;
    }

    public Cop getCop() {
        return cop;
    }

    public void setCop(Cop cop) {
        this.cop = cop;
    }
}
