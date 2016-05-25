package org.e38.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import org.e38.game.model.npc.Cop;
import org.e38.game.model.npc.Criminal;
import org.e38.game.model.npc.NPC;
import org.e38.game.persistance.ProfileManager;
import org.e38.game.screens.AnimationManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sergi on 4/27/16.
 */
public class Recurses implements Disposable {
    public static final String SNIPER_BUENO = "sniperBueno",
            POLICIA_ESCOPETA = "policiaEscopeta",
            POLICIA_BAZOOKA = "policiaBazooka",
            POLICIA_BUENO = "policiaBueno";

    public static final String GRAFICS_TEXTURES_POLICIAS_PACK = "grafics/textures/policias.pack";
    public static final String GRAFICS_TEXTURES_ACRIMINALS_PACK = "grafics/textures/Acriminals.pack";
    public static final int CRIMINAL_PARTS = 3;
    public static final float FRAME_DURATION = 0.5f;
    public static Sound MACHINE_GUN,
            SILENCER, ALARM, MP5_SMG,
            GUN, SNIPER_SHOT, RPG,
            SHOTGUN, EXPLOCION;
    public final AtomicBoolean isLoaded = new AtomicBoolean(false);
    public TextureAtlas atlasButtons;
    public Texture buttonBg;
    private TextureAtlas atlasPolicias;
    private TextureAtlas atlasAnimCriminals;

    /**
     * NOTE : create the instance in Gdx thread and call load in a separate Tread
     */
    public Recurses() {
        createGrafics();
    }

    private void createGrafics() {
        //create no final Texture Here
        atlasPolicias = new TextureAtlas(GRAFICS_TEXTURES_POLICIAS_PACK) {
            @Override
            protected void finalize() throws Throwable {
                dispose();//automatic dispose on GC
                super.finalize();
            }
        };
        atlasAnimCriminals = new TextureAtlas(GRAFICS_TEXTURES_ACRIMINALS_PACK) {
            @Override
            protected void finalize() throws Throwable {
                dispose();
                super.finalize();
            }
        };
        atlasButtons = new TextureAtlas();

        buttonBg = new Texture("grafics/textures/buttonBack.png");
        atlasButtons.addRegion("btt_bg", TextureRegion.split(buttonBg, buttonBg.getWidth(), buttonBg.getHeight())[0][0]);
    }

    public TextureRegion getPolicia(Cop cop) {
        return getNpc(atlasPolicias, cop.getName(), cop.getOrientation());
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

    public AnimationManager getACriminal(Criminal criminal) {
        return getACriminal(criminal.getName(), criminal.getOrientation());
    }

    public AnimationManager getACriminal(String name, NPC.Orientation orientation) {
        TextureRegion region = getCriminal(name, orientation);
        TextureRegion[][] regions = region.split(region.getRegionWidth() / CRIMINAL_PARTS, region.getRegionHeight());
        Animation animation = new Animation(FRAME_DURATION, regions[0]);
        animation.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        return new AnimationManager(animation);
    }

    public TextureRegion getCriminal(String name, NPC.Orientation orientation) {
        return getNpc(atlasAnimCriminals, name, orientation);
    }

    public TextureRegion getCriminal(Criminal criminal) {
        return getNpc(atlasAnimCriminals, criminal);
    }

    public TextureRegion getNpc(TextureAtlas atlas, NPC npc) {
        return getNpc(atlas, npc.getName(), npc.getOrientation());
    }

    public TextureRegion getPolicia(String name, NPC.Orientation orientation) {
        return getNpc(atlasPolicias, name, orientation);
    }

    /**
     * Do all hard IO load operations ,NO Texture Load here !!.
     * Is recommended to run this on separate thread
     */
    public void load() {
        ProfileManager.getInstance();//load static context
        createSounds();
        // TODO: 4/27/16 init asserts
        isLoaded.set(true);
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

    public enum AnimatedCriminals {
        bane, bicicletaFinal, enemigoEspadon, ladronEscopetaBueno;
    }

    @Override
    public void dispose() {
        atlasAnimCriminals.dispose();
        atlasPolicias.dispose();
        buttonBg.dispose();
        atlasButtons.dispose();

        MACHINE_GUN.dispose();
        SILENCER.dispose();
        ALARM.dispose();
        SHOTGUN.dispose();
        MP5_SMG.dispose();
        EXPLOCION.dispose();
        GUN.dispose();
        SNIPER_SHOT.dispose();
        RPG.dispose();

    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }


}
