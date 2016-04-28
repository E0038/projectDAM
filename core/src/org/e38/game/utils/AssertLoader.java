package org.e38.game.utils;

import com.badlogic.gdx.files.FileHandle;
import org.e38.game.persistance.ProfileManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class AssertLoader {
    public static FileHandle profiles;
    public volatile static AtomicBoolean isLoaded = new AtomicBoolean(false);



    public static void load() {
        ProfileManager.getProfile();//load Static
        // TODO: 4/27/16 load asserts
        isLoaded.set(true);
    }

}
