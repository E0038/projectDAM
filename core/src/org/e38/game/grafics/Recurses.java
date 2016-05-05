package org.e38.game.grafics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
        isLoaded.set(true);
    }
}
