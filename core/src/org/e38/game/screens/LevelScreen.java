package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.hud.CopsBar;
import org.e38.game.hud.LowerBar;
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
import org.e38.game.persistance.ProfileManager;
import org.e38.game.utils.LevelUpdater;
import org.e38.game.utils.Recurses;
import org.e38.game.utils.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
// TODO: 5/29/16 add floating buttons (Back and Mute/Unmute) Support. ##Low priority##
public class LevelScreen implements Screen {

    public static final int TYPE_UPGRADE = 0;
    public static final int TYPE_COPS = 1;
    public MapLayer objects;
    private Level level;
    private MainGame game;
    private OrthogonalTiledMapRenderer ot;
    private OrthographicCamera camera;
    private TopBar topBar;
    private CopsBar copsBar;
    private LowerBar lowerBar;
    private UpgradeBar upgradeBar;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Stage stage;
//    private int lastPlazaId = 0;
    private Actor areaCopButton;
    private Actor damageOverCopButton;
    private Actor lentoCopButton;
    private Actor rapidoCopButton;
    private Actor sellCopButton;
    private Actor upgradeCopButton;
    private LevelUpdater levelUpdater;
    private Actor[] botonesCop;
    private Actor[] botonesUpgrade;
    private ImageButton back;
    private ImageButton volumeSwitch;
    private TextureRegionDrawable umuteDrawable;
    private TextureRegionDrawable muteDrawable;
    private Dialog errorDialog;
    private LowerBar voidBar = new LowerBar() {
        private Stage stage = new Stage();

        @Override
        public void updateBar(int money) {
        }

        @Override
        public void updateBar(int money, Cop cop) {
        }

        @Override
        public Plaza getPlaza() {
            return null;
        }

        @Override
        public void setPlaza(Plaza plaza) {

        }

        @Override
        public Stage getStage() {
            return stage;
        }
    };
    private List<Plaza> plazas = new ArrayList<>();

    public LevelScreen(final Level level, final MainGame game) {
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
        levelUpdater = new LevelUpdater(this);
        errorDialog = new Dialog("Juego finalizado", new Window.WindowStyle(new BitmapFont(), new Color(Color.BLACK), new TextureRegionDrawable(new TextureRegion(World.getRecurses().cuadradoBlanco))));
        errorDialog.padTop(40).padLeft(5);

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton dbutton = new TextButton("Volver al menú",new TextButton.TextButtonStyle(drawable, drawable,drawable, new BitmapFont()));
        dbutton.getStyle().fontColor = Color.BLACK;
        dbutton.setSize(10, 10);
        errorDialog.text(new Label("Lorem ipsum dolor sit amet,\n consectetur adipiscing elit.\n Quisque facilisis, nulla ultrices gravida porta", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        errorDialog.button(dbutton, true);

        dbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        Level.OnEndListerner onEndListerner = new Level.OnEndListerner() {
            @Override
            public void onEnd(boolean isWined) {
                if (isWined){
                    ProfileManager.getInstance().save(level);
                }
                errorDialog.getTitleLabel().setText(isWined ? "Partida acabada" : "Partida fallida");
                errorDialog.show(stage);

            }
        };
        level.addOnEndListerner(onEndListerner);
    }

    public List<Plaza> getPlazas() {
        return plazas;
    }

    public Stage getStage() {
        return stage;
    }

    public LowerBar getLowerBar() {
        return lowerBar;
    }

    public LevelScreen setLowerBar(LowerBar lowerBar) {
        this.lowerBar = lowerBar;
        return this;
    }

    public CopsBar getCopsBar() {
        return copsBar;
    }

    public TopBar getTopBar() {
        return topBar;
    }

    public UpgradeBar getUpgradeBar() {
        return upgradeBar;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        TiledMap map = new TmxMapLoader() {
            @Override
            protected void finalize() throws Throwable {
                dispose();
                super.finalize();
            }
        }.load(this.level.getMapPath());
        objects = map.getLayers().get("objetos");
        for (MapObject object : objects.getObjects()) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("plaza")) {
                final Plaza plaza = new Plaza(object, this);
                plazas.add(plaza);
                plaza.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        for (Plaza plaza1 : plazas) {//unselect all
                            plaza1.isSelected = false;
                        }
                        plaza.isSelected = true;
                        lowerBar = plaza.isOcupada() ? upgradeBar : copsBar;
                        lowerBar.setPlaza(plaza);
                        changeButtonsState();
                        plaza.onClick();
                    }
                });
            }
        }
        stage.getActors().addAll(plazas.toArray(new Actor[plazas.size()]));
        createButtons();
        configureButtons();
        stage.addActor(back);
        stage.addActor(volumeSwitch);
        ot = new OrthogonalTiledMapRenderer(map) {
            @Override
            protected void endRender() {
                renderCops(getBatch());
                renderCriminals(getBatch());
                super.endRender();
            }
        };

        camera.viewportHeight = stage.getViewport().getWorldHeight();
        camera.viewportWidth = stage.getViewport().getWorldWidth();
        camera.position.set(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2, 0);
        initActors();
        camera.update();
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
        int idx;
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

    @SuppressWarnings("FeatureEnvy")
    private void initActors() {
        areaCopButton = new Actor();
        areaCopButton.setTouchable(Touchable.disabled);
        areaCopButton.setBounds(519, 600 - 85 - 60, 100, 60);
        stage.addActor(areaCopButton);
        areaCopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (level.getCoins() >= 40) {
                    Plaza plaza = getSelected();
                    if (plaza != null){
                        Area a = new Area();
                        a.spawn();
                        a.setPosicion(new Vector2(plaza.getX(), plaza.getY()));
                        level.cops.add(a);
                        lowerBar = voidBar;
                        level.setCoins((int) (level.getCoins() - a.getNivel().getPrecioCompra()));
                        plaza.setCop(a);
                    }
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
                    Plaza plaza = getSelected();
                    if (plaza != null) {
                        DamageOverTime a = new DamageOverTime();
                        a.spawn();
                        a.setPosicion(new Vector2(plaza.getX(), plaza.getY()));
                        level.cops.add(a);
                        lowerBar = voidBar;
                        level.setCoins((int) (level.getCoins() - a.getNivel().getPrecioCompra()));
                        plaza.setCop(a);
                    }
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
                    Plaza plaza = getSelected();
                    if (plaza != null) {
                        Lento a = new Lento();
                        a.spawn();
                        a.setPosicion(new Vector2(plaza.getX(), plaza.getY()));
                        level.cops.add(a);
                        lowerBar = voidBar;
                        level.setCoins((int) (level.getCoins() - a.getNivel().getPrecioCompra()));
                        plaza.setCop(a);
                    }
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
                    Plaza plaza = getSelected();
                    if (plaza != null) {
                        Rapido a = new Rapido();
                        a.spawn();
                        a.setPosicion(new Vector2(plaza.getX(), plaza.getY()));
                        level.cops.add(a);
                        lowerBar = voidBar;
                        level.setCoins((int) (level.getCoins() - a.getNivel().getPrecioCompra()));
                        plaza.setCop(a);
                    }
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
                Cop cop = getSelected().getCop();
                if(cop != null) {
                    if (level.getCoins() >= cop.getNivel().getPrecioCompra() && cop.isUpgradeAvailable()) {
                        level.setCoins((int) (level.getCoins() - cop.getNivel().getPrecioCompra()));
                        cop.onUpgrade();
                    }
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
                Cop cop = getSelected().getCop();
                if (cop != null) {
                    //sumamos el precio de venta del poli al dinero del jugador
                    level.setCoins((int) (level.getCoins() + cop.getNivel().getPrecioVenta()));
                    cop.onSell();
                    getSelected().setCop(null);
                    level.cops.remove(cop);
                    lowerBar = voidBar;
                    changeButtonsState();
                    System.out.println(cop);
                }
            }
        });

        botonesCop = new Actor[]{areaCopButton, damageOverCopButton, rapidoCopButton, lentoCopButton};
