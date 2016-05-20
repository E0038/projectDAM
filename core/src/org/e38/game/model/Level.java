package org.e38.game.model;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.e38.game.World;
import org.e38.game.model.npc.Cop;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sergi on 4/20/16.
 */
public abstract class Level {

    private static final float MODIFICADOR_VIDAS = 10;
    public Dificultat dificultat;
    public List<Cop> cops = new ArrayList<Cop>();
    public List<Wave> remaingWaves = new ArrayList<Wave>();
    public TiledMap map;
    protected int coins;
    protected int lifes;
    protected Path path;
    protected int levelUID;
    private List<OnEndListerner> onEndListerners;
    private OrthogonalTiledMapRenderer renderer;
    private MapLayer layer;
    public FileHandle waves;


    /**
     * C style boolean : 0 false , 1 true
     */
    private byte isWined = 0;

    protected Level(int initialCoins, int levelUID) {
        this.coins = initialCoins;
        this.levelUID = levelUID;
        dificultat = Dificultat.valueOf(World.selecteDificultat);
        map = new TmxMapLoader().load("grafics/map1/Mapa_lvl1.tmx");
        layer = map.getLayers().get("objetos");
        renderer = new OrthogonalTiledMapRenderer(map);
    }


    public void addOnEndListerner(OnEndListerner onEndListerners) {
        this.onEndListerners.add(onEndListerners);
    }

    public void removeOnEndListerner(OnEndListerner onEndListerner) {
        this.onEndListerners.remove(onEndListerner);
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
        this.coins = coins;
        return this;
    }

    public int getLifes() {
        return lifes;
    }

    public Level setLifes(int lifes) {
        this.lifes = lifes;
        return this;
    }

    public Path getPath() {
        return path;
    }

    public Level setPath(Path path) {
        this.path = path;
        return this;
    }

    public int getLevelUID() {
        return levelUID;
    }

    public Level setLevelUID(int levelUID) {
        this.levelUID = levelUID;
        return this;
    }

    public Dificultat getDificultat() {
        return dificultat;
    }

    public Level setDificultat(Dificultat dificultat) {
        this.dificultat = dificultat;
        return this;
    }

    public TiledMap getMap() {
        return map;
    }

    public MapLayer getLayer() {
        return layer;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public int getScore() {
        return (int) ((coins * dificultat.modificadorPuntos) + (lifes * MODIFICADOR_VIDAS)) * isWined;
    }

    protected abstract void onStart();

    protected abstract void onDestroy();

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

    public abstract void onRestart();

    public abstract Level fromJson();

    public enum Dificultat {
        FACIL(0.4f), NORMAL(0.6f), DIFICIL(1f);
        private float modificadorPuntos;

        Dificultat(float modificadorPuntos) {
            this.modificadorPuntos = modificadorPuntos;
        }
    }

    public interface OnEndListerner {
        void onEnd(boolean isWined);
    }
}
