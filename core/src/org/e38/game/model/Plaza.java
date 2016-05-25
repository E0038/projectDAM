package org.e38.game.model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import org.e38.game.screens.LevelScreen;

import java.util.Iterator;

/**
 * Created by ALUMNEDAM on 25/05/2016.
 */
public class Plaza extends Actor {
    MapObject object;
    LevelScreen levelScreen;

    public Plaza(final MapObject object, final LevelScreen levelScreen) {
        this.object = object;
        this.levelScreen = levelScreen;

        setBounds((float)object.getProperties().get("x"),(float)object.getProperties().get("y"),(float)object.getProperties().get("width"),(float)object.getProperties().get("height"));
        setTouchable(Touchable.enabled);
//        System.out.println(getX() + " : " + getY());

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println(x + " : " + y);
                System.out.println(propetresToString(object.getProperties()));
                levelScreen.showHideCopsBar(object);

                levelScreen.unSelectLastPlaza();
                //Recupera el indice de la lista (de objetos) de la plaza y la setea en LevelScreen
                levelScreen.setLastPlazaId((Integer) levelScreen.getLevel().getLayer().getObjects().getIndex(object));

                //marcar seleccionada al hacer click
                if (object.getProperties().get("isSelected") == null)
                    object.getProperties().put("isSelected", true);
                else
                    object.getProperties().put("isSelected", !(boolean)object.getProperties().get("isSelected"));

                return true;
            }
        });
    }
    private String propetresToString(MapProperties properties) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = properties.getKeys();
        builder.append("{\n");
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.append("\t" + key + " : " + properties.get(key) + ",\n");
        }
        builder.append("}");
        return builder.toString();
    }
}
