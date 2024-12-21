package com.redcraft.starlight.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.redcraft.communication.client.GClient;
import com.redcraft.communication.packets.handlers.PacketHandler;
import com.redcraft.starlight.client.effects.SoundSystem;
import com.redcraft.starlight.client.frame.FrameHandler;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.input.InputCache;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.server.SStarlightDefender;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.shared.MessagePacket;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.shared.packets.StarlightDefenderPacketList;

import java.util.Collections;
import java.util.Scanner;

public class CStarlightDefender extends ApplicationAdapter {

    RenderSystem renderSystem;
    FrameHandler frameHandler;
    InputCache inputCache;
    SoundSystem soundSystem;
    Connection connection;

    public CStarlightDefender(Connection connection) {
        this.connection = connection;
    }

    public void create() {

        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        //Gdx.input.setCursorCatched(true);

        CSetup.loadAll();
        Shared.CLIENT.set(CComponents.connection, connection);
        renderSystem = CSetup.beginGraphics();
        soundSystem = CSetup.beginSounds();

        frameHandler = new FrameHandler();
        frameHandler.add("connected", new ConnectedFrame(frameHandler)).resume();

        inputCache = new InputCache();
        Gdx.input.setInputProcessor(inputCache);
    }

    @Override
    public void render() {
        renderSystem.clear();
        frameHandler.loop(Gdx.graphics.getDeltaTime(), renderSystem, inputCache);

    }

    @Override
    public void dispose() {
        renderSystem.dispose();
        frameHandler.disposeAll();
        if (Shared.SERVER != null && Shared.SERVER.has(SConstants.server)) {
            Shared.SERVER.get(SConstants.main, SStarlightDefender.class).stop();
        }
        soundSystem.disposeAndClear();
    }
}
