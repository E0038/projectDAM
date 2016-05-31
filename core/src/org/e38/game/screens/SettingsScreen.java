package org.e38.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.utils.World;

/**
 * Created by ALUMNEDAM on 31/05/2016.
 */
public class SettingsScreen implements Screen {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private final MainGame game;
    private Stage stage;

    public SettingsScreen(MainGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
    }

    @Override
    public void render(float delta) {

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

    }

    @Override
    public void dispose() {

    }
}
