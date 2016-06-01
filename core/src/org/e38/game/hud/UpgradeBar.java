package org.e38.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.e38.game.model.Plaza;
import org.e38.game.model.npcs.Cop;
import org.e38.game.utils.World;

public class UpgradeBar implements Disposable, LowerBar {
    public Stage stage;
    public Table table;
    @SuppressWarnings("FieldCanBeLocal")
    private Label mejorar;
    @SuppressWarnings("FieldCanBeLocal")
    private Label vender;
    private Plaza plaza;

    public UpgradeBar(float y) {
        Viewport viewport = new FitViewport(300, 200);

        stage = new Stage(viewport);

        table = new Table();

        table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().upgrade_bar) {
            @Override
            protected void finalize() throws Throwable {
                dispose();
                super.finalize();
            }
        }));

        table.setSize(300, 26);

        table.setX(0);
        table.setY(y);

        mejorar = new Label("Upgrade", new Label.LabelStyle(new BitmapFont(), Color.RED));
        vender = new Label("Sell", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        vender.setFontScale(0.7f);
        mejorar.setFontScale(0.7f);

        table.add(mejorar);
        table.add(vender).padLeft(50);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void updateBar(int money) {
    }

    @Override
    public void updateBar(int money, Cop cop) {
        mejorar.getStyle().fontColor = money > cop.getNivel().getPrecioCompra() && cop.isUpgradeAvailable() ? Color.GREEN : Color.RED;
    }

    @Override
    public Plaza getPlaza() {
        return plaza;
    }

    @Override
    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    @Override
    public Stage getStage() {
        return stage;
    }    @Override
    protected void finalize() throws Throwable {
        this.dispose();
        super.finalize();
    }


}
