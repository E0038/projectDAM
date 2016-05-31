package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.utils.World;

/**
 * Created by ALUMNEDAM on 31/05/2016.
 */
public class SettingsScreen implements Screen {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private final MainGame game;
    private Stage stage;
    private Slider slider;
    private TextButton menu;

    public SettingsScreen(MainGame game) {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        this.game = game;
        createButtons();
        configureButtons();

    }


    private void createButtons() {
        TextureRegionDrawable defaultDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(defaultDrawable, defaultDrawable, defaultDrawable, new BitmapFont());
        style.fontColor = Color.BLACK;
        menu = new TextButton("Main Menu", style);
    }

    private void configureButtons() {
        float centerX = (stage.getViewport().getWorldWidth() / 2) - World.getRecurses().buttonBg.getWidth() / 2;
        float bttWidth = World.getRecurses().buttonBg.getWidth();
        float bttHeight = World.getRecurses().buttonBg.getHeight();

        menu.setSize(bttWidth, bttHeight);
        menu.setY((stage.getViewport().getWorldHeight() / 10) * 6);
        menu.setX(centerX);

        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
    }
    @Override
    public void show() {
        slider = new Slider(0, 1, 0.1f, false, new Slider.SliderStyle( new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg)), new TextureRegionDrawable(new TextureRegion(World.getRecurses().knob))));
        slider.setValue(0.3f);
        slider.setSize(100, 50);
        slider.setPosition(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2);
        slider.setAnimateDuration(0.3f);
        //TODO hacer visible el movimento del knob
        slider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("slider: " + slider.getValue());
                World.volumeChange(slider.getValue());

            }
        });
        stage.addActor(menu);
        stage.addActor(slider);
        Gdx.input.setInputProcessor(stage);
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
        game.pause();//auto pause when user leave game window
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
