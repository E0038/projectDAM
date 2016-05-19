package org.e38.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.e38.game.screens.LevelScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by joel on 18/05/2016.
 */
public class InputHandler implements InputProcessor{
    LevelScreen lvl;

    public InputHandler(LevelScreen lvl) {
        this.lvl = lvl;
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
private  static List iterToList(Iterator tIterator){
    List list = new ArrayList<>();
    while (tIterator.hasNext()){
        list.add(tIterator.next());
    }
        return list;
}
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println("height = " + lvl.getLevel().getLayer().getTileHeight());
        try {
            //lvl.getLevel().getMap().getLayers();
            System.out.println(iterToList(lvl.getLevel().getLayer().getObjects().iterator().next().getProperties().getKeys()));
//            TiledMapTileLayer.Cell cell = lvl.getLevel().getLayer().getCell(screenX/8, screenY/6);
//            java.util.Iterator<java.lang.Object> iterator =cell.getTile().getProperties().getValues();

//            System.out.println(iterToList(lvl.getLevel().getMap().getTileSets().iterator()));
//            System.out.println(cell.getTile().getTextureRegion());
//            System.out.println(iterToList(cell.getTile().getProperties().getKeys()).toString());
        }catch(Exception e){
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
