package org.e38.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.e38.game.screens.SplashScreen;
import org.e38.game.utils.Recurses;
import org.e38.game.utils.World;

public class MainGame extends Game {
    public boolean doRender = true;
    public Thread loader;

    @Override
    public void create() {
        final Recurses recurses = new Recurses();
        World.setRecurses(recurses);
        loader = new Thread(new Runnable() {
            @Override
            public void run() {
                recurses.load();
            }
        }, "contextLoaderThread");
        loader.setDaemon(true);
        loader.start();
        setScreen(new SplashScreen(this));
        Gdx.app.setLogLevel(Application.LOG_ERROR);
    }

    @Override
    public void dispose() {
        screen.dispose();
    }

    @Override
    public void pause() {
        doRender = false;
        super.pause();
    }

    @Override
    public void resume() {
        doRender = true;
        super.resume();
    }

    @Override
    public void render() {
        if (doRender) super.render();
    }

    @Override
    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            resume();
        }
    }
}
