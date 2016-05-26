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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.World;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.ImproveBar;
import org.e38.game.hud.TopBar;
import org.e38.game.model.Level;
import org.e38.game.model.Plaza;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Cop;
import org.e38.game.model.npc.Criminal;
import org.e38.game.model.npc.cops.Area;

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
    private ImproveBar improveBar;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Stage stage;
    private boolean showCopsBar;
    private boolean showImproveBar;
    private int lastPlazaId = 0;
    private Actor areaCopButton;
    private Actor damageOverCopButton;
    private Actor lentoCopButton;
    private Actor rapidoCopButton;


    public LevelScreen(Level level, Game game) {
        this.level = level;
        this.game = game;
        camera = new OrthographicCamera();

        topBar = new TopBar(0, 0);
        copsBar = new CopsBar(0, topBar.table.getY() - topBar.table.getHeight());
        improveBar = new ImproveBar(0, topBar.table.getY() - topBar.table.getHeight(), new Area());

//        initActors();


    }

    private void initActors() {
        areaCopButton = new Actor();

        areaCopButton.setBounds(114, 600 - 85 - 60, 100, 60);
        areaCopButton.isTouchable();
        stage.addActor(areaCopButton);
        areaCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clickao");
                Area a = new Area();
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                a.setPosicion(new Vector2(xPlaza, yPlaza));
                level.cops.add(a);
                showCopsBar = false;
                level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                areaCopButton.setTouchable(Touchable.disabled);
                return true;
            }
        });


        damageOverCopButton = new Actor();
        damageOverCopButton.setX(249);
        damageOverCopButton.setY(492);
        damageOverCopButton.setHeight(60);
        damageOverCopButton.setWidth(100);

        lentoCopButton = new Actor();
        lentoCopButton.setX(386);
        lentoCopButton.setY(492);
        lentoCopButton.setHeight(60);
        lentoCopButton.setWidth(100);

        rapidoCopButton = new Actor();
        rapidoCopButton.setX(519);
        rapidoCopButton.setY(492);
        rapidoCopButton.setHeight(60);
        rapidoCopButton.setWidth(100);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));

        for (MapObject object : level.getLayer().getObjects()) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
                float y = (Float) object.getProperties().get("y");
                object.getProperties().put("y", (float) object.getProperties().get("height") + y);
                stage.addActor(new Plaza(object, this));
            }
        }
        ot = new OrthogonalTiledMapRenderer(level.map) {
            @Override
            protected void endRender() {
                renderCops(getBatch());
                renderCriminals(getBatch());
//                renderPlazas(getBatch());
                super.endRender();
            }
        };
//        Gdx.input.setInputProcessor(new InputHandler(level, this));

        camera.position.set(400, 300, 0);
        camera.update();
        initActors();
        game.resume();
        Gdx.input.setInputProcessor(stage);
    }

    private void renderPlazas() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for (MapObject object : level.getLayer().getObjects()) {
            float x = (Float) object.getProperties().get("x");
            float y = (Float) object.getProperties().get("y");
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza") && object.getProperties().get("ocupada").equals("false")) {
                drawPlaza(object, x, y);
            }
            //dibuja policias por todos lados
//            batch.draw(World.getRecurses().getPolicia(Recurses.POLICIA_BUENO, NPC.Orientation.LEFT), x, y);
        }
        shapeRenderer.end();
    }

    private void renderCriminals(Batch batch) {
        int idx = 0;
        for (Wave w : level.waves) {
            for (Criminal c : w.getCriminals()) {
                if (c.isAlive()) {
                    idx = c.getPathPointer();
                    float x = (float) level.getPath().get(idx).getProperties().get("x");
                    float y = (float) level.getPath().get(idx).getProperties().get("y");
                    batch.draw(World.getRecurses().getACriminal(c).update(Gdx.graphics.getDeltaTime()), x, y);
                    c.setPathPointer(idx + 1);
                }
            }

        }
    }

    private void renderCops(Batch batch) {
        for (Cop c : level.cops) {
            float x = (float) c.getPosition().x;
            float y = (float) c.getPosition().y;
            batch.draw(World.getRecurses().getPolicia(c), x, y);
        }
    }

    private void drawPlaza(MapObject object, float x, float y) {
        float width = (float) object.getProperties().get("width");
        float heght = (float) object.getProperties().get("height");
        if (object.getProperties().get("isSelected") != null) {
            if ((boolean) object.getProperties().get("isSelected"))
                shapeRenderer.rect(x, y, width, heght);
        }
    }

    public void unSelectLastPlaza() {
        if (lastPlazaId != 0) {
            level.getLayer().getObjects().get(lastPlazaId).getProperties().put("isSelected", false);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);

        ot.render();
        renderPlazas();
        topBar.stage.draw();
        if (showCopsBar)
            copsBar.stage.draw();
        if(showImproveBar)
            improveBar.stage.draw();

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

    public void showHideCopsBar(MapObject object) {
        if(showImproveBar)
            showImproveBar=false;
        if (object.getProperties().get("isSelected") == null) {
            showCopsBar = true;
            areaCopButton.setTouchable(Touchable.enabled);
        }

    }

    public void showHideImproveBar() {
        showImproveBar = !showImproveBar;
    }

    public Level getLevel() {
        return level;
    }

    public LevelScreen setLastPlazaId(int lastPlazaId) {
        this.lastPlazaId = lastPlazaId;
        return this;
    }
}
