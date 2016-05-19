package org.e38.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import org.e38.game.screens.LevelScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by joel on 18/05/2016.
 */
public class InputHandler implements InputProcessor {
    LevelScreen lvl;
    private MapObject[][] mapObjects = new MapObject[100][100];

    public InputHandler(LevelScreen lvl) {
        this.lvl = lvl;
        int c = 0;
        List<MapObject> objects = iterToList(lvl.getLevel().getLayer().getObjects().iterator());
        for (MapObject object : objects) {
            if (object.getProperties().get("type") != null && !object.getProperties().get("type").equals("camino")) {
                System.out.println(propetresToString(object.getProperties()));
            }
        }
        System.out.println(objects);
        for (MapObject object : objects) {
            int x, y;
            x = (int) ((Float) object.getProperties().get("x") / 8);
            y = (int) ((Float) object.getProperties().get("y") / 6);
            float width = (Float) object.getProperties().get("width");
            float height = (Float) object.getProperties().get("height");

            mapObjects[x][y] = object;
            int xCasillas = Math.round(width / 8);
            int yCasillas = Math.round(height / 6);
            for (int i = x - xCasillas / 2; i < x + xCasillas / 2; i++) {
                for (int j = y - yCasillas / 2; j < y + yCasillas / 2; j++) {
                    mapObjects[i][j] = object;
                }
            }
        }
        lvl.getLevel().getLayer().getObjects().iterator();
    }

    private <T> List<T> iterToList(Iterator<T> tIterator) {
        List<T> list = new ArrayList<>();
        while (tIterator.hasNext()) {
            list.add(tIterator.next());
        }
        return list;
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
        try {
//            screenY = 600 - screenY;//normalizar y
            System.out.println(screenX + " : " + screenY);
            MapProperties properties = mapObjects[Math.round(screenX / 8)][Math.round(screenY / 6)].getProperties();
            System.out.println(propetresToString(properties));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lvl.getCopsBar().updateBar(30);
        //use touch.x and touch.y as your new touch point

        return false;
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
}
