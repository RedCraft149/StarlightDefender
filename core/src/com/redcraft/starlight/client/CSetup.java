package com.redcraft.starlight.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.redcraft.starlight.client.effects.SoundSystem;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;

public class CSetup {

    public static void loadAll() {
        Shared.initSharedClient();
        CConstants.loadAll();
    }

    public static RenderSystem beginGraphics() {
        float ration = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        float screenChunkSize = Shared.CLIENT.getFloat(CComponents.chunksOnScreen);
        Shared.CLIENT.set(CComponents.projectionDimensions,new Vector2(screenChunkSize,screenChunkSize*ration));

        RenderSystem system = new RenderSystem(new Matrix4().setToOrtho2D(0f,0f,screenChunkSize,screenChunkSize*ration));
        Shared.CLIENT.set(CComponents.renderSystem,system);

        system.sprites().add("spark","spark",4096);
        system.sprites().add("bullet","bullet",4096);
        system.sprites().add("player","player",16);
        system.sprites().add("enemy1","enemy1",4096);
        system.sprites().add("enemy2","enemy2",4096);
        system.sprites().add("rock","rock",4096);
        system.sprites().add("mine","mine",4096);

        return system;
    }

    public static SoundSystem beginSounds() {
        SoundSystem soundSystem = new SoundSystem();
        Shared.CLIENT.set(CComponents.soundSystem,soundSystem);

        soundSystem.createSound("shot","shot");
        soundSystem.createSound("explosion","explosion2");
        soundSystem.createSound("death","death");

        return soundSystem;
    }
}
