package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.e38.game.MainGame;
import org.e38.game.model.Level;
import org.e38.game.persistance.ProfileManager;
import org.e38.game.utils.DisableableTextButon;
import org.e38.game.utils.World;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by sergi on 4/28/16.
 */
public class LevelSelectScreen implements Screen {
    public static final int TABLE_COLS = 5;
    private static final int TABLES_ROWS = 6;
    private final MainGame game;
    private Stage stage;
    private Table table;
    private Image background;

    public LevelSelectScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        createStage();
        Gdx.input.setInputProcessor(stage);
        game.resume();
    }

    @SuppressWarnings("MagicNumber")
    private void createStage() {
        TextureRegionDrawable defaultDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(defaultDrawable, defaultDrawable, defaultDrawable, new BitmapFont());
        table = new Table(new Skin());
        float actorWidth = stage.getViewport().getWorldWidth() / TABLES_ROWS;
        float actorHeight = stage.getViewport().getWorldHeight() / TABLE_COLS;
        fillTable(buttonStyle, actorWidth, actorHeight);
        table.setPosition(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 1.2f);
        background = new Image(World.getRecurses().backgroundSettings);
        background.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.addActor(background);
        stage.addActor(table);
        TextButton returnButon = new TextButton("Back", buttonStyle);
        returnButon.setSize(100, 50);
        returnButon.setPosition(0, 0);
        returnButon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        stage.addActor(returnButon);
    }

    private void fillTable(TextButton.TextButtonStyle buttonStyle, float actorWidth, float actorHeight) {
        int colCount = 0;
        HashMap<Integer, Integer> completeLevels = ProfileManager.getInstance().getProfile().getCompleteLevels();
        int lastLevel = 0;
        if (completeLevels.size() > 0) {
            lastLevel = Collections.max(completeLevels.keySet()) + 1;
        }
        for (int i = 0, size = World.levels.size(); i < size; i++) {
            TextButton actor = new DisableableTextButon("Level - " + (i + 1), new TextButton.TextButtonStyle(buttonStyle));
            final int finalI = i;
            actor.addListener(new ClickListener() {
                int idx = finalI;

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Level level = World.levels.get(idx);
                    level.onRestart();
                    game.setScreen(new LevelScreen(level, game));
                    super.clicked(event, x, y);
                }
            });
            actor.setDisabled(true);
            actor.setSize(actorWidth, actorHeight);
            table.add(actor).width(actorWidth).height(actorHeight).pad(10);

            if (++colCount > 2) {
                table.row();
                colCount = 0;
            }
        }
        for (int i = 0; i <= lastLevel; i++) {
            if (i < table.getCells().size)
                ((TextButton) table.getCells().get(i).getActor()).setDisabled(false);
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
