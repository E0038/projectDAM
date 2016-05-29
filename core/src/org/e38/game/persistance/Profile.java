package org.e38.game.persistance;

import org.e38.game.model.Level;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sergi on 4/21/16.
 */
public class Profile {
    //TODO : migrate to Idx of levels array
    private Set<Level> completeLevels = new HashSet<>();

    public Profile() {
    }

    public Set<Level> getCompleteLevels() {
        return completeLevels;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profile{");
        sb.append("completeLevels=").append(completeLevels);
        sb.append('}');
        return sb.toString();
    }
}
