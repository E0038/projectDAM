package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
