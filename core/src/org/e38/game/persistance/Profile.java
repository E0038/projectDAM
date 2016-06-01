package org.e38.game.persistance;

import java.util.HashMap;

/**
 * Created by sergi on 4/21/16.
 */
public class Profile {
    private HashMap<Integer, Integer> completeLevels = new HashMap<>();

    public Profile() {
    }

    public HashMap<Integer, Integer> getCompleteLevels() {
        return completeLevels;
    }

    @Override
    public String toString() {
        return "Profile{" + "completeLevels=" + completeLevels +
                '}';
    }
}
