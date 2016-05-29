package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.e38.game.utils.World;

/**
 * Created by sergi on 4/28/16.
 */
public class LevelSelectScreen implements Screen {
    public static final int TABLE_COLS = 5;
    private static final int TABLES_ROWS = 6;
    private final Game game;
    private Stage stage;
    private Table table;

    public LevelSelectScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        TextureRegionDrawable defaultDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(defaultDrawable, defaultDrawable, defaultDrawable, new BitmapFont());
        table = new Table(new Skin());
        float actorWidth = stage.getViewport().getWorldWidth() / TABLES_ROWS;
        float actorHeight = stage.getViewport().getWorldHeight() / TABLE_COLS;
        fillTable(buttonStyle, actorWidth, actorHeight);
        table.setPosition(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 1.2f);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        game.resume();
    }

    private void fillTable(TextButton.TextButtonStyle buttonStyle, float actorWidth, float actorHeight) {
        int colCount = 0;
        for (int i = 0, size = World.levels.size(); i < size; i++) {
            TextButton actor = new TextButton("Level - " + (i + 1), buttonStyle);
            final int finalI = i;
            actor.addListener(new ClickListener() {
                int idx = finalI;

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new LevelScreen(World.levels.get(idx), game));
                    super.clicked(event, x, y);
                }
            });
            actor.setSize(actorWidth, actorHeight);
            table.add(actor).width(actorWidth).height(actorHeight).pad(10);

            if (++colCount > 2) {
                table.row();
                colCount = 0;
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
        stage.dispose();
    }
}
