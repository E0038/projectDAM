package org.e38.game.screens;

import com.badlogic.gdx.Screen;
import org.e38.game.MainGame;
import org.e38.game.World;
import org.e38.game.model.Level;
import org.e38.game.model.Wave;
import org.e38.game.model.npc.Criminal;
import org.e38.game.persistance.ProfileManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sergi on 4/22/16.
 */
public class SplashScreen implements Screen {
    private final MainGame game;

    public SplashScreen(final MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (World.getRecurses().isLoaded.get()) {
            Level lvl = new Level(0, "grafics/map1/Mapa_lvl2.tmx");
            ArrayList<Wave> waves = new ArrayList<>();
            waves.add(new Wave(Arrays.asList(new Criminal(), new Criminal())));
            lvl.waves=waves;
            Level level = ProfileManager.getProfile().gson.fromJson("{\"coins\":0,\"INIT_LIFES\":0,\"waves\":[{\"criminals\":[{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"},{\"state\":\"SPAWING\",\"hpPoints\":0.0,\"dodgeRate\":0.0,\"protecion\":0.0,\"orientation\":\"LEFT\",\"pathPointer\":0,\"totalHpPoins\":10.0,\"name\":\"bane\"}],\"gap\":0.0}],\"mapPath\":\"grafics/map1/Mapa_lvl1.tmx\",\"waveGap\":3000.0}\n",Level.class);
            System.out.println(ProfileManager.getProfile().gson.toJson(lvl));
            System.out.println(level);
            lvl.onStart();
            game.setScreen(new LevelScreen(lvl, game));
//            game.setScreen(new MenuScreen(game));
        } else {
//            System.out.println("loading...");
        }
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

    private void initError(Throwable throwable) {
        // TODO: 5/1/16 show error message
        System.exit(-1);
    }
}
