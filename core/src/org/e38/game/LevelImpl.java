package org.e38.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.e38.game.model.Level;

import java.util.List;

/**
 * Created by sergi on 5/10/16.
 */
public class LevelImpl extends Level {

    private List<OnEndListerner> onEndListerners;
    private TiledMap map;

    public LevelImpl(int initialCoins, int levelUID, List<OnEndListerner> onEndListerners) {
        super(initialCoins, levelUID);
    }

    public LevelImpl(int initialCoins, int levelUID) {
        super(initialCoins, levelUID);
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onDestroy() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public Level fromJson() {
        return null;
    }
}
