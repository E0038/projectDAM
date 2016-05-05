package org.e38.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.e38.game.screens.SplashScreen;
import org.e38.game.grafics.Recurses;

public class MainGame extends Game {
    SpriteBatch batch;
    Texture img;
    AssetManager am;


    @Override
    public void create() {
//        ProfileManager.getProfile();
        batch = new SpriteBatch();
        img = new Texture("data/textures/policiaBueno.png");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Recurses.load();//does nothing ,but jvm will call static initializer
            }
        }, "contextLoaderThread").start();
        setScreen(new SplashScreen(this));
        am = Recurses.load();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void setScreen(Screen screen) {
        synchronized (this) {
            if (this.screen != null) {
                this.screen.hide();
                this.screen.dispose();
            }
            this.screen = screen;
            if (this.screen != null) {
                this.screen.show();
                this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
        }
    }
}
