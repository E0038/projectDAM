package org.e38.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.e38.game.model.npc.Cop;
import org.e38.game.model.npc.NPC;
import org.e38.game.persistance.ProfileManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class Recurses {
    public static final String SNIPER_BUENO = "sniperBueno",
            POLICIA_ESCOPETA = "policiaEscopeta",
            POLICIA_BAZOOKA = "policiaBazooka",
            POLICIA_BUENO = "policiaBueno";
    public static final String GRAFICS_TEXTURES_POLICIAS_PACK = "grafics/textures/policias.pack";
    public static Sound MACHINE_GUN,
            SILENCER, ALARM, MP5_SMG,
            GUN, SNIPER_SHOT, RPG,
            SHOTGUN, EXPLOCION;

    static {
    }

    public final AtomicBoolean isLoaded = new AtomicBoolean(false);
    private final TextureAtlas ATLAS_POLICIAS;

    /**
     * NOTE : create the instance in Gdx thread and call load in a separate Tread
     */
    public Recurses() {
        ATLAS_POLICIAS = new TextureAtlas(GRAFICS_TEXTURES_POLICIAS_PACK) {
            @Override
            protected void finalize() throws Throwable {
                dispose();//automatic dispose on GC
                super.finalize();
            }
        };
        createGrafics();
    }

    private void createGrafics() {
        //create no final Texture Here
    }

    public TextureRegion getPolicia(Cop cop) {
        return getNpc(ATLAS_POLICIAS, cop.getName(), cop.getOrientation());
    }

    public TextureRegion getNpc(TextureAtlas atlas, String name, NPC.Orientation orientation) {
        String sufix;
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

    public TextureRegion getPolicia(String name, NPC.Orientation orientation) {
        return getNpc(ATLAS_POLICIAS, name, orientation);
    }

    public TextureRegion getNpc(TextureAtlas atlas, NPC npc) {
        return getNpc(atlas, npc.getName(), npc.getOrientation());
    }

    /**
     * Do all hard IO load operations ,NO Texture Load here !!.
     * Is recommended to run this on separate thread
     */
    public void load() {
        ProfileManager.getProfile();//init persistence Static Context
        createSounds();
        // TODO: 4/27/16 init asserts
        isLoaded.set(true);
//        return am;
    }

    private void createSounds() {
        MACHINE_GUN = Gdx.audio.newSound(Gdx.files.internal("audio/Automatic_MachineGun.mp3"));
        SILENCER = Gdx.audio.newSound(Gdx.files.internal("audio/Silencer.mp3"));
        ALARM = Gdx.audio.newSound(Gdx.files.internal("audio/Police_Siren.mp3"));
        SHOTGUN = Gdx.audio.newSound(Gdx.files.internal("audio/shot_gun.mp3"));
        MP5_SMG = Gdx.audio.newSound(Gdx.files.internal("audio/MP5_SMG.mp3"));
        EXPLOCION = Gdx.audio.newSound(Gdx.files.internal("audio/Grenade_Explosion.mp3"));
        GUN = Gdx.audio.newSound(Gdx.files.internal("audio/gunshot.mp3"));
        SNIPER_SHOT = Gdx.audio.newSound(Gdx.files.internal("audio/sniper_shot.mp3"));
        RPG = Gdx.audio.newSound(Gdx.files.internal("audio/RPG.mp3"));
    }
}
