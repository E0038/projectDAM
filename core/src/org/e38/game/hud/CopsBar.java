package org.e38.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.jfxmediaimpl.MediaDisposer;

public class CopsBar implements MediaDisposer.Disposable {
    public Stage stage;
    OrthographicCamera cam;
    private Viewport viewport;
    Skin skin;
    private int money;
    private Table table;

    public CopsBar(int money){
        this.money = money;
        cam = new OrthographicCamera();
        skin = new Skin();
        viewport = new FitViewport(300, 200, cam);

        stage = new Stage(viewport);
        skin.add("cop_bar", new Texture("grafics/hud/cops/5.png"));

        table = new Table();

        table.background(skin.newDrawable("cop_bar"));

        table.setSize(300, 26);

        table.setX(0);
        table.setY(300);

        stage.addActor(table);
    }

   public void updateBar(int money){
       skin.add("cop_bar", new Texture("grafics/hud/cops/0.png"));
       table.background(skin.newDrawable("cop_bar"));
   }


    @Override
    public void dispose() {
        //TO DO
    }
    @Override
    public void finalize() {
        this.dispose();
    }
}
