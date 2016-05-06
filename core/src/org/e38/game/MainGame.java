package org.e38.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.e38.game.grafics.Recurses;
import org.e38.game.screens.SplashScreen;

public class MainGame extends Game {
    SpriteBatch batch;
    Texture img;
    AssetManager am;


    @Override
    public void create() {
//        ProfileManager.getProfile();
        batch = new SpriteBatch();
        img = new Texture("grafics/badlogic.jpg");
        final Recurses recurses = new Recurses();
        World.setRecurses(recurses);
        new Thread(new Runnable() {
            @Override
            public void run() {
                recurses.load();
            }
        }, "contextLoaderThread").start();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.dispose();
        super.setScreen(screen);
    }
}
