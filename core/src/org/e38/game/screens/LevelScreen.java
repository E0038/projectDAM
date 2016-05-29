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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.hud.Bar;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.TopBar;
import org.e38.game.hud.UpgradeBar;
import org.e38.game.model.Level;
import org.e38.game.model.Plaza;
import org.e38.game.model.Wave;
import org.e38.game.model.npcs.Cop;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.model.npcs.cops.Area;
import org.e38.game.model.npcs.cops.DamageOverTime;
import org.e38.game.model.npcs.cops.Lento;
import org.e38.game.model.npcs.cops.Rapido;
import org.e38.game.utils.GameUpdater;
import org.e38.game.utils.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
public class LevelScreen implements Screen {

    public static final int TYPE_UPGRADE = 0;
    public static final int TYPE_COPS = 1;

    private Level level;
    private Game game;
    private OrthogonalTiledMapRenderer ot;
    private OrthographicCamera camera;
    private TopBar topBar;
    private CopsBar copsBar;
    private Bar lowerBar;
    private UpgradeBar upgradeBar;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Stage stage;
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
    //bar vacia para cuando no hay que mosta
    private Bar voidBar = new Bar() {
        private Stage stage = new Stage();

        @Override
        public void updateBar(int money) {
        }

        @Override
        public void updateBar(int money, Cop cop) {
        }

        @Override
        public Stage getStage() {
            return stage;
        }
    };
    private List<Plaza> plazas = new ArrayList<>();

