package org.e38.game;

import com.badlogic.gdx.Game;
import org.e38.game.screens.SplashScreen;

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
//        try {
//            SecretKey key = KeyGenerator.getInstance("AES").generateKey();
//            Writer writer = new FileWriter("key");
//            writer.write(Base64.getEncoder().encodeToString(key.getEncoded()));
//            writer.close();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
