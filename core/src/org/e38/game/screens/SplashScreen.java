package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.e38.game.MainGame;
import org.e38.game.utils.AssertLoader;

/**
 * Created by sergi on 4/22/16.
 */
public class SplashScreen implements Screen {
    private MainGame game;

    public SplashScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (AssertLoader.isLoaded.get()) {
                        game.setScreen(new MenuScreen(game));
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Gdx.app.log(SplashScreen.class.getName(), e.getMessage());
                    }
                }
            }
        }, "loadWatcherTread").start();
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
