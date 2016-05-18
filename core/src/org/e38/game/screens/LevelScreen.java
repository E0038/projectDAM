package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.TopBar;
import org.e38.game.model.Level;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/20/16.
 */
public class LevelScreen implements Screen {
    private Level level;
    private Game game;
    private OrthogonalTiledMapRenderer ot;
    private OrthographicCamera camera;
    private TopBar topBar;
    private CopsBar copsBar;


    public LevelScreen(Level level, Game game) {
        this.level = level;
        this.game = game;
        camera = new OrthographicCamera();
        topBar = new TopBar(0, 0);
        copsBar = new CopsBar(0);
        copsBar.table.setY(topBar.table.getY()-topBar.table.getHeight());
    }

    @Override
    public void show() {
        ot = level.getRenderer();

        camera.position.set(400,300,0);
        camera.update();
        game.resume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                showBar.set(!showBar.get());}
            }
        }).start();
    }
private AtomicBoolean showBar= new AtomicBoolean(true);
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);

        ot.render();
        topBar.stage.draw();
        if (showBar.get())
            copsBar.stage.draw();
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

    public TopBar getTopBar() {
        return topBar;
    }
}
