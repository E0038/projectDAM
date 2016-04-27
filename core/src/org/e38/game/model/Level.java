package org.e38.game.model;


import org.e38.game.LevelWorld;


/**
 * Created by sergi on 4/20/16.
 */
public abstract class Level {

    private static final float MODIFICADOR_VIDAS = 10;
    public Dificultat dificultat;
    protected int coins;
    protected int vidas;
    /**
     * C style boolean : 0 false , 1 true
     */
    private int isWined = 0;

    protected Level(int initialCoins) {
        this.coins = initialCoins;
        dificultat = Dificultat.valueOf(LevelWorld.selecteDificultat);
    }

    public int getScore() {
        return (int) ((coins * dificultat.modificadorPuntos) + (vidas * MODIFICADOR_VIDAS)) * isWined;
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
}
