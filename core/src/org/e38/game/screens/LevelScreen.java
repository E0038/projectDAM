package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.e38.game.GameUpdater;
import org.e38.game.InputHandler;
import org.e38.game.World;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.TopBar;
import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Criminal;

import java.util.ArrayList;

/**
 * Created by sergi on 4/20/16.
 */
public class LevelScreen implements Screen {
    private final GameUpdater gameUpdater;
    private Level level;
    private Game game;
    private OrthogonalTiledMapRenderer ot;
    private OrthographicCamera camera;
    private TopBar topBar;
    private CopsBar copsBar;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    //testeo spawn criminales
    private ArrayList<Criminal> aliveCriminals = new ArrayList<>();
    private float tiempo = 0;
    private int waveCount = 0;
    private int criminalCount = 0;
    private boolean canSpawn = true;

    public LevelScreen(Level level, Game game) {
        this.level = level;
        this.game = game;
//        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        camera = new OrthographicCamera();


        topBar = new TopBar(0, 0);
        copsBar = new CopsBar(0, topBar.table.getY() - topBar.table.getHeight());
        gameUpdater = new GameUpdater(this.level);
        //Stage stage = new TiledMapStage(level.getMap());
        Gdx.input.setInputProcessor(new InputHandler(level, this));

    }

    @Override
    public void show() {
        for (MapObject object : level.getLayer().getObjects()) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
                float x = (Float) object.getProperties().get("x");
                float y = (Float) object.getProperties().get("y");
                object.getProperties().put("y", (float) object.getProperties().get("height") + y);
            }
        }
        ot = new OrthogonalTiledMapRenderer(level.map) {
            @Override
            protected void endRender() {
                renderLevel(getBatch());
                renderCriminals(getBatch());
                super.endRender();
            }
        };

        camera.position.set(400, 300, 0);
        camera.update();
        game.resume();
    }

    private void renderLevel(Batch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        for (MapObject object : level.getLayer().getObjects()) {
            float x = (Float) object.getProperties().get("x");
            float y = (Float) object.getProperties().get("y");
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
                drawPlaza(object, x, y);
            }
        }
        shapeRenderer.end();
    }

    private void renderCriminals(Batch batch) {
        int idx = 0;
        for (Wave w : level.waves) {
//            for (Criminal c : w.getCriminals()) {
//                if (c.isAlive()) {
//                    idx = c.getPathPointer();
//                    float x = (float) level.getPath().get(idx).getProperties().get("x");
//                    float y = (float) level.getPath().get(idx).getProperties().get("y");
//                    batch.draw(World.getRecurses().getACriminal(aliveCriminals.get(aliveCriminals.size())).update(Gdx.graphics.getDeltaTime()), x, y);
//                    c.setPathPointer(idx + 1);

                }
//            }

//        }


    }

    private void drawPlaza(MapObject object, float x, float y) {
        float width = (float) object.getProperties().get("width");
        float heght = (float) object.getProperties().get("height");
        if (object.getProperties().get("isSelected") != null) {
//            if ((boolean) object.getProperties().get("isSelected"))
                shapeRenderer.rect(x, y, width, heght);
        }
    }

    @Override
    public void render(float delta) {
        gameUpdater.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);

        ot.render();

        topBar.stage.draw();
        copsBar.stage.draw();

        copsBar.table.setY(topBar.table.getY() - topBar.table.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 800;
        camera.viewportHeight = 600;
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

    public int getCriminalCount() {
        return criminalCount;
    }

    public int getWaveCount() {
        return waveCount;
    }

    public boolean canSpawn() {
        return canSpawn;
    }

    //getters para la prueba de click sobre HUD de cops
    public CopsBar getCopsBar() {
        return copsBar;
    }


    public Level getLevel() {
        return level;
    }
}
