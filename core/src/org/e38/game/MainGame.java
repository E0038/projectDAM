package org.e38.game;

import com.badlogic.gdx.Game;
import org.e38.game.screens.SplashScreen;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MainGame extends Game {
    public boolean doRender = true;

    @Override
    public void create() {
        final Recurses recurses = new Recurses();
        World.setRecurses(recurses);
        new Thread(new Runnable() {
            @Override
            public void run() {
                recurses.load();
            }
        }, "contextLoaderThread").start();
        setScreen(new SplashScreen(this));
        try {
            SecretKey key = KeyGenerator.getInstance("AES").generateKey();
            Writer writer = new FileWriter("key");
            writer.write(new String(Base64.getEncoder().encode(key.getEncoded())));
            writer.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
