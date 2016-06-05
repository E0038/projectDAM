package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.persistance.Profile;
import org.e38.game.persistance.ProfileManager;
import org.e38.game.utils.Recurses;
import org.e38.game.utils.World;

import java.util.Collections;
import java.util.Map;

/**
 * Created by sergi on 4/20/16.
 */
public class MenuScreen implements Screen {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private final MainGame game;
    private Stage stage;
    private TextButton newGame;
    private TextButton continueGame;
    private TextButton selectLevel;
    //    private Label title;
    private ImageButton exit;
    private ImageButton volumeSwitch;
    private TextureRegionDrawable umuteDrawable;
    private TextureRegionDrawable muteDrawable;
    private TextButton ranking;
    private TextButton settings;
    private Dialog rankingDialog;
    private Dialog newGameDialog;
    private boolean isNewGame;
    private Image background;

    public MenuScreen(final MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        createButtons();
        configureButtons();
        game.resume();//fix false pause state
        stage.addActor(background);
        stage.getActors().addAll(selectLevel, continueGame, newGame, exit, volumeSwitch, ranking, settings);
        rankingDialog = new Dialog("Ranking", new Window.WindowStyle(new BitmapFont(), new Color(Color.BLACK), new TextureRegionDrawable(new TextureRegion(World.getRecurses().cuadradoBlanco))));
        newGameDialog = new Dialog("New Game", new Window.WindowStyle(new BitmapFont(), new Color(Color.BLACK), new TextureRegionDrawable(new TextureRegion(World.getRecurses().cuadradoBlanco))));
        Gdx.input.setInputProcessor(stage);
    }

