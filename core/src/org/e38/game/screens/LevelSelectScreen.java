package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Criminal;
import org.e38.game.persistance.ProfileManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sergi on 4/28/16.
 */
public class LevelSelectScreen implements Screen {
    private final Game game;
    private Stage stage;

    public LevelSelectScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        stage.addActor(new Label("Hello World!!", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        //DEBUG
        Level lvl = new Level(0, "grafics/map1/Mapa_lvl1.tmx");
        ArrayList<Wave> waves = new ArrayList<>();
        waves.add(new Wave(Arrays.asList(new Criminal(), new Criminal())));
        lvl.waves = waves;
        Level level = ProfileManager.getInstance().gson.fromJson("{\"coins\":0,\"INIT_LIFES\":0,\"waves\":[{\"criminals\":[{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"},{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"}],\"gap\":0.0}],\"mapPath\":\"grafics/map1/Mapa_lvl1.tmx\",\"waveGap\":3000.0}\n", Level.class);
        System.out.println(ProfileManager.getInstance().gson.toJson(lvl));
        System.out.println(level);
        lvl.onCreate();
        game.setScreen(new LevelScreen(lvl, game));
        //END
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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

    }
}
