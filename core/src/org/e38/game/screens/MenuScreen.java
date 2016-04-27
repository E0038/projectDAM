package org.e38.game.screens;

import com.badlogic.gdx.Screen;
import org.e38.game.World;
import org.e38.game.MainGame;

/**
 * Created by sergi on 4/20/16.
 */
public class MenuScreen implements Screen {
    private MainGame game;

    public MenuScreen(MainGame game) {

    }

    public void onVolumeChange(float volumne) {
        if (volumne > 1f) {
            volumne = 1f;
        } else if (volumne < 0) {
            volumne = 0f;
        }
        World.volume = volumne;
    }

    public void onShowRanking() {

    }

    @Override
    public void show() {

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
