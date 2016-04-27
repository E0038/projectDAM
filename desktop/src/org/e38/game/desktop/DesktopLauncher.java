package org.e38.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.google.gson.Gson;
import org.e38.game.GameLoader;
import org.e38.game.persistance.Profile;
import org.e38.game.persistance.ProfileManager;

import java.io.Reader;

public class DesktopLauncher {
    public static void main(String[] arg) {
//        System.out.println(new Gson().toJson(new Profile()));
//        System.out.println(new Gson().fromJson(,"{\"completeLevels\": []}"));
        ProfileManager.getProfile();
//        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        new LwjglApplication(new GameLoader(), config);
    }
}