//        botonesCop = new Actor[]{areaCopButton};
        botonesUpgrade = new Actor[]{upgradeCopButton, sellCopButton};
    }

    private void createButtons() {
        this.back = new ImageButton(new TextureRegionDrawable(new TextureRegion(World.getRecurses().back)));
        umuteDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().unmute));
        muteDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().mute));
        volumeSwitch = new ImageButton(!World.isMuted() ? umuteDrawable : muteDrawable);
    }

    private void configureButtons() {
        back.setSize(World.getRecurses().back.getWidth() * 0.75f, World.getRecurses().back.getHeight() * 0.75f);
        back.setPosition(0, stage.getViewport().getWorldHeight()- back.getHeight());

        volumeSwitch.setSize(World.getRecurses().mute.getWidth(), World.getRecurses().mute.getHeight());
        volumeSwitch.setPosition(back.getWidth(), stage.getViewport().getWorldHeight()- World.getRecurses().mute.getHeight());

        volumeSwitch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                volumeSwitch.getStyle().imageUp = World.isMuted() ? umuteDrawable : muteDrawable;
                World.onSwichMuteUnMute();
                World.play(Recurses.POP);
                super.clicked(event, x, y);
            }
        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                super.clicked(event, x, y);
            }
        });


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
        levelUpdater.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ot.setView(camera);

        ot.render();
        renderPlazas();
        topBar.stage.draw();
        lowerBar.getStage().draw();
        stage.draw();

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
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    protected void finalize() throws Throwable {
        stage.dispose();
        shapeRenderer.dispose();
        ot.dispose();
//        level.map.dispose();
        topBar.dispose();
        upgradeBar.dispose();
        copsBar.dispose();
        super.finalize();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
//        try {
//            stage.dispose();
//            shapeRenderer.dispose();
//            ot.dispose();
////        level.map.dispose();
//            topBar.dispose();
//            upgradeBar.dispose();
//            copsBar.dispose();
//        } catch (IllegalArgumentException ignored) {
//        }
    }

    private Plaza getSelected() {
        for (Plaza p : plazas) {
            if (p.isSelected) return p;
        }
        return null;
    }

//    public void unSelectLastPlaza() {
//        if (lastPlazaId != -1) {
//            objects.getObjects().get(lastPlazaId).getProperties().put("isSelected", false);
//        }
//    }

    public void updateLowerBar(int type) {
        if (type == TYPE_COPS) {
            copsBar.updateBar(level.getCoins());
        } else {
            Cop cop = getSelected().getCop();
            if(cop != null)
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

//    public int getLastPlazaId() {
//        return lastPlazaId;
//    }

//    public LevelScreen setLastPlazaId(int lastPlazaId) {
//        this.lastPlazaId = lastPlazaId;
//        return this;
//    }
}
