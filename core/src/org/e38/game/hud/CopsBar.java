package org.e38.game.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.e38.game.model.Plaza;
import org.e38.game.model.npcs.Cop;
import org.e38.game.utils.World;

public class CopsBar implements Disposable, LowerBar {
    public Stage stage;
    public Table table;
    private Plaza plaza;

    public CopsBar(int money, float Y) {
        Viewport viewport = new FitViewport(300, 200);

        stage = new Stage(viewport);

        table = new Table();

        updateBar(money);
        table.setSize(300, 26);
        table.setY(Y);

        stage.addActor(table);
    }

    @Override
    public void updateBar(int money) {
        if (money < 10)
            table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().bar_no_money)));
        else if (money < 20)
            table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().bar_L20)));
        else if (money < 30)
            table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().bar_L30)));
        else if (money < 40)
            table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().bar_L40)));
        else
            table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().bar_ALL)));
    }

    @Override
    public void updateBar(int money, Cop cop) {
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
