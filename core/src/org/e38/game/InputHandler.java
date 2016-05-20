package org.e38.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import org.e38.game.model.Level;
import org.e38.game.screens.LevelScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by joel on 18/05/2016.
 */
public class InputHandler implements InputProcessor {
    Level lvl;
    LevelScreen lScr;
    private MapObject[][] mapObjects = new MapObject[100][100];
    private List<RectangleMapObject> plazas = new ArrayList<>();

    public InputHandler(Level lvl, LevelScreen lScr) {
        this.lvl = lvl;
        this.lScr = lScr;

        List<MapObject> objects = iterToList(lvl.getLayer().getObjects().iterator());
        for (MapObject object : objects) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
                TiledMapTileMapObject mapObject = (TiledMapTileMapObject) object;
                float x = (float) object.getProperties().get("x");
                float y = (float) object.getProperties().get("y");
//                y = (float) object.getProperties().get("height") + y;
                float height = (float) object.getProperties().get("height");
                float width = (float) object.getProperties().get("width");
                RectangleMapObject rectangleMapObject = new RectangleMapObject(x, height + y, height, width);
                rectangleMapObject.getProperties().putAll(object.getProperties());
                plazas.add(rectangleMapObject);
            }
        }
//        System.out.println(objects);
//        for (MapObject object : objects) {
//            int x, y;
//            x = (int) ((Float) object.getProperties().get("x") / 8);
//            y = (int) ((Float) object.getProperties().get("y") / 6);
//            float width = (Float) object.getProperties().get("width");
//            float height = (Float) object.getProperties().get("height");
//
//            mapObjects[x][y] = object;
//            int xCasillas = Math.round(width / 8);
//            int yCasillas = Math.round(height / 6);
//            for (int i = x - xCasillas ; i < x + xCasillas; i++) {
//                for (int j = y - yCasillas ; j < y + yCasillas; j++) {
//                    mapObjects[i][j] = object;
//                }
//            }
//        }
//        lvl.getLayer().getObjects().iterator();
    }

    private <T> List<T> iterToList(Iterator<T> tIterator) {
        List<T> list = new ArrayList<>();
        while (tIterator.hasNext()) {
            list.add(tIterator.next());
        }
        return list;
    }

    public List<RectangleMapObject> getPlazas() {
        return plazas;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        MapProperties properties;
        try {
            screenY = Gdx.graphics.getHeight() - screenY;//normalizar y
//
            System.out.println(screenX + " : " + screenY);
            for (RectangleMapObject plaza : plazas) {
                if (plaza.getRectangle().contains(screenX, screenY)) {
                    System.out.println(plaza.getName() + "\n" + propetresToString(plaza.getProperties()));
                    break;
                }
            }
//            MapObject properties2 = mapObjects[Math.round(screenX / 8)][Math.round(screenY / 6)];
//            if (properties2 != null) {
//                properties = mapObjects[Math.round(screenX / 8)][Math.round(screenY / 6)].getProperties();
//
//            } else {
//                screenY -= 80;
//                properties = mapObjects[Math.round(screenX / 8)][Math.round(screenY / 6)].getProperties();
//
//            }
//            System.out.println(properties2.getName() + "\n" + propetresToString(properties));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lScr.getCopsBar().updateBar(30);
        //use touch.x and touch.y as your new touch point

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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
