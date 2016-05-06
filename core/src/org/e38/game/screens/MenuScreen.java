package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.e38.game.MainGame;
import org.e38.game.World;
import org.e38.game.grafics.Recurses;
import org.e38.game.model.npc.NPC;

/**
 * Created by sergi on 4/20/16.
 */
public class MenuScreen implements Screen {
    private final MainGame game;
    private SpriteBatch batcher;

    public MenuScreen(final MainGame game) {
        this.game = game;
        Gdx.app.log(getClass().getName(), "MENU SCREEN");
    }


    public void onShowRanking() {

    }

    @Override
    public void show() {
        Gdx.app.log(getClass().getName(), "MENU SHOW");
    }

    @Override
    public void render(float delta) {
        batcher = new SpriteBatch();
        batcher.begin();
        for (int i = 0; i < 5; i++) {
            batcher.draw(World.getRecurses().getPolicia(Recurses.POLICIA_BAZOOKA, NPC.Orientation.DOWN), i * 30, 0);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            World.play(Recurses.GUN);
        }
        batcher.end();
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
