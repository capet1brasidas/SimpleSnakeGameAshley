package com.jga.snake.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.jga.snake.assets.AssetDescriptors;
import com.jga.snake.assets.RegionNames;
import com.jga.snake.collision.CollisionListener;

public class SoundSystem extends EntitySystem implements CollisionListener {
    private Sound coinSound;
    private Sound loseSound;
    private final AssetManager assetManager;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        coinSound=assetManager.get(AssetDescriptors.COIN_SOUND);
        loseSound=assetManager.get(AssetDescriptors.LOSE_SOUND);
    }

    @Override
    public void update(float deltaTime) {
        //note: no processing
    }

    @Override
    public boolean checkProcessing() {
        //note: returning false since this is "passive" system
        return false;
    }

    @Override
    public void hitCoin() {
        coinSound.play();
    }

    @Override
    public void lose() {
        loseSound.play();
    }
}
