package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.model.Level;
import org.e38.game.utils.AnimationManager;
import org.e38.game.utils.World;

/**
 * Created by sergi on 4/22/16.
 */
public class SplashScreen implements Screen {
    public static final int LOADING_GIF_PARTS = 24;
    private final MainGame game;
    private AnimationManager loadingGif;
    private Texture loadingSprite;
    private Stage stage;
    private boolean showDialog;
    private Dialog errorDialog;

    public SplashScreen(final MainGame game) {
        this.game = game;
        game.loader.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Gdx.app.error(MainGame.class.getName(), e.getMessage(), e);
                showDialog = true;
            }
        });
    }

    @Override
    public void show() {
        loadingSprite = new Texture("grafics/textures/loading_sprite.png");
        TextureRegion[][] regions = TextureRegion.split(loadingSprite, loadingSprite.getWidth() / LOADING_GIF_PARTS, loadingSprite.getHeight());
        loadingGif = new AnimationManager(new Animation(0.01f, regions[0]));
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        Label label = new Label("LOADING...", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        label.setX((stage.getViewport().getWorldWidth() / 2) - (loadingSprite.getWidth() / LOADING_GIF_PARTS / 2));
        label.setY((stage.getViewport().getWorldHeight() / 2) + loadingSprite.getHeight());
        label.setFontScale(2f);
        errorDialog = new Dialog("Error al cargar el juego.", new Window.WindowStyle(new BitmapFont(), new Color(Color.BLACK), new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg))));
        errorDialog.padTop(40).padLeft(60);
        stage.addActor(label);
        stage.addActor(new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                TextureRegion region = loadingGif.update(Gdx.graphics.getDeltaTime());
                float x = (stage.getViewport().getWorldWidth() / 2) - region.getRegionWidth() / 2;
                float y = (stage.getViewport().getWorldHeight() / 2) - region.getRegionHeight() / 2;
                batch.draw(region, x, y);
                super.draw(batch, parentAlpha);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (World.getRecurses().isLoaded.get()) {
            onGameLoaded();
        } else {
            stage.draw();
        }
        if (showDialog)
            errorDialog.show(stage);
    }

    private void onGameLoaded() {
        for (Level level : World.levels) {
            level.onCreate();//no se puede desde el hilo de carga porque usa el contexto OpenGl
        }
        game.setScreen(new MenuScreen(game));
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
    public void hide() {
        game.pause();
    }

    @Override
    public void dispose() {
        loadingSprite.dispose();
        stage.dispose();
    }

    @Override
    protected void finalize() throws Throwable {
//        dispose();
        super.finalize();
    }
}
