package org.e38.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.e38.game.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Juego ladrones";
        cfg.height = 600;
        cfg.width = 800;
        new LwjglApplication(new MainGame(), cfg);
    }
}
