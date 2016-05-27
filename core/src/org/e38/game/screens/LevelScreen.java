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
import org.e38.game.GameUpdater;
import org.e38.game.World;
import org.e38.game.hud.Bar;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.UpgradeBar;
import org.e38.game.hud.TopBar;
import org.e38.game.model.Level;
import org.e38.game.model.Plaza;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Cop;
import org.e38.game.model.npc.Criminal;
import org.e38.game.model.npc.cops.Area;
import org.e38.game.model.npc.cops.DamageOverTime;
import org.e38.game.model.npc.cops.Lento;
import org.e38.game.model.npc.cops.Rapido;

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
    private Bar lowerBar;
    private UpgradeBar improveBar;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Stage stage;

    public int getLastPlazaId() {
        return lastPlazaId;
    }

    private int lastPlazaId = 0;
    private Actor areaCopButton;
    private Actor damageOverCopButton;
    private Actor lentoCopButton;
    private Actor rapidoCopButton;

    private Actor sellCopButton;
    private Actor upgradeCopButton;


    private GameUpdater gameUpdater;
    private Actor[] botonesCop;
    private Actor[] botonesUpgrade;


    public LevelScreen(Level level, Game game) {
        //grosor bordes plaza
        Gdx.gl.glLineWidth(30);
        this.level = level;
        this.game = game;
        camera = new OrthographicCamera();

        topBar = new TopBar(level.getLifes(), level.getCoins());
        lowerBar = voidBar;
        copsBar = new CopsBar(level.getCoins(), topBar.table.getY() - topBar.table.getHeight());
        improveBar = new UpgradeBar(level.getCoins(), topBar.table.getY() - topBar.table.getHeight());
        level.addOnChangeStateListerner(topBar);
        gameUpdater = new GameUpdater(level);
    }

    //bar vacia para cuando no hay que mosta
    private Bar voidBar = new Bar() {
        private Stage stage = new Stage();

        @Override
        public void updateBar(int money) {}

        @Override
        public void updateBar(int money, Cop cop) {}

        @Override
        public Stage getStage() {
            return stage;
        }
    };

    private void initActors() {
        areaCopButton = new Actor();
        areaCopButton.setTouchable(Touchable.disabled);
        areaCopButton.setBounds(519, 600 - 85 - 60, 100, 60);
        stage.addActor(areaCopButton);
        areaCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Area a = new Area();
                a.spawn();
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                a.setPosicion(new Vector2(xPlaza, yPlaza));
                level.cops.add(a);
                lowerBar = voidBar;
                //restamos el precio de compra del dinero del jugador
                level.setCoins((int) (level.getCoins()-a.getNivel().getPrecioCompra()));

                System.out.println("Coins level: " + String.valueOf(level.getCoins()));
                level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                areaCopButton.setTouchable(Touchable.disabled);
                return true;
            }
        });


        damageOverCopButton = new Actor();
        damageOverCopButton.setTouchable(Touchable.disabled);
        damageOverCopButton.setBounds(249, 600 - 85 - 60, 100, 60);
        stage.addActor(damageOverCopButton);
        damageOverCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DamageOverTime a = new DamageOverTime();
                a.spawn();
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                a.setPosicion(new Vector2(xPlaza, yPlaza));
                level.cops.add(a);
                lowerBar = voidBar;
                level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                areaCopButton.setTouchable(Touchable.disabled);
                return true;
            }
        });

        lentoCopButton = new Actor();
        lentoCopButton.setTouchable(Touchable.disabled);
        lentoCopButton.setBounds(386, 600 - 85 - 60, 100, 60);
        stage.addActor(lentoCopButton);
        lentoCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Lento a = new Lento();
                a.spawn();
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                a.setPosicion(new Vector2(xPlaza, yPlaza));
                level.cops.add(a);
                lowerBar = voidBar;
                level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                areaCopButton.setTouchable(Touchable.disabled);
                return true;
            }
        });

        rapidoCopButton = new Actor();
        rapidoCopButton.setTouchable(Touchable.disabled);
        rapidoCopButton.setBounds(114, 600 - 85 - 60, 100, 60);
        stage.addActor(rapidoCopButton);
        rapidoCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Rapido a = new Rapido();
                a.spawn();
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                a.setPosicion(new Vector2(xPlaza, yPlaza));
                level.cops.add(a);
                lowerBar = voidBar;
                level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                areaCopButton.setTouchable(Touchable.disabled);
                return true;
            }
        });

        upgradeCopButton = new Actor();
        upgradeCopButton.setTouchable(Touchable.disabled);
        upgradeCopButton.setBounds(155, 600 - 85 - 85, 200, 100);
        stage.addActor(upgradeCopButton);
        upgradeCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("upgrade");
                Cop cop = null;
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                for (Cop c : level.cops) {
                    if (c.getPosition().x == xPlaza && c.getPosition().y == yPlaza)
                        cop = c;
                }

                System.out.println(cop.toString());
                return true;
            }
        });

        sellCopButton = new Actor();
        sellCopButton.setTouchable(Touchable.disabled);
        sellCopButton.setBounds(455, 600 - 85 - 85, 200, 100);
        stage.addActor(sellCopButton);
        sellCopButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("sell");
                Cop cop = null;
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                for (Cop c : level.cops) {
                    if (c.getPosition().x == xPlaza && c.getPosition().y == yPlaza)
                        cop = c;
                }
                //sumamos el precio de venta del poli al dinero del jugador
                level.setCoins((int) (level.getCoins() + cop.getNivel().getPrecioVenta()));
                cop.onSell();
                cop.onUpgrade();
                level.cops.remove(cop);
                lowerBar = voidBar;
                changeButtonsState();

                level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", false);
                System.out.println(cop.toString());
                return true;
            }
        });

        botonesCop = new Actor[]{areaCopButton, damageOverCopButton, rapidoCopButton, lentoCopButton};
        botonesUpgrade = new Actor[]{upgradeCopButton, sellCopButton};
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));

        for (MapObject object : level.getLayer().getObjects()) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
