package org.e38.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * Created by ALUMNEDAM on 11/05/2016.
 */
public class MoneyCount implements MediaDisposer.Disposable {
    public Stage stage;
    private Viewport viewport;
    private Label money;
    Skin skin;

    //  TO DO VOLVER A PONER 2 CAMARAS Y SETEAR EL X Y
    public MoneyCount(OrthographicCamera cam){
        skin = new Skin();
        viewport = new FitViewport(300, 200, cam);
        //new com.badlogic.gdx.utils.viewport.ScalingViewport()
        stage = new Stage(viewport);
        skin.add("white", new Texture("grafics/hud/top_bar.png"));

        //define a table used to organize our grafics.hud's labels
        Table table = new Table();

        table.background(skin.newDrawable("white"));
        table.setHeight(10);
        table.setWidth(80);
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        money = new Label(String.format("%03d", 888), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(money).expandX().padTop(10);

        //add our table to the stage
        stage.addActor(table);

        //table.add(new Image(skin.newDrawable("white"))).size(260, 25);

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
