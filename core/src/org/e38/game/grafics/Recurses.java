package org.e38.game.grafics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.e38.game.model.npc.NPC;
import org.e38.game.persistance.ProfileManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class Recurses {
    public static final Sound MACHINE_GUN,
            SILENCER, ALARM, MP5_SMG,
            GUN, SNIPER_SHOT,
            SHOTGUN, EXPLOCION;
    public static final String SNIPER_BUENO = "sniperBueno",
            POLICIA_ESCOPETA = "policiaEscopeta",
            POLICIA_BAZOOKA = "policiaBazooka",
            POLICIA_BUENO = "policiaBueno";
    public static final String GRAFICS_TEXTURES_POLICIAS_PACK = "grafics/textures/policias.pack";

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

    public final AtomicBoolean isLoaded = new AtomicBoolean(false);
    private AssetManager manager;

    public TextureRegion getPolicia(String name, NPC.Orientation orientation) {
        TextureAtlas atlas;
        if (manager.isLoaded(GRAFICS_TEXTURES_POLICIAS_PACK)){
            atlas = manager.get(GRAFICS_TEXTURES_POLICIAS_PACK);
        }else {
            atlas = new TextureAtlas(GRAFICS_TEXTURES_POLICIAS_PACK);
        }
        return getNpc(atlas, name, orientation);
    }

    public TextureRegion getNpc(TextureAtlas atlas, String name, NPC.Orientation orientation) {
        String sufix = "";
        switch (orientation) {
            case DOWN:
                sufix = "0-0";
                break;
            case TOP:
                sufix = "3-0";
                break;
            case LEFT:
                sufix = "1-0";
                break;
            case RIGHT:
                sufix = "2-0";
                break;
            default:
                sufix = "0-0";
                break;
        }
        return atlas.findRegion(name + sufix);
    }


    public void load() {

        ProfileManager.getProfile();//init persistence Static Context
        // TODO: 4/27/16 init asserts
        manager = new AssetManager();
        manager.load(GRAFICS_TEXTURES_POLICIAS_PACK, TextureAtlas.class);

        //TEXTURAS
//        POLICEGUN = new Texture(TEXTURES_POLICIA_BUENO);
//        POLICEGUNFRONT = new TextureRegion(POLICEGUN, 0, 0, 27, 32);
//        POLICEGUNLEFT = new TextureRegion(POLICEGUN, 0, 32, 27, 32);
//        POLICEGUNRIGHT = new TextureRegion(POLICEGUN, 0, 64, 27, 32);
//        POLICEGUNBACK = new TextureRegion(POLICEGUN, 0, 86, 27, 32);
//
//        POLICEBAZOOKA = new Texture(TEXTURES_POLICIA_BAZOOKA);
//        POLICEBAZOOKAFRONT = new TextureRegion(POLICEBAZOOKA, 0, 0, 27, 47);
//        POLICEBAZOOKALEFT = new TextureRegion(POLICEBAZOOKA, 0, 32, 27, 47);
//        POLICEBAZOOKARIGHT = new TextureRegion(POLICEBAZOOKA, 0, 64, 27, 47);
//        POLICEBAZOOKABACK = new TextureRegion(POLICEBAZOOKA, 0, 86, 27, 47);
//
//        POLICESHOTGUN = new Texture(TEXTURES_POLICIA_ESCOPETA);
//        POLICESHOTGUNFRONT = new TextureRegion(POLICESHOTGUN, 0, 0, 27, 56);
//        POLICESHOTGUNLEFT = new TextureRegion(POLICESHOTGUN, 0, 32, 27, 56);
//        POLICESHOTGUNRIGHT = new TextureRegion(POLICESHOTGUN, 0, 64, 27, 56);
//        POLICESHOTGUNBACK = new TextureRegion(POLICESHOTGUN, 0, 86, 27, 56);
//
//        POLICESNIPER = new Texture(TEXTURES_SNIPER_BUENO);
//        POLICESNIPERFRONT = new TextureRegion(POLICESNIPER, 0, 0, 27, 60);
//        POLICESNIPERLEFT = new TextureRegion(POLICESNIPER, 0, 32, 27, 60);
//        POLICESNIPERRIGHT = new TextureRegion(POLICESNIPER, 0, 64, 27, 60);
//        POLICESNIPERBACK = new TextureRegion(POLICESNIPER, 0, 86, 27, 60);

        isLoaded.set(true);
//        return am;
    }
}