//                float y = (Float) object.getProperties().get("y");
//                object.getProperties().put("y", y);
                stage.addActor(new Plaza(object, this));
            }
        }
        ot = new OrthogonalTiledMapRenderer(level.map) {
            @Override
            protected void endRender() {
                renderCops(getBatch());
                renderCriminals(getBatch());
                super.endRender();
            }
        };

        camera.position.set(400, 300, 0);
        camera.update();
        initActors();
        game.resume();
        Gdx.input.setInputProcessor(stage);
    }

    private void renderPlazas() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (MapObject object : level.getLayer().getObjects()) {
            float x = (Float) object.getProperties().get("x");
            float y = (Float) object.getProperties().get("y");
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
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
//                    c.setPathPointer(idx + 1);
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
                shapeRenderer.rect(x - 3, y, width + 4, heght + 4);
        }
    }

    public void unSelectLastPlaza() {
        if (lastPlazaId != -1) {
            level.getLayer().getObjects().get(lastPlazaId).getProperties().put("isSelected", false);
        }
    }

    public void changeButtonsState() {
        if (lowerBar instanceof CopsBar) {
            for (Actor a : botonesCop) {
                a.setTouchable(Touchable.enabled);
            }
            for (Actor a : botonesUpgrade) {
                a.setTouchable(Touchable.disabled);
            }
        } else if (lowerBar instanceof UpgradeBar) {
            for (Actor a : botonesCop) {
                a.setTouchable(Touchable.disabled);
            }
            for (Actor a : botonesUpgrade) {
                a.setTouchable(Touchable.enabled);
            }
        } else {
            for (Actor a : botonesCop) {
                a.setTouchable(Touchable.disabled);
            }
            for (Actor a : botonesUpgrade) {
                a.setTouchable(Touchable.disabled);
            }
        }
    }

    @Override
    public void render(float delta) {
        gameUpdater.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);

        ot.render();
        renderPlazas();
        topBar.stage.draw();
        lowerBar.getStage().draw();

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

    public void showCopsBar() {
        lowerBar = copsBar;

    }

    public void showImproveBar() {
        lowerBar = improveBar;
    }

    public Level getLevel() {
        return level;
    }

    public LevelScreen setLastPlazaId(int lastPlazaId) {
        this.lastPlazaId = lastPlazaId;
        return this;
    }
}
