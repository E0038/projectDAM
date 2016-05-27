package org.e38.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.e38.game.World;
import org.e38.game.model.npc.Cop;

public class UpgradeBar implements Disposable, Bar {
    public Stage stage;
    public Table table;
    private Viewport viewport;
    private Label mejorar;
    private Label vender;

    public UpgradeBar(float y) {
        Skin skin = new Skin();
        viewport = new FitViewport(300, 200);

        stage = new Stage(viewport);
        skin.add("improve_bar", new TextureRegion(World.getRecurses().upgrade_bar){@Override protected void finalize() throws Throwable {dispose();super.finalize();}});

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
        mejorar.getStyle().fontColor = money > cop.getNivel().getPrecioCompra() && cop.isUpgradeAvailable() ? Color.GREEN : Color.RED;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void finalize() throws Throwable {
        this.dispose();
        super.finalize();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
