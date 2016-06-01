package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.utils.Recurses;
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
//    private ProgressBar bar;
    private Label volumeLabel;
    private Label volumeValue;

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
        volumeLabel = new Label("Set Volume", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        volumeValue = new Label("100", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
    }

    private void configureButtons() {
        float bttWidth = World.getRecurses().buttonBg.getWidth();
        float bttHeight = World.getRecurses().buttonBg.getHeight();

        menu.setSize(bttWidth, bttHeight);
        menu.setY((stage.getViewport().getWorldHeight() / 10) * 1);
        menu.setX((stage.getViewport().getWorldWidth() / 2) - World.getRecurses().buttonBg.getWidth() / 2);

        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        slider = new Slider(0, 1, 0.1f, false, new Skin(Gdx.files.internal("skin/uiskin.json")));
        slider.setValue(1f);
        slider.setSize(100, 50);
        slider.setPosition((stage.getViewport().getWorldWidth() / 2) - slider.getWidth() / 2, stage.getViewport().getWorldHeight() / 10 * 8);
        slider.setAnimateDuration(0.3f);
        slider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
//                System.out.println("slider: " + slider.getValue());
                World.volumeChange(slider.getValue());
                World.play(Recurses.POP);
                volumeValue.setText(String.valueOf((int)(slider.getValue() * 100)));

            }
        });

        volumeLabel.setPosition((stage.getViewport().getWorldWidth() / 2) - volumeLabel.getWidth() / 2, stage.getViewport().getWorldHeight() / 10 * 9);
        volumeValue.setPosition((stage.getViewport().getWorldWidth() / 2) - volumeValue.getWidth() + slider.getWidth(), stage.getViewport().getWorldHeight() / 10 * 8 + volumeValue.getHeight());

//        bar = new ProgressBar(0, 100, 1, false, new Skin(Gdx.files.internal("skin/uiskin.json")));
//        bar.setSize(100, 50);
//        bar.setPosition(centerX, stage.getViewport().getWorldHeight() / 10 * 6);
//        bar.setValue(1f);
    }
    @Override
    public void show() {
//        stage.addActor(bar);
        stage.addActor(menu);
        stage.addActor(slider);
        stage.addActor(volumeLabel);
        stage.addActor(volumeValue);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        bar.setValue(slider.getValue()*100);

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