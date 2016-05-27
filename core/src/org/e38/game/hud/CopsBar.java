package org.e38.game.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.e38.game.World;
import org.e38.game.model.npc.Cop;

public class CopsBar implements Disposable, Bar {
    public Stage stage;
    public Table table;
    //    Skin skin;
    private Viewport viewport;
    private int money;

    public CopsBar(int money, float Y) {
        this.money = money;
//        skin = new Skin();
        viewport = new FitViewport(300, 200);

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
//        stage.addActor(table);
    }

    @Override
    public void updateBar(int money, Cop cop) {
    }

    ;

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