    public LevelScreen(Level level, Game game) {
        //grosor bordes plaza
        Gdx.gl.glLineWidth(30);
        this.level = level;
        this.game = game;
        camera = new OrthographicCamera();

        topBar = new TopBar(level.getLifes(), level.getCoins());
        lowerBar = voidBar;
        copsBar = new CopsBar(level.getCoins(), topBar.table.getY() - topBar.table.getHeight());
        upgradeBar = new UpgradeBar(topBar.table.getY() - topBar.table.getHeight());
        level.addOnChangeStateListerner(topBar);
        gameUpdater = new GameUpdater(level);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));

        for (MapObject object : level.getLayer().getObjects()) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
                plazas.add(new Plaza(object, this));
            }
        }
        stage.getActors().addAll(plazas.toArray(new Actor[plazas.size()]));
        ot = new OrthogonalTiledMapRenderer(level.map) {
            @Override
            protected void endRender() {
                renderCops(getBatch());
                renderCriminals(getBatch());
                super.endRender();
            }
        };

        camera.position.set(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2, 0);
        camera.update();
        initActors();
        game.resume();
        Gdx.input.setInputProcessor(stage);
    }

    private void renderCops(Batch batch) {
        for (Cop c : level.cops) {
            float x = c.getPosition().x;
            float y = c.getPosition().y;
            batch.draw(World.getRecurses().getPolicia(c), x, y);
        }
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
                }
            }

        }
    }

    private void initActors() {
        areaCopButton = new Actor();
        areaCopButton.setTouchable(Touchable.disabled);
        areaCopButton.setBounds(519, 600 - 85 - 60, 100, 60);
        stage.addActor(areaCopButton);
        areaCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level.getCoins() >= 40) {
                    Area a = new Area();
                    a.spawn();
                    float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                    float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                    a.setPosicion(new Vector2(xPlaza, yPlaza));
                    level.cops.add(a);
                    lowerBar = voidBar;
                    //restamos el precio de compra del dinero del jugador
                    level.setCoins((int) (level.getCoins() - a.getNivel().getPrecioCompra()));

                    System.out.println("Coins level: " + level.getCoins());
                    level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                    areaCopButton.setTouchable(Touchable.disabled);
                }
            }
        });


        damageOverCopButton = new Actor();
        damageOverCopButton.setTouchable(Touchable.disabled);
        damageOverCopButton.setBounds(249, 600 - 85 - 60, 100, 60);
        stage.addActor(damageOverCopButton);
        damageOverCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level.getCoins() >= 20) {
                    DamageOverTime a = new DamageOverTime();

                    a.spawn();
                    float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                    float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                    a.setPosicion(new Vector2(xPlaza, yPlaza));
                    level.cops.add(a);
                    lowerBar = voidBar;
                    level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                    areaCopButton.setTouchable(Touchable.disabled);
                }
            }
        });

        lentoCopButton = new Actor();
        lentoCopButton.setTouchable(Touchable.disabled);
        lentoCopButton.setBounds(386, 600 - 85 - 60, 100, 60);
        stage.addActor(lentoCopButton);
        lentoCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level.getCoins() >= 30) {
                    Lento a = new Lento();
                    a.spawn();
                    float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                    float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                    a.setPosicion(new Vector2(xPlaza, yPlaza));
                    level.cops.add(a);
                    lowerBar = voidBar;
                    level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                    areaCopButton.setTouchable(Touchable.disabled);
                }
            }
        });

        rapidoCopButton = new Actor();
        rapidoCopButton.setTouchable(Touchable.disabled);
        rapidoCopButton.setBounds(114, 600 - 85 - 60, 100, 60);
        stage.addActor(rapidoCopButton);
        rapidoCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level.getCoins() >= 10) {
                    Rapido a = new Rapido();
                    a.spawn();
                    float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                    float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                    a.setPosicion(new Vector2(xPlaza, yPlaza));
                    level.cops.add(a);
                    lowerBar = voidBar;
                    level.getLayer().getObjects().get(lastPlazaId).getProperties().put("ocupada", true);
                    areaCopButton.setTouchable(Touchable.disabled);
                }
            }
        });

        upgradeCopButton = new Actor();
        upgradeCopButton.setTouchable(Touchable.disabled);
        upgradeCopButton.setBounds(155, 600 - 85 - 85, 200, 100);
        stage.addActor(upgradeCopButton);
        upgradeCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("upgrade");
                Cop cop = null;
                float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
                float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
                for (Cop c : level.cops) {
                    if (c.getPosition().x == xPlaza && c.getPosition().y == yPlaza)
                        cop = c;
                }
                if (level.getCoins() >= cop.getNivel().getPrecioCompra() && cop.isUpgradeAvailable()) {
                    level.setCoins((int) (level.getCoins() - cop.getNivel().getPrecioCompra()));
                    cop.onUpgrade();
                }
            }
        });

        sellCopButton = new Actor();
        sellCopButton.setTouchable(Touchable.disabled);
        sellCopButton.setBounds(455, 600 - 85 - 85, 200, 100);
        stage.addActor(sellCopButton);
        sellCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
                System.out.println(cop);
            }
        });

        botonesCop = new Actor[]{areaCopButton, damageOverCopButton, rapidoCopButton, lentoCopButton};
        botonesUpgrade = new Actor[]{upgradeCopButton, sellCopButton};
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);

        ot.render();
        renderPlazas();
        topBar.stage.draw();
        lowerBar.getStage().draw();

        copsBar.table.setY(topBar.table.getY() - topBar.table.getHeight());
    }

    private void renderPlazas() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Plaza plaza : plazas) {
            if (plaza.isSelected) {
                Vector2 coordinates = stage.stageToScreenCoordinates(new Vector2(plaza.getX(), plaza.getY()));
                shapeRenderer.rect(coordinates.x,
                        Gdx.graphics.getHeight() - coordinates.y,
                        plaza.getWidth() * (Gdx.graphics.getWidth() / World.WORLD_WIDTH),
                        plaza.getHeight() * (Gdx.graphics.getHeight() / World.WORLD_HEIGHT));
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = stage.getViewport().getWorldHeight();
        camera.viewportWidth = stage.getViewport().getWorldWidth();
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
        stage.dispose();
        shapeRenderer.dispose();
        ot.dispose();
        level.map.dispose();
        topBar.dispose();
        upgradeBar.dispose();
        copsBar.dispose();
    }

    public void unSelectLastPlaza() {
        if (lastPlazaId != -1) {
            level.getLayer().getObjects().get(lastPlazaId).getProperties().put("isSelected", false);
        }
    }

    public void updateLowerBar(int type) {
        if (type == TYPE_COPS) {
            copsBar.updateBar(level.getCoins());
        } else {
            Cop cop = null;
            float xPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("x");
            float yPlaza = (float) level.getLayer().getObjects().get(lastPlazaId).getProperties().get("y");
            for (Cop c : level.cops) {
                if (c.getPosition().x == xPlaza && c.getPosition().y == yPlaza)
                    cop = c;
            }
            upgradeBar.updateBar(level.getCoins(), cop);
        }
    }

    public void showCopsBar() {
        lowerBar = copsBar;
    }

    public void showUpgradeBar() {
        lowerBar = upgradeBar;
    }

    public Level getLevel() {
        return level;
    }

    public int getLastPlazaId() {
        return lastPlazaId;
    }

    public LevelScreen setLastPlazaId(int lastPlazaId) {
        this.lastPlazaId = lastPlazaId;
        return this;
    }

}
