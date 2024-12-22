package com.redcraft.starlight.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.redcraft.starlight.client.effects.SoundSystem;
import com.redcraft.starlight.client.frame.FrameHandler;
import com.redcraft.starlight.client.frames.ConnectedFrame;
import com.redcraft.starlight.client.input.InputCache;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.server.SStarlightDefender;
import com.redcraft.starlight.server.SConstants;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.shared.Shared;

import java.io.PrintStream;
import java.io.PrintWriter;

public class CStarlightDefender extends ApplicationAdapter {

    RenderSystem renderSystem;
    FrameHandler frameHandler;
    InputCache inputCache;
    SoundSystem soundSystem;
    Connection connection;
    PrintStream stream;

    public CStarlightDefender(Connection connection) {
        this.connection = connection;
    }

    public void create() {

        //Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        FileHandle file = Gdx.files.external("out.txt");
        stream = new PrintStream(file.write(false));
        System.setOut(stream);
        try {
            Gdx.input.setCursorCatched(true);

            CSetup.loadAll();
            Shared.CLIENT.set(CComponents.connection, connection);
            renderSystem = CSetup.beginGraphics();
            soundSystem = CSetup.beginSounds();

            frameHandler = new FrameHandler();
            frameHandler.add("connected", new ConnectedFrame(frameHandler)).resume();

            inputCache = new InputCache();
            Gdx.input.setInputProcessor(inputCache);
        } catch (Exception e) {
            PrintWriter writer = new PrintWriter(Gdx.files.external("crash.txt").writer(false));
            e.printStackTrace(writer);
            writer.close();
        }
    }

    @Override
    public void render() {
        try {
            renderSystem.clear();
            frameHandler.loop(Gdx.graphics.getDeltaTime(), renderSystem, inputCache);
        } catch (Exception e) {
            PrintWriter writer = new PrintWriter(Gdx.files.external("crash.txt").writer(false));
            e.printStackTrace(writer);
            writer.close();
        }

    }

    @Override
    public void dispose() {
        stream.close();
        renderSystem.dispose();
        frameHandler.disposeAll();
        if (Shared.SERVER != null && Shared.SERVER.has(SConstants.server)) {
            Shared.SERVER.get(SConstants.main, SStarlightDefender.class).stop();
        }
        soundSystem.disposeAndClear();
    }
}
