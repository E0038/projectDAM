package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.e38.game.InputHandler;
import org.e38.game.Recurses;
import org.e38.game.TiledMapStage;
import org.e38.game.World;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.TopBar;
import org.e38.game.model.Level;
import org.e38.game.model.npc.NPC;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleShader.Setters.screenWidth;

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
    private OrthogonalTiledMapRenderer batcher;

    public LevelScreen(Level level, Game game) {
        this.level = level;
        this.game = game;
        float ratio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        camera = new OrthographicCamera();


        //topBar = new TopBar(0, 0);
        //copsBar = new CopsBar(0, topBar.table.getY()-topBar.table.getHeight());

        //Stage stage = new TiledMapStage(level.getMap());
        Gdx.input.setInputProcessor(new InputHandler(level));

    }

    @Override
    public void show() {
        ot = new OrthogonalTiledMapRenderer(level.map) {
            @Override
            protected void endRender() {

                for (MapObject object : level.getLayer().getObjects()) {
//            if (object.getProperties().get("type") != null  && object.getProperties().get("type").equals("camino")){
                    float x3 = ((Float) object.getProperties().get("x"));
                    float y = ((Float) object.getProperties().get("y"));
//                System.out.println(x + " : " +y);
                    getBatch().draw(World.getRecurses().getPolicia(Recurses.POLICIA_BUENO, NPC.Orientation.LEFT), x3, y);

//            }
                }


                super.endRender();
            }
        };

        camera.position.set(400, 300, 0);
        camera.update();
        game.resume();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);
        batcher = ot;



ot.render();


        /*topBar.stage.draw();
        copsBar.stage.draw();*/

//        copsBar.table.setY(topBar.table.getY()-topBar.table.getHeight());
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

    //getters para la prueba de click sobre HUD de cops
    public CopsBar getCopsBar() {
        return copsBar;
    }

    public Level getLevel() {
        return level;
    }
}
