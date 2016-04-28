package org.e38.game.model;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergi on 4/20/16.
 */
public class Path {
    public List<Route> routes = new ArrayList<Route>();

    public static class Route {
        public List<Vector2> positions = new ArrayList<Vector2>();
    }
}

