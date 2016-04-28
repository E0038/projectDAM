package org.e38.game.persistance;

import org.e38.game.model.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/21/16.
 */
public class Profile {
    private List<Integer> completeLevels= new ArrayList<Integer>();
    public Profile() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profile{");
        sb.append("completeLevels=").append(completeLevels);
        sb.append('}');
        return sb.toString();
    }
}
