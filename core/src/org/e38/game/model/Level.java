package org.e38.game.model;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.npcs.Criminal;
import org.e38.game.utils.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by sergi on 4/20/16.
 */
public class Level {

    public static final int TYPE_COIN = 0;
    public static final int TYPE_LIFE = 1;
    public static final int PADING = 4;
    public static final float DEFAULT_GAP = 3000f;
    private static final float MODIFICADOR_VIDAS = 10;
    public Dificultat dificultat = Dificultat.NORMAL;
    //    public TiledMap map;
    public List<Wave> waves;
    //    public List<Wave> remaingWaves = new ArrayList<Wave>();
    public int wavePointer = 0;
    public String mapPath;
    public float waveGap = DEFAULT_GAP;
    public List<OnChangeStateListener> onChangeStateList = new ArrayList<>();
    //    private MapLayer layer;
    public List<OnEndListerner> onEndListerners = new ArrayList<>();
    public List<Vector2> pathv2 = new ArrayList<>();
    protected int initLifes;
    protected int initCoins;
    protected int coins;
    //    protected List<MapObject> path = new ArrayList<>();
    protected int lifes;
    /**
     * C style boolean : 0 false , 1 true
     */
    private byte isWined = 0;

    public Level() {
    }

    public Level(int initialCoins, String path) {
        this.mapPath = path;
        this.coins = initialCoins;
//        this.levelUID = levelUID;
    }

    public int getInitCoins() {
        return initCoins;
    }

    public Level setInitCoins(int initCoins) {
        this.initCoins = initCoins;
        return this;
    }

    public Level setWaveGap(float waveGap) {
        this.waveGap = waveGap;
        return this;
    }

    public int getInitLifes() {
        return initLifes;
    }

    public Level setInitLifes(int initLifes) {
        this.initLifes = initLifes;
        return this;
    }

    public String getMapPath() {
        return mapPath;
    }

    public Level setMapPath(String mapPath) {
        this.mapPath = mapPath;
        return this;
    }

    public void addOnEndListerner(OnEndListerner onEndListerners) {
        this.onEndListerners.add(onEndListerners);
    }

    public void removeOnEndListerner(OnEndListerner onEndListerner) {
        this.onEndListerners.remove(onEndListerner);
    }

    public void addOnChangeStateListerner(OnChangeStateListener onChangeStateListener) {
        this.onChangeStateList.add(onChangeStateListener);
    }

    public void removeOnChangeStateListerner(OnChangeStateListener onChangeStateListener) {
        this.onChangeStateList.remove(onChangeStateListener);
    }

    public byte getIsWined() {
        return isWined;
    }

    public Level setIsWined(byte isWined) {
        this.isWined = isWined;
        return this;
    }

    public int getCoins() {
        return coins;
    }

    public Level setCoins(int coins) {
        int old = this.coins;
        this.coins = coins;
        if (this.coins < 0) this.coins = 0;
        for (OnChangeStateListener change : onChangeStateList) {
            change.onChangeState(old, this.coins, TYPE_COIN);
        }
        return this;
    }

    public int getLifes() {
        return lifes;
    }

    public Level setLifes(int lifes) {
        int old = this.lifes;
        this.lifes = lifes;
        if (this.lifes < 0) this.lifes = 0;
        for (OnChangeStateListener change : onChangeStateList) {
            change.onChangeState(old, this.lifes, TYPE_LIFE);
        }
        if (lifes <= 0) {
            fail();
        }

        return this;
    }

    public void fail() {
        isWined = 0;
        onEnd();
    }

    public void onEnd() {
        for (OnEndListerner listerner : onEndListerners) {
            listerner.onEnd(isWined != 0);
        }
    }

    public List<Vector2> getPath() {
        return pathv2;
    }

//    public Level setPath(List<MapObject> path) {
//        this.path = path;
//        return this;
//    }

    public Dificultat getDificultat() {
        return dificultat;
    }

    public Level setDificultat(Dificultat dificultat) {
        this.dificultat = dificultat;
        return this;
    }

    public int getScore() {
        return (int) ((coins * dificultat.modificadorPuntos) + (lifes * MODIFICADOR_VIDAS)) * isWined;
    }

    protected void onDestroy() {
    }

    public void win() {
        isWined = 1;
        onEnd();
    }

    public void onRestart() {
        isWined = 0;
        wavePointer = 0;
        onEndListerners = new ArrayList<>();
        onCreate();
    }

    /**
     * extra init code with OpenGl context enabled
     */
    public void onCreate() {
        dificultat = Dificultat.valueOf(World.selecteDificultat);
        TiledMap map = new TmxMapLoader().load(mapPath);
        List<MapObject> mapObjects = buildPath(map);
        //new otimized pre mapped path
        //noinspection ForLoopReplaceableByForEach
        pathv2.clear();
        for (int i = 0, limit = mapObjects.size(); i < limit; i++) {
            MapObject object = mapObjects.get(i);
            pathv2.add(new Vector2((float) object.getProperties().get("x"), (float) object.getProperties().get("y")));
        }
        lifes = initLifes;
        coins = initCoins;
        //add reference to criminals
        for (Wave wave : this.waves) {
            wave.restart();
            for (Criminal criminal : wave.getCriminals()) {
                criminal.setLevel(this);
            }
        }
    }

    private List<MapObject> buildPath(TiledMap map) {
        List<MapObject> caminos = new ArrayList<>();
        for (MapObject object : map.getLayers().get("objetos").getObjects()) {
            if (object.getProperties().get("type") != null && object.getProperties().get("type").equals("camino")) {
                caminos.add(object);
            }
        }
        Collections.sort(caminos, new Comparator<MapObject>() {
            @Override
            public int compare(MapObject o1, MapObject o2) {

                String name1 = "camino" + addPading(o1.getName().substring(6));
                String name2 = "camino" + addPading(o2.getName().substring(6));
                return name1.compareTo(name2);
            }

            private String addPading(String s) {
                StringBuilder builder = new StringBuilder();
                for (int i = s.length(); i < PADING; i++) {
                    builder.append('0');
                }
                builder.append(s);
                return builder.toString();
            }
        });
        return caminos;
    }

//    public MapLayer getLayer() {
//        return map.getLayers().get("objetos");
//    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Level && ((Level) obj).mapPath.equals(this.mapPath);
//    }

    @Override
    public String toString() {
        return "Level{" + "dificultat=" + dificultat +
                ", waves=" + waves +
                ", wavePointer=" + wavePointer +
                ", mapPath='" + mapPath + '\'' +
                ", waveGap=" + waveGap +
                ", initLifes=" + initLifes +
                ", coins=" + coins +
                ", lifes=" + lifes +
//                ", path=" + path +
                ", onEndListerners=" + onEndListerners +
                ", isWined=" + isWined +
                '}';
    }

    public enum Dificultat {
        EASY(0.4f), NORMAL(0.6f), HARD(1f);
        private float modificadorPuntos;

        Dificultat(float modificadorPuntos) {
            this.modificadorPuntos = modificadorPuntos;
        }
    }

    public interface OnChangeStateListener {
        void onChangeState(int oldValue, int newValue, int type);
    }

    public interface OnEndListerner {
        void onEnd(boolean isWined);
    }
}
