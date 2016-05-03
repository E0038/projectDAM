package org.e38.game.persistance;

import org.e38.game.model.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/21/16.
 */
public class Profile {
    private List<Level> completeLevels = new ArrayList<Level>(

    );

    public Profile() {
    }

    public List<Level> getCompleteLevels() {
        return completeLevels;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profile{");
        sb.append("completeLevels=").append(completeLevels);
        sb.append('}');
        return sb.toString();
    }
    /*
     * jyrhon test scritp
     *
     from java.net import URL, URLClassLoader
     from java.lang import ClassLoader
     from java.io import File
     m = URLClassLoader.getDeclaredMethod("addURL", [URL])
     m.accessible = 1
     m.invoke(ClassLoader.getSystemClassLoader(), [File("/home/sergi/Projects/java/gdx/projectDAM/out/artifacts/projectDAM_jar/projectDAM.jar").toURL()])
     from org.e38.game.persistance import Profile
     */
}
