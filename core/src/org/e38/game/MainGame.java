package org.e38.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.e38.game.logic.AssertLoader;
import org.e38.game.screens.SplashScreen;

public class MainGame extends Game {
    SpriteBatch batch;
    Texture img;


    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("grafics/badlogic.jpg");
        new Thread(new Runnable() {
            @Override
            public void run() {
                AssertLoader.load();
            }
        }, "contextLoaderThread").start();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }
}
