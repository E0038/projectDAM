package org.e38.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.reflect.TypeToken;
import org.e38.game.World;
import org.e38.game.model.Level;
import org.e38.game.persistance.ProfileManager;

import java.io.IOException;
import java.util.List;

/**
 * Created by sergi on 4/28/16.
 */
public class LevelSelectScreen implements Screen {
    public static final int TABLE_COLS = 5;
    private static final int TABLES_ROWS = 6;
    private final Game game;
    private Stage stage;
    private List<Level> levels;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Table table;

    public LevelSelectScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        TextureRegionDrawable defaultDrawable = new TextureRegionDrawable(new TextureRegion(World.getRecurses().buttonBg));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(defaultDrawable, defaultDrawable, defaultDrawable, new BitmapFont());
        stage.addActor(new Label("Hello World!!", new Label.LabelStyle(new BitmapFont(), Color.BLACK)));
        try {
            String json = ProfileManager.readChars(Gdx.files.internal("raw/rawLevels.json").file());
            levels = ProfileManager.getInstance().gson.fromJson(json, new TypeToken<List<Level>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        skin.add("top_bar", new Texture("grafics/hud/top_bar.png"));

        table = new Table(new Skin());

        float actorWidth = Gdx.graphics.getWidth() / TABLES_ROWS;
        float actorHeight = Gdx.graphics.getHeight() / TABLE_COLS;
        int colCount = 0;
        for (int i = 0, size = levels.size(); i < size; i++) {
            TextButton actor = new TextButton("Level - " + (i + 1), buttonStyle);
            final int finalI = i;
            actor.addListener(new ClickListener() {
                int idx = finalI;

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new LevelScreen(levels.get(idx), game));
                    super.clicked(event, x, y);
                }
            });
            actor.setSize(actorWidth, actorHeight);
            table.add(actor).width(actorWidth).height(actorHeight).pad(10);

            if (++colCount > TABLE_COLS)
                table.row();
        }

        //DEBUG
//        Level lvl = new Level(0, "grafics/map1/Mapa_lvl1.tmx");
//        ArrayList<Wave> waves = new ArrayList<>();
//        waves.add(new Wave(Arrays.asList(new Criminal(), new Criminal())));
//        lvl.waves = waves;
//        Level level = ProfileManager.getInstance().gson.fromJson("{\"coins\":0,\"INIT_LIFES\":0,\"waves\":[{\"criminals\":[{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"},{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"}],\"gap\":0.0}],\"mapPath\":\"grafics/map1/Mapa_lvl1.tmx\",\"waveGap\":3000.0}\n", Level.class);
//        System.out.println(ProfileManager.getInstance().gson.toJson(lvl));
//        System.out.println(level);
//        lvl.onCreate();
//        game.setScreen(new LevelScreen(lvl, game));
        //END
        table.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
//        table.drawDebug(shapeRenderer);
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
}
