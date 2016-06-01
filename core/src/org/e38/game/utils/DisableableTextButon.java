package org.e38.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by sergi on 6/1/16.
 */
public class DisableableTextButon extends TextButton {
    public DisableableTextButon(String text, Skin skin) {
        super(text, skin);
    }

    public DisableableTextButon(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public DisableableTextButon(String text, TextButtonStyle style) {
        super(text, style);
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        getStyle().fontColor = isDisabled ? Color.GRAY : Color.BLACK;
        setTouchable(isDisabled ? Touchable.disabled : Touchable.enabled);
        super.setDisabled(isDisabled);
    }
}