    @SuppressWarnings("FeatureEnvy")
    private void createButtons() {
        TextureRegionDrawable defaultDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(defaultDrawable, defaultDrawable, defaultDrawable, new BitmapFont());
        style.fontColor = Color.BLACK;
        newGame = new TextButton("New Game", style);

        continueGame = new TextButton("Continue Game", new TextButton.TextButtonStyle(style)) {
            @Override
            public void setDisabled(boolean isDisabled) {
                getStyle().fontColor = isDisabled ? Color.GRAY : Color.BLACK;
                setTouchable(isDisabled ? Touchable.disabled : Touchable.enabled);
                super.setDisabled(isDisabled);
            }
        };

        selectLevel = new TextButton("Select Level", new TextButton.TextButtonStyle(style)) {
            @Override
            public void setDisabled(boolean isDisabled) {
                getStyle().fontColor = isDisabled ? Color.GRAY : Color.BLACK;
                setTouchable(isDisabled ? Touchable.disabled : Touchable.enabled);
                super.setDisabled(isDisabled);
            }
        };

//        title = new Label("Bank Defense", new Label.LabelStyle(new BitmapFont(), Color.BLACK));


        this.exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(World.getRecurses().exitBtt)));

        umuteDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().unmute));
        muteDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().mute));

        volumeSwitch = new ImageButton(!World.isMuted() ? umuteDrawable : muteDrawable);

        ranking = new TextButton("Ranking", new TextButton.TextButtonStyle(style));
        settings = new TextButton("Settings", new TextButton.TextButtonStyle(style));

        background = new Image(World.getRecurses().background);
    }

    @SuppressWarnings("FeatureEnvy")
    private void configureButtons() {
        float centerX = (stage.getViewport().getWorldWidth() / 2) - World.getRecurses().buttonBg.getWidth() / 2;
        float bttWidth = World.getRecurses().buttonBg.getWidth();
        float bttHeight = World.getRecurses().buttonBg.getHeight();

//        title.setFontScale(1.5f);
//        title.setX(centerX + title.getWidth() / 2);
//        title.setY((stage.getViewport().getWorldHeight() / 10) * 8);

        newGame.setSize(bttWidth, bttHeight);
        newGame.setY((stage.getViewport().getWorldHeight() / 10) * 6);
        newGame.setX(centerX);

        continueGame.setSize(bttWidth, bttHeight);
        continueGame.setY((stage.getViewport().getWorldHeight() / 10) * 5);
        continueGame.setX(centerX);

        selectLevel.setSize(bttWidth, bttHeight);
        selectLevel.setY((stage.getViewport().getWorldHeight() / 10) * 4);
        selectLevel.setX(centerX);

        ranking.setSize(bttWidth, bttHeight);
        ranking.setY((stage.getViewport().getWorldHeight() / 10) * 3);
        ranking.setX(centerX);

        settings.setSize(bttWidth, bttHeight);
        settings.setY((stage.getViewport().getWorldHeight() / 10) * 2);
        settings.setX(centerX);

        exit.setSize(World.getRecurses().exitBtt.getWidth(), World.getRecurses().exitBtt.getHeight());
        exit.setPosition(stage.getViewport().getWorldWidth() - exit.getWidth(), 0);

        volumeSwitch.setSize(World.getRecurses().unmute.getWidth(), World.getRecurses().unmute.getHeight());
        volumeSwitch.setPosition(0, 0);
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newGame();
            }
        });
        selectLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectLevel();
            }
        });
        continueGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueGame();
            }
        });
        ranking.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fillRanking();
                rankingDialog.show(stage);
            }
        });
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        isNewGame = ProfileManager.getInstance().getProfile().getCompleteLevels().size() == 0;
        selectLevel.setDisabled(isNewGame);
        continueGame.setDisabled(isNewGame);

        //noinspection Duplicates
        volumeSwitch.addListener(new ClickListener() {
            @SuppressWarnings("Duplicates")
            @Override
            public void clicked(InputEvent event, float x, float y) {
                volumeSwitch.getStyle().imageUp = World.isMuted() ? umuteDrawable : muteDrawable;
                World.onSwichMuteUnMute();
                World.play(Recurses.POP);
                super.clicked(event, x, y);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                World.exit();
                super.clicked(event, x, y);
            }
        });
        background.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
    }

    @SuppressWarnings({"FeatureEnvy", "MagicNumber"})
    private void newGame() {
        if (!isNewGame) {
            newGameDialog.text(new Label("If you choose that option, all your progess of the game will be removed.\nIf you want to continue with your progres select the menu options:\nContinue or Select level.", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
            Drawable drawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
            TextButton rbutton = new TextButton("Return to the menu", new TextButton.TextButtonStyle(drawable, drawable, drawable, new BitmapFont()));
            rbutton.getStyle().fontColor = Color.BLACK;
            rbutton.setSize(10, 10);
            newGameDialog.button(rbutton, true);
            newGameDialog.getButtonTable().add(rbutton);
            rbutton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MenuScreen(game));
                }
            });
            TextButton rbutton2 = new TextButton("Continue with New Game", new TextButton.TextButtonStyle(drawable, drawable, drawable, new BitmapFont()));
            rbutton2.getStyle().fontColor = Color.BLACK;
            rbutton2.setSize(10, 10);
            newGameDialog.button(rbutton2, true);
            newGameDialog.getButtonTable().add(rbutton2);
            newGameDialog.getButtonTable().padBottom(5);
            newGameDialog.padTop(30).padLeft(10).padRight(14);
            rbutton2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ProfileManager.getInstance().newGame();
                    game.setScreen(new LevelSelectScreen(game));
                }
            });
            newGameDialog.show(stage);
        } else {
            if (ProfileManager.getInstance().newGame()) {
                game.setScreen(new LevelSelectScreen(game));
            }
        }
        World.play(Recurses.POP);
    }

    private void selectLevel() {
        game.setScreen(new LevelSelectScreen(game));
        World.play(Recurses.POP);
    }

    private void continueGame() {
        Profile profile = ProfileManager.getInstance().getProfile();
        if (profile.getCompleteLevels().size() > 0) {
//            List<Level> list = new ArrayList<>(profile.getCompleteLevels());
            Integer lastComplete = Collections.max(profile.getCompleteLevels().keySet()) + 1;
            if (lastComplete >= World.levels.size()) {
                lastComplete = World.levels.size() - 1;
            }
            game.setScreen(new LevelScreen(World.levels.get(lastComplete), game));
        } else {
            selectLevel();
        }
        World.play(Recurses.POP);
    }

    @SuppressWarnings("MagicNumber")
    private void fillRanking() {
        rankingDialog.getTitleLabel().setY(rankingDialog.getTitleLabel().getY() - 5);
        //rankingDialog.get
        Table table = new Table();
        table.add(new Label("Nivel  ", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        table.add(new Label("Puntuación", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        Map<Integer, Integer> puntuaciones = ProfileManager.getInstance().getGameProgres();
        for (int level : puntuaciones.keySet()) {
            table.row();
            table.add(new Label(String.valueOf(level + 1), new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
            table.add(new Label(String.valueOf(puntuaciones.get(level)), new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        }
//        rankingDialog.add(table);
        rankingDialog.getContentTable().add(table).padTop(-50).padLeft(5);

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton dbutton = new TextButton("Back to menu", new TextButton.TextButtonStyle(drawable, drawable, drawable, new BitmapFont()));
        dbutton.getStyle().fontColor = Color.BLACK;
        dbutton.setSize(10, 10);
        rankingDialog.button(dbutton, true);
        rankingDialog.getButtonTable().add(dbutton).padTop(10).padBottom(5);
        rankingDialog.padTop(30).padLeft(5).padRight(5);
        dbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

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
