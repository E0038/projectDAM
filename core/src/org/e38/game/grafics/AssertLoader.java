package org.e38.game.grafics;

import com.badlogic.gdx.files.FileHandle;
import org.e38.game.persistance.ProfileManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class AssertLoader {
    public volatile static AtomicBoolean isLoaded = new AtomicBoolean(false);



    public static void load() {
        ProfileManager.getProfile();//load persistence Static Context
        // TODO: 4/27/16 load asserts
        isLoaded.set(true);
    }

}
