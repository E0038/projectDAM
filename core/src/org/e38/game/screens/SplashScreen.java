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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.World;
import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Criminal;
import org.e38.game.persistance.ProfileManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sergi on 4/22/16.
 */
public class SplashScreen implements Screen {
    public static final int LOADING_GIF_PARTS = 24;
    private final MainGame game;
    private AnimationManager loadingGif;
    private Texture loadingSprite;
    private Stage stage;

    public SplashScreen(final MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        loadingSprite = new Texture("grafics/textures/loading_sprite.png") {
            @Override
            protected void finalize() throws Throwable {
                dispose();
                super.finalize();
            }
        };
        TextureRegion[][] regions = TextureRegion.split(loadingSprite, loadingSprite.getWidth() / LOADING_GIF_PARTS, loadingSprite.getHeight());
        loadingGif = new AnimationManager(new Animation(0.01f, regions[0]));
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        Label label = new Label("LOADING...", new Label.LabelStyle(new BitmapFont(), Color.BLACK)) {
            @Override
            protected void finalize() throws Throwable {
                dispose();
                super.finalize();
            }
        };
        label.setX((Gdx.graphics.getWidth() / 2) - (loadingSprite.getWidth() / LOADING_GIF_PARTS / 2));
        label.setY((Gdx.graphics.getHeight() / 2) + loadingSprite.getHeight());
        label.setFontScale(2f);
        stage.addActor(label);
        stage.addActor(new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                TextureRegion region = loadingGif.update(Gdx.graphics.getDeltaTime());
                float x = (Gdx.graphics.getWidth() / 2) - region.getRegionWidth() / 2;
                float y = (Gdx.graphics.getHeight() / 2) - region.getRegionHeight() / 2;
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
    }

    private void onGameLoaded() {
        Level lvl = new Level(0, "grafics/map1/Mapa_lvl1.tmx");
        ArrayList<Wave> waves = new ArrayList<>();
        waves.add(new Wave(Arrays.asList(new Criminal(), new Criminal())));
        lvl.waves = waves;
        Level level = ProfileManager.getInstance().gson.fromJson("{\"coins\":0,\"INIT_LIFES\":0,\"waves\":[{\"criminals\":[{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"},{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"}],\"gap\":0.0}],\"mapPath\":\"grafics/map1/Mapa_lvl1.tmx\",\"waveGap\":3000.0}\n", Level.class);
        System.out.println(ProfileManager.getInstance().gson.toJson(lvl));
        System.out.println(level);
        lvl.onCreate();
//        game.setScreen(new LevelScreen(lvl, game));
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
}
