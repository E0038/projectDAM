package org.e38.game.model;


import org.e38.game.World;


/**
 * Created by sergi on 4/20/16.
 */
public abstract class Level{

    private static final float MODIFICADOR_VIDAS = 10;
    public Dificultat dificultat;
    protected int coins;
    protected int lifes;
    protected Path path;
    protected int levelUID;
    /**
     * C style boolean : 0 false , 1 true
     */
    private int isWined = 0;


    protected Level(int initialCoins, int levelUID) {
        this.coins = initialCoins;
        this.levelUID = levelUID;
        dificultat = Dificultat.valueOf(World.selecteDificultat);
    }

    public int getIsWined() {
        return isWined;
    }

    public Level setIsWined(int isWined) {
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

    public int getScore() {
        return (int) ((coins * dificultat.modificadorPuntos) + (lifes * MODIFICADOR_VIDAS)) * isWined;
    }

    protected abstract void onStart();

    protected abstract void onDestroy();

//    protected abstract void onRestart();

    public abstract void onEnd();

    public abstract void onRestart();

    public enum Dificultat {
        FACIL(0.4f), NORMAL(0.6f), DIFICIL(1f);
        private float modificadorPuntos;

        Dificultat(float modificadorPuntos) {
            this.modificadorPuntos = modificadorPuntos;
        }
    }
    public abstract Level fromJson();
}
