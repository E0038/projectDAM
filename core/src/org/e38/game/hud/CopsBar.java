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
import org.e38.game.model.npc.Cop;

public class CopsBar implements Disposable, Bar {
    public Stage stage;
    OrthographicCamera cam;
    private Viewport viewport;
    Skin skin;
    private int money;
    public Table table;

    public CopsBar(int money, float Y) {
        this.money = money;
        cam = new OrthographicCamera();
//        skin = new Skin();
        viewport = new FitViewport(300, 200, cam);

        stage = new Stage(viewport);

        table = new Table();

        updateBar(money);
//        table.background(skin.newDrawable("cop_bar"));
        table.setSize(300, 26);
        table.setY(Y);

        stage.addActor(table);
    }

    @Override
    public void updateBar(int money) {
        skin = new Skin();
        if (money < 10)
            skin.add("cop_bar", new Texture("grafics/hud/cops/noMoney.png"));
        else if (money < 20)
            skin.add("cop_bar", new Texture("grafics/hud/cops/L20.png"));
        else if (money < 30)
            skin.add("cop_bar", new Texture("grafics/hud/cops/L30.png"));
        else if (money < 40)
            skin.add("cop_bar", new Texture("grafics/hud/cops/L40.png"));
        else
            skin.add("cop_bar", new Texture("grafics/hud/cops/All.png"));
        table.background(skin.newDrawable("cop_bar"));
        stage.addActor(table);
    }

    @Override
    public void updateColor(Cop cop) {

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
