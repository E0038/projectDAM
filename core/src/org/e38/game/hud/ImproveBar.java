package org.e38.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.e38.game.model.npc.Cop;

public class ImproveBar implements Disposable {
    public Stage stage;
    OrthographicCamera cam;
    private Viewport viewport;
    private Label mejorar;
    private Label vender;
    Skin skin;
    private int money;
    public Table table;
    Cop cop;

    public ImproveBar(int money, float y, Cop cop){
        this.cop = cop;
        this.money = money;
        cam = new OrthographicCamera();
        skin = new Skin();
        viewport = new FitViewport(300, 200, cam);

        stage = new Stage(viewport);
        skin.add("improve_bar", new Texture("grafics/hud/improve_cop.png"));

        table = new Table();

        table.background(skin.newDrawable("improve_bar"));

        table.setSize(300, 26);

        table.setX(0);
        table.setY(y);

        mejorar = new Label("Mejorar", new Label.LabelStyle(new BitmapFont(), Color.RED));
        vender = new Label("Vender", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        vender.setFontScale(0.7f);
        mejorar.setFontScale(0.7f);

        table.add(mejorar);
        table.add(vender).padLeft(50);

        stage.addActor(table);
    }

   public void updateColor(){
        mejorar.setColor(cop.isUpgradeAvailable() ? Color.GREEN: Color.RED);
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
