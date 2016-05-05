package org.e38.game.grafics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.e38.game.persistance.ProfileManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class Recurses {
    public final static AtomicBoolean isLoaded = new AtomicBoolean(false);
    public static final Sound MACHINE_GUN,
            SILENCER, ALARM, MP5_SMG,
            GUN, SNIPER_SHOT,
            SHOTGUN, EXPLOCION;
    public static  Texture POLICEGUN, POLICEBAZOOKA, POLICESHOTGUN, POLICESNIPER ;
    public static  TextureRegion POLICEGUNFRONT, POLICEGUNBACK, POLICEGUNRIGHT, POLICEGUNLEFT,
            POLICEBAZOOKAFRONT, POLICEBAZOOKABACK, POLICEBAZOOKARIGHT, POLICEBAZOOKALEFT,
            POLICESHOTGUNFRONT, POLICESHOTGUNBACK, POLICESHOTGUNRIGHT, POLICESHOTGUNLEFT,
            POLICESNIPERFRONT, POLICESNIPERBACK, POLICESNIPERRIGHT, POLICESNIPERLEFT;
    private AssetManager am = new AssetManager();


    static {
        MACHINE_GUN = Gdx.audio.newSound(Gdx.files.internal("audio/Automatic_MachineGun.mp3"));
        SILENCER = Gdx.audio.newSound(Gdx.files.internal("audio/Silencer.mp3"));
        ALARM = Gdx.audio.newSound(Gdx.files.internal("audio/Police_Siren.mp3"));
        SHOTGUN = Gdx.audio.newSound(Gdx.files.internal("audio/shot_gun.mp3"));
        MP5_SMG = Gdx.audio.newSound(Gdx.files.internal("audio/MP5_SMG.mp3"));
        EXPLOCION = Gdx.audio.newSound(Gdx.files.internal("audio/Grenade_Explosion.mp3"));
        GUN = Gdx.audio.newSound(Gdx.files.internal("audio/gunshot.mp3"));
        SNIPER_SHOT = Gdx.audio.newSound(Gdx.files.internal("audio/sniper_shot.mp3"));


    }


    public static void load() {

        ProfileManager.getProfile();//init persistence Static Context
        // TODO: 4/27/16 init asserts

        //TEXTURAS
        POLICEGUN = new Texture("data/textures/policiaBueno.png");
        POLICEGUNFRONT = new TextureRegion(POLICEGUN, 0, 0, 27, 32);
        POLICEGUNLEFT = new TextureRegion(POLICEGUN, 0, 32, 27, 32);
        POLICEGUNRIGHT = new TextureRegion(POLICEGUN, 0, 64, 27, 32);
        POLICEGUNBACK = new TextureRegion(POLICEGUN, 0, 86, 27, 32);

        POLICEBAZOOKA = new Texture("data/textures/policiaBazooka.png");
        POLICEBAZOOKAFRONT = new TextureRegion(POLICEBAZOOKA, 0, 0, 27, 47);
        POLICEBAZOOKALEFT = new TextureRegion(POLICEBAZOOKA, 0, 32, 27, 47);
        POLICEBAZOOKARIGHT = new TextureRegion(POLICEBAZOOKA, 0, 64, 27, 47);
        POLICEBAZOOKABACK = new TextureRegion(POLICEBAZOOKA, 0, 86, 27, 47);

        POLICESHOTGUN = new Texture("data/textures/policiaEscopeta.png");
        POLICESHOTGUNFRONT = new TextureRegion(POLICESHOTGUN, 0, 0, 27, 56);
        POLICESHOTGUNLEFT = new TextureRegion(POLICESHOTGUN, 0, 32, 27, 56);
        POLICESHOTGUNRIGHT = new TextureRegion(POLICESHOTGUN, 0, 64, 27, 56);
        POLICESHOTGUNBACK = new TextureRegion(POLICESHOTGUN, 0, 86, 27, 56);

        POLICESNIPER = new Texture("/data/textures/sniperBueno.png");
        POLICESNIPERFRONT = new TextureRegion(POLICESNIPER, 0, 0, 27, 60);
        POLICESNIPERLEFT = new TextureRegion(POLICESNIPER, 0, 32, 27, 60);
        POLICESNIPERRIGHT = new TextureRegion(POLICESNIPER, 0, 64, 27, 60);
        POLICESNIPERBACK = new TextureRegion(POLICESNIPER, 0, 86, 27, 60);

        isLoaded.set(true);
        return am;
    }
}
