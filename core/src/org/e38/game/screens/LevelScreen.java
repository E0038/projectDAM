package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.e38.game.model.Level;

/**
 * Created by sergi on 4/20/16.
 */
public class LevelScreen implements Screen {
    private Level level;
    private Game game;
    private OrthogonalTiledMapRenderer ot;
    private OrthographicCamera camera;


    public LevelScreen(Level level, Game game) {
        this.level = level;
        this.game = game;
    }

    @Override
    public void show() {
        ot = level.getRenderer();

        camera = new OrthographicCamera();
        camera.position.set(400,300,0);
        camera.update();
        game.resume();
    }

    @Override
    public void render(float delta) {
        ot.setView(camera);
        ot.render();


    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
