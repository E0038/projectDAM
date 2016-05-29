package org.e38.game.model;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.e38.game.model.npcs.Cop;
import org.e38.game.screens.LevelScreen;

import java.util.Iterator;

/**
 * Created by ALUMNEDAM on 25/05/2016.
 */
public class Plaza extends Actor {
    public MapObject object;
    public boolean isSelected = false;
    LevelScreen levelScreen;
    private Cop cop;

    public Plaza(final MapObject object, LevelScreen levelScreen) {
        this.levelScreen = levelScreen;

        setBounds((float) object.getProperties().get("x"), (float) object.getProperties().get("y"), (float) object.getProperties().get("width"), (float) object.getProperties().get("height"));
        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick(object);
            }
        });
    }

    private void onClick(MapObject object) {
        object.getProperties().put("isSelected", true);
        if (levelScreen.objects.getObjects().getIndex(object) != levelScreen.getLastPlazaId())
            levelScreen.unSelectLastPlaza();

        //Recupera el indice de la lista (de objetos) de la plaza y la setea en LevelScreen
        levelScreen.setLastPlazaId(levelScreen.objects.getObjects().getIndex(object));
        if (object.getProperties().get("ocupada").equals(true)) {
            levelScreen.updateLowerBar(LevelScreen.TYPE_UPGRADE);
            levelScreen.showUpgradeBar();
        } else {
            levelScreen.updateLowerBar(LevelScreen.TYPE_COPS);
            levelScreen.showCopsBar();
        }
        levelScreen.changeButtonsState();
        //Recupera el indice de la lista (de objetos) de la plaza y la setea en LevelScreen
        levelScreen.setLastPlazaId(levelScreen.objects.getObjects().getIndex(object));
    }

    @SuppressWarnings("Duplicates")
    private String propetresToString(MapProperties properties) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = properties.getKeys();
        builder.append("{\n");
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.append("\t").append(key).append(" : ").append(properties.get(key)).append(",\n");
        }
        builder.append("}");
        return builder.toString();
    }

    public void changeOcupada() {
        object.getProperties().put("isSelected", !(boolean) object.getProperties().get("ocupada"));
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
//    private ShapeRenderer shapeRenderer = new ShapeRenderer();
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
//    }
}
