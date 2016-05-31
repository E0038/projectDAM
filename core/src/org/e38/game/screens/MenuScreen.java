package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import org.e38.game.model.Level;
import org.e38.game.persistance.Profile;
import org.e38.game.persistance.ProfileManager;
import org.e38.game.utils.AnimationManager;
import org.e38.game.utils.Recurses;
import org.e38.game.utils.World;

import java.util.*;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
// TODO: 5/29/16 add ranking
public class MenuScreen implements Screen {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private final MainGame game;
    int count = 0;
    AnimationManager[] animationManagers;
    private String[] polis = new String[]{Recurses.POLICIA_ESCOPETA, Recurses.POLICIA_BAZOOKA, Recurses.SNIPER_BUENO, Recurses.POLICIA_BUENO};
    private SpriteBatch batcher;
    private Recurses.AnimatedCriminals[] criminals = Recurses.AnimatedCriminals.values();
    private Stage stage;
    private TextButton newGame;
    private TextButton continueGame;
    private TextButton selectLevel;
    private Label title;
    private ImageButton exit;
    private ImageButton volumeSwitch;
    private TextureRegionDrawable umuteDrawable;
    private TextureRegionDrawable muteDrawable;
    private TextButton ranking;
    private TextButton settings;
    private Dialog rankingDialog;

    public MenuScreen(final MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        createButtons();
        configureButtons();
        game.resume();//fix false pause state
        stage.getActors().addAll(selectLevel, continueGame, newGame, title, exit, volumeSwitch, ranking, settings);
        rankingDialog = new Dialog("Ranking", new Window.WindowStyle(new BitmapFont(), new Color(Color.BLACK), new TextureRegionDrawable(new TextureRegion(World.getRecurses().cuadradoBlanco))));
        Gdx.input.setInputProcessor(stage);
    }

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

        title = new Label("Bank Defense", new Label.LabelStyle(new BitmapFont(), Color.BLACK));


        this.exit = new ImageButton(new TextureRegionDrawable(new TextureRegion(World.getRecurses().exitBtt)));

        umuteDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().unmute));
        muteDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().mute));

        volumeSwitch = new ImageButton(!World.isMuted() ? umuteDrawable : muteDrawable);

        ranking = new TextButton("Ranking", new TextButton.TextButtonStyle(style));
        settings = new TextButton("Ajustes", new TextButton.TextButtonStyle(style));
    }

    private void configureButtons() {
        float centerX = (stage.getViewport().getWorldWidth() / 2) - World.getRecurses().buttonBg.getWidth() / 2;
        float bttWidth = World.getRecurses().buttonBg.getWidth();
        float bttHeight = World.getRecurses().buttonBg.getHeight();

        title.setFontScale(1.5f);
        title.setX(centerX + title.getWidth() / 2);
        title.setY((stage.getViewport().getWorldHeight() / 10) * 8);

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


        boolean isNewGame = ProfileManager.getInstance().getProfile().getCompleteLevels().size() == 0;
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
    }

    private void fillRanking(){
        //TODO Separar boton respecto a la tabla
        Table table = new Table();
        table.add(new Label("Nivel:", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));;
        table.add(new Label ("Puntuación:", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        Map<Integer, Integer> puntuaciones = ProfileManager.getInstance().getGameProgres();
        for (int level :puntuaciones.keySet()) {//TODO comprobar funcionamiento con ranking lleno (ProfileManager)
            table.row();
            table.add(new Label(String.valueOf(level), new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
            table.add(new Label(String.valueOf(puntuaciones.get(level)), new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        }
        rankingDialog.add(table);
        rankingDialog.getContentTable().add(table).padTop(-70).padLeft(-150);

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton dbutton = new TextButton("Volver al menú", new TextButton.TextButtonStyle(drawable, drawable, drawable, new BitmapFont()));
        dbutton.getStyle().fontColor = Color.BLACK;
        dbutton.setSize(10, 10);
        rankingDialog.button(dbutton, true);
        rankingDialog.getButtonTable().add(dbutton).padTop(10);
        dbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    private void newGame() {
        if (ProfileManager.getInstance().newGame()) {
            game.setScreen(new LevelSelectScreen(game));
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
            List<Level> list = new ArrayList<>(profile.getCompleteLevels());
            list.get(list.size() - 1);
            Level lastComplete = Collections.max(list, new Comparator<Level>() {
                @Override
                public int compare(Level o1, Level o2) {
                    return World.levels.indexOf(o1) - World.levels.indexOf(o2);
                }
            });
            game.setScreen(new LevelScreen(World.levels.get(World.levels.indexOf(lastComplete)), game));
        } else {
            selectLevel();
        }
        World.play(Recurses.POP);
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

//    private void debugShow() {
//        batcher = new SpriteBatch();
//        for (String poli : polis) {
//            TextureRegion region = World.getRecurses().getPolicia(poli, NPC.Orientation.LEFT);
//            System.out.println(poli + "{\nwidth = " + region.getRegionWidth() + "\nheight = " + region.getRegionHeight() + "\n}");
//        }
//        animationManagers = new AnimationManager[criminals.length];
//        for (int i = 0; i < animationManagers.length; i++) {
//            System.out.println(criminals[i].name());
//            NPC.Orientation orientation = NPC.Orientation.RIGHT;// NPC.Orientation.values()[(int) (Math.random() * 4)];
//            System.out.println(orientation.name());
//            animationManagers[i] = World.getRecurses().getACriminal(criminals[i].name(), orientation);
//        }
//
//    }
//
//    private void debugRender(float delta) {
//        Level level = new Level(0, "grafics/map1/Mapa_lvl1.tmx");
//        //        Gdx.app.log(getClass().getName(), "RENDER");
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batcher.begin();
//        int x = 0;
//        int x2 = 0;
//        for (int i = 0; i < 10; i++) {
//
//            TextureRegion region = World.getRecurses().getPolicia(polis[i % polis.length], NPC.Orientation.LEFT);
//            batcher.draw(region, x, 0);
//            x += region.getRegionWidth();
//            TextureRegion criminal = animationManagers[i % criminals.length].update(delta);
//            batcher.draw(criminal, x2, 100);
//            x2 += criminal.getRegionWidth();
//        }
//
//        for (MapObject object : level.getLayer().getObjects()) {
////            if (object.getProperties().get("type") != null  && object.getProperties().get("type").equals("camino")){
//            float x3 = (Float) object.getProperties().get("x");
//            float y = (Float) object.getProperties().get("y");
////                System.out.println(x + " : " +y);
//            batcher.draw(World.getRecurses().getPolicia(Recurses.POLICIA_BUENO, NPC.Orientation.LEFT), x3, y);
//
////            }
//        }
//        batcher.end();
//        count++;
//    }
}
