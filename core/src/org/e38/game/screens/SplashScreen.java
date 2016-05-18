package org.e38.game.screens;

import com.badlogic.gdx.Screen;
import org.e38.game.LevelImpl;
import org.e38.game.MainGame;
import org.e38.game.World;

/**
 * Created by sergi on 4/22/16.
 */
public class SplashScreen implements Screen {
    private final MainGame game;

    public SplashScreen(final MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (World.getRecurses().isLoaded.get()) {
            LevelImpl lvl = new LevelImpl(0, 0);
            game.setScreen(new LevelScreen(lvl, game));
//            game.setScreen(new MenuScreen(game));
        } else {
            System.out.println("loading...");
        }
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
        game.pause();
    }

    @Override
    public void dispose() {
    }

    private void initError(Throwable throwable) {
        // TODO: 5/1/16 show error message
        System.exit(-1);
    }
}
