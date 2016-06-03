package org.e38.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import org.e38.game.utils.World;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.hideStatusBar = true;
        config.touchSleepTime = 16;
        config.useGyroscope = false;
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useImmersiveMode = true;
        config.resolutionStrategy = new RatioResolutionStrategy(World.WORLD_WIDTH, World.WORLD_HEIGHT);
//        config.useGL30=true;
        initialize(new MainGame(), config);
    }
}
