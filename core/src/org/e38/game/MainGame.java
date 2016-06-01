package org.e38.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.e38.game.screens.SplashScreen;
import org.e38.game.utils.Recurses;
import org.e38.game.utils.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainGame extends Game {
    public static final AtomicBoolean doRender = new AtomicBoolean(true);
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
    }

    @Override
    public void pause() {
        doRender.set(false);
        super.pause();
    }

    @Override
    public void resume() {
        doRender.set(true);
        super.resume();
    }

    @Override
    public void render() {
        if (doRender.get()) super.render();
    }

    @Override
    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.dispose();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            resume();
        }
    }
}
