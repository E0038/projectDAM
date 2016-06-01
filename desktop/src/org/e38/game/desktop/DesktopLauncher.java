package org.e38.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.e38.game.MainGame;
import org.e38.game.utils.World;

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
#1r RUN :desktop.other.dist
import os,sys
sys.path.append(os.path.abspath("../desktop/build/libs/desktop-1.0.jar"))
from org.e38.game import *
from org.e38.game.utils import *
from org.e38.game.model import *
from org.e38.game.hud import *
from com.badlogic.gdx.backends.lwjgl import *
from com.badlogic.gdx import *

conf = LwjglApplicationConfiguration()
conf.height = World.WORLD_HEIGHT
conf.width = World.WORLD_WIDTH
game = MainGame()
LwjglApplication(game,conf)

     */
}
