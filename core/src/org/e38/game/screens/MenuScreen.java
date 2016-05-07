package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.e38.game.MainGame;
import org.e38.game.World;
import org.e38.game.Recurses;
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
        batcher = new SpriteBatch();
        World.play(Recurses.SILENCER);
    }

    @Override
    public void render(float delta) {
        batcher.begin();
        for (int i = 0; i < 10; i++) {
            batcher.draw(World.getRecurses().getPolicia(Recurses.POLICIA_BAZOOKA, NPC.Orientation.DOWN), i * 30, 0);
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
