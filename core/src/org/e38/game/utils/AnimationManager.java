package org.e38.game.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by sergi on 5/11/16.
 */
public class AnimationManager {
    private float timeState = 0f, frameDuration;
    private Animation animation;
    private int frames;

    public AnimationManager(Animation animation) {
        setAnimation(animation);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        frames = this.animation.getKeyFrames().length;
        frameDuration = this.animation.getFrameDuration();
    }

    public TextureRegion update(float delta) {
        timeState = timeState > (frameDuration * frames) + animation.getPlayMode().ordinal() ? 0 : timeState;//reset
        timeState += delta;
        return animation.getKeyFrame(timeState);
    }
}
