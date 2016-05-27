package org.e38.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.e38.game.model.Level;
import org.e38.game.model.Plaza;
import org.e38.game.model.npc.Cop;

public class UpgradeBar implements Disposable, Bar{
    public Stage stage;
    OrthographicCamera cam;
    private Viewport viewport;
    private Label mejorar;
    private Label vender;
    Skin skin;
    private int money;
    public Table table;


    public UpgradeBar(int money, float y){
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

    @Override
    public void updateBar(int money) {
    }

    @Override
    public void updateBar(int money, Cop cop) {
        mejorar.getStyle().fontColor = money < cop.getNivel().getPrecioCompra()&& cop.isUpgradeAvailable() ? Color.RED: Color.GREEN;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    @Override
    public void finalize() {
        this.dispose();
    }
}
