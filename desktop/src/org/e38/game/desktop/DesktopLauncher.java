package org.e38.game.desktop;

import org.e38.game.persistance.ProfileManager;

public class DesktopLauncher {
    public static void main(String[] arg) {
//        System.out.println(new Gson().toJson(new Profile()));
//        System.out.println(new Gson().fromJson(,"{\"completeLevels\": []}"));
        ProfileManager.getProfile();
//        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//        new LwjglApplication(new MainGame(), config);
    }
}
