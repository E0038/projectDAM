package org.e38.game.screens;

import com.badlogic.gdx.Screen;
import org.e38.game.MainGame;
import org.e38.game.World;
import org.e38.game.grafics.Recurses;

/**
 * Created by sergi on 4/20/16.
 */
public class MenuScreen implements Screen {
    private MainGame game;

    public MenuScreen(MainGame game) {
        for (int i = 0; i < 5; i++) {
            World.play(Recurses.GUN);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            World.play(Recurses.GUN);
        }
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
