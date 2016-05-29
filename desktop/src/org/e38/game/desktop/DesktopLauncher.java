package org.e38.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.e38.game.MainGame;
import org.e38.game.World;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Bank Defense";
        cfg.height = World.WORLD_HEIGHT;
        cfg.width = World.WORLD_WIDTH;
        cfg.resizable = false;
        new LwjglApplication(new MainGame(), cfg);
    }

    /*
    scripting testing code

from org.e38.game import *
from com.badlogic.gdx.backends.lwjgl import *
from com.badlogic.gdx import *
conf = LwjglApplicationConfiguration()
game = MainGame()
LwjglApplication(game,conf)

     */
}
