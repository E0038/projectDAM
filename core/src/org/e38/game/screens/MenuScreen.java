package org.e38.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.e38.game.MainGame;
import org.e38.game.Recurses;
import org.e38.game.World;
import org.e38.game.model.Level;
import org.e38.game.model.npc.NPC;

/**
 * Created by sergi on 4/20/16.
 */
public class MenuScreen implements Screen {
    private final MainGame game;
    int count = 0;
    AnimationManager[] animationManagers;
    float stateTime = 0;
    Level level = new Level(0, "grafics/map1/Mapa_lvl1.tmx");
    private String[] polis = new String[]{Recurses.POLICIA_ESCOPETA, Recurses.POLICIA_BAZOOKA, Recurses.SNIPER_BUENO, Recurses.POLICIA_BUENO};
    private SpriteBatch batcher;
    private Recurses.AnimatedCriminals[] criminals = Recurses.AnimatedCriminals.values();
    private Stage stage;

    public MenuScreen(final MainGame game) {
        this.game = game;
        Gdx.app.log(getClass().getName(), "MENU SCREEN");
    }

    private void newGame() {

    }

    private void continueGame() {

    }

    private void selectLevel() {

    }

    public void onShowRanking() {

    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT));
        TextButton button = new TextButton("new Game",new TextButton.TextButtonStyle());
        batcher = new SpriteBatch();
        game.resume();//fix false pause state
//        debugShow();

    }

    @Override
    public void render(float delta) {
        stage.draw();
//        debugRender(delta);
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
    }

    private void debugShow() {
        for (int i = 0; i < polis.length; i++) {
            String poli = polis[i];
            TextureRegion region = World.getRecurses().getPolicia(poli, NPC.Orientation.LEFT);
            System.out.println(poli + "{\nwidth = " + region.getRegionWidth() + "\nheight = " + region.getRegionHeight() + "\n}");
        }
        animationManagers = new AnimationManager[criminals.length];
        for (int i = 0; i < animationManagers.length; i++) {
            System.out.println(criminals[i].name());
            NPC.Orientation orientation = NPC.Orientation.RIGHT;// NPC.Orientation.values()[(int) (Math.random() * 4)];
            System.out.println(orientation.name());
            animationManagers[i] = World.getRecurses().getACriminal(criminals[i].name(), orientation);
        }
        //Gdx.input.setInputProcessor(new InputHandler(level, ));

    }

    private void debugRender(float delta) {
        //        Gdx.app.log(getClass().getName(), "RENDER");
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        int x = 0;
        int x2 = 0;
        for (int i = 0; i < 10; i++) {

            TextureRegion region = World.getRecurses().getPolicia(polis[i % polis.length], NPC.Orientation.LEFT);
            batcher.draw(region, x, 0);
            x += region.getRegionWidth();
            TextureRegion criminal = animationManagers[i % criminals.length].update(delta);
            batcher.draw(criminal, x2, 100);
            x2 += criminal.getRegionWidth();
        }

        for (MapObject object : level.getLayer().getObjects()) {
//            if (object.getProperties().get("type") != null  && object.getProperties().get("type").equals("camino")){
            float x3 = ((Float) object.getProperties().get("x"));
            float y = ((Float) object.getProperties().get("y"));
//                System.out.println(x + " : " +y);
            batcher.draw(World.getRecurses().getPolicia(Recurses.POLICIA_BUENO, NPC.Orientation.LEFT), x3, y);

//            }
        }
        batcher.end();
        count++;
    }
}
