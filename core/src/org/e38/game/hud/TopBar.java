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

public class TopBar implements Disposable, Level.OnChangeStateListener {
    public Stage stage;
    OrthographicCamera cam;
    private Viewport viewport;
    private Label moneyl;
    private Label labelsl;
    Skin skin;
    private int money, labels;
    public Table table;

    public TopBar(int money, int labels){
        this.money = money;
        this.labels = labels;
        cam = new OrthographicCamera();
        skin = new Skin();
        viewport = new FitViewport(300, 200, cam);

        stage = new Stage(viewport);
        skin.add("top_bar", new Texture("grafics/hud/top_bar.png"));

        table = new Table();

        table.background(skin.newDrawable("top_bar"));

        table.setSize(300, 26);

        table.setX(0);
        table.setY(174);

        moneyl = new Label(String.format("%03d", money), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelsl = new Label(String.format("%03d", labels), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(moneyl).padLeft((table.getWidth()/10)*3).padRight(1);
        table.add(labelsl).padLeft((table.getWidth()/10)*1);

        stage.addActor(table);
    }

   public void updateMoney(int money){
        this.money = money;
        moneyl.setText(String.format("%03d", money));
   }

   public void updateLabels(int labels){
        this.labels = labels;
        labelsl.setText(String.format("%03d", labels));
   }

    @Override
    public void dispose() {
        stage.dispose();
    }
    @Override
    public void finalize() {
        this.dispose();
    }

    @Override
    public void onChangeState(int oldValue, int newValue, int type) {
        if(type == Level.TYPE_LIFE){
            updateMoney(newValue);
        System.out.println("money");}
        else{
            updateLabels(newValue);
        System.out.println("labels");}
    }
}
