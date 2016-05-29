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
import org.e38.game.utils.World;
import org.e38.game.model.Level;

public class TopBar implements Disposable, Level.OnChangeStateListener {
    public Stage stage;
    public Table table;
    private Label moneyl;
    private Label labelsl;

    public TopBar(int money, int labels) {
        Viewport viewport = new FitViewport(300, 200);

        stage = new Stage(viewport);

        table = new Table();

        table.background(new TextureRegionDrawable(new TextureRegion(World.getRecurses().top_bar)){@Override protected void finalize() throws Throwable {dispose();super.finalize();}});

        table.setSize(300, 26);

        table.setX(0);
        table.setY(174);

        moneyl = new Label(String.format("%03d", money), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelsl = new Label(String.format("%03d", labels), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(moneyl).padLeft((table.getWidth() / 10) * 3).padRight(1);
        table.add(labelsl).padLeft((table.getWidth() / 10) * 1);

        stage.addActor(table);
    }

    @Override
    protected void finalize() throws Throwable {
        this.dispose();
        super.finalize();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void onChangeState(int oldValue, int newValue, int type) {
        if (type == Level.TYPE_LIFE) {
            updateLifes(newValue);
        } else if (type == Level.TYPE_COIN) {
            updateCoins(newValue);
        }
    }

    public void updateLifes(int money) {
        moneyl.setText(String.format("%03d", money));
    }

    public void updateCoins(int labels) {
        labelsl.setText(String.format("%03d", labels));
    }
}
