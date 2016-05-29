package org.e38.game.model;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.e38.game.utils.World;
import org.e38.game.model.npcs.Cop;
import org.e38.game.model.npcs.Criminal;

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
    private static final float MODIFICADOR_VIDAS = 10;

    public Dificultat dificultat;
    public List<Cop> cops = new ArrayList<>();
//    public TiledMap map;
    public List<Wave> waves;
    //    public List<Wave> remaingWaves = new ArrayList<Wave>();
    public int wavePointer = 0;
    public String mapPath;
    public float waveGap = 3000f;
    public List<OnChangeStateListener> onChangeStateList = new ArrayList<>();
    protected int initLifes;
    protected int coins;
    protected int lifes;
    protected List<MapObject> path = new ArrayList<>();
    //    private MapLayer layer;
    private List<OnEndListerner> onEndListerners = new ArrayList<>();
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
        for (OnChangeStateListener change : onChangeStateList) {
            change.onChangeState(this.coins, coins, TYPE_COIN);
        }
        this.coins = coins;
        return this;
    }

    public int getLifes() {
        return lifes;
    }

    public Level setLifes(int lifes) {
        for (OnChangeStateListener change : onChangeStateList) {
            change.onChangeState(this.lifes, lifes, TYPE_LIFE);
        }
        this.lifes = lifes;
        return this;
    }

    public List<MapObject> getPath() {
        return path;
    }

    public Level setPath(List<MapObject> path) {
        this.path = path;
        return this;
    }

    public Dificultat getDificultat() {
        return dificultat;
    }

    public Level setDificultat(Dificultat dificultat) {
        this.dificultat = dificultat;
        return this;
    }

//    public TiledMap getMap() {
//        return map;
//    }

    public int getScore() {
        return (int) ((coins * dificultat.modificadorPuntos) + (lifes * MODIFICADOR_VIDAS)) * isWined;
    }

    protected void onDestroy() {
    }

    public void fail() {
        isWined = 0;
        onEnd();
    }

    public void onEnd() {
        for (OnEndListerner listerner : onEndListerners) {
            listerner.onEnd(isWined == 1);
        }
    }

    public void win() {
        isWined = 1;
        onEnd();
    }

    public void onRestart() {
        isWined = 0;
        wavePointer = 0;
        onCreate();
    }

    /**
     * extra init code with OpenGl context enabled
     */
    public void onCreate() {
        dificultat = Dificultat.valueOf(World.selecteDificultat);
        TiledMap map = new TmxMapLoader().load(mapPath);
        this.path = buildPath(map,4);
        lifes = initLifes;
        //add reference to criminals
        for (Wave wave : this.waves) {
            for (Criminal criminal : wave.getCriminals()) {
                criminal.setLevel(this);
            }
        }
    }

    private List<MapObject> buildPath(TiledMap map, final int pading) {
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
                for (int i = s.length(); i < pading; i++) {
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Level && ((Level) obj).mapPath.equals(this.mapPath);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Level{");
        sb.append("dificultat=").append(dificultat);
        sb.append(", cops=").append(cops);
//        sb.append(", map=").append(map);
        sb.append(", waves=").append(waves);
        sb.append(", wavePointer=").append(wavePointer);
        sb.append(", mapPath='").append(mapPath).append('\'');
        sb.append(", waveGap=").append(waveGap);
        sb.append(", initLifes=").append(initLifes);
        sb.append(", coins=").append(coins);
        sb.append(", lifes=").append(lifes);
        sb.append(", path=").append(path);
        sb.append(", onEndListerners=").append(onEndListerners);
        sb.append(", isWined=").append(isWined);
        sb.append('}');
        return sb.toString();
    }

    public enum Dificultat {
        FACIL(0.4f), NORMAL(0.6f), DIFICIL(1f);
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
