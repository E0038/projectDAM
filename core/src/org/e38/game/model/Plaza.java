package org.e38.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.*;
import org.e38.game.screens.LevelScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ALUMNEDAM on 25/05/2016.
 */
public class Plaza extends Group {
    public MapObject object;
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
//                System.out.println(x + " : " + y);
//                System.out.println(propetresToString(object.getProperties()));


                object.getProperties().put("isSelected", true);
                if (levelScreen.getLevel().getLayer().getObjects().getIndex(object) != levelScreen.getLastPlazaId())
                    levelScreen.unSelectLastPlaza();
                if (object.getProperties().get("ocupada").equals(true)) {
                    levelScreen.showImproveBar();
                } else {
                    levelScreen.showCopsBar();
                }
                levelScreen.changeButtonsState();
                //Recupera el indice de la lista (de objetos) de la plaza y la setea en LevelScreen
                levelScreen.setLastPlazaId((Integer) levelScreen.getLevel().getLayer().getObjects().getIndex(object));

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

    public void  changeOcupada(){
        object.getProperties().put("isSelected", !(boolean)object.getProperties().get("ocupada"));
    }
//private ShapeRenderer shapeRenderer = new ShapeRenderer();
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        batch.end();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
////        super.draw(batch, parentAlpha);
//        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
//        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
//        shapeRenderer.translate(getX(), getY(), 0);
//
//        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
//        shapeRenderer.end();
//
//
//    }
}
