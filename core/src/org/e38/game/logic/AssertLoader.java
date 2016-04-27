package org.e38.game.logic;

import com.badlogic.gdx.files.FileHandle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class AssertLoader {
    public static FileHandle profiles;
    public volatile static AtomicBoolean isLoaded = new AtomicBoolean(false);



    public static void load() {
        // TODO: 4/27/16 load asserts
        isLoaded.set(true);
    }

}
