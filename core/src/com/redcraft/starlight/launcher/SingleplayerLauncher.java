package com.redcraft.starlight.launcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.redcraft.starlight.ApplicationHook;
import com.redcraft.starlight.client.CStarlightDefender;
import com.redcraft.starlight.server.SStarlightDefender;
import com.redcraft.starlight.shared.Connection;
import com.redcraft.starlight.util.Files;

import java.io.PrintWriter;

public class SingleplayerLauncher extends ImageButton {

    public SingleplayerLauncher() {
        super(new TextureRegionDrawable(Files.texture("button_singleplayer")),
                new TextureRegionDrawable(Files.texture("button_singleplayer_down")));
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onClick(event,actor);
            }
        });
    }

    private void onClick(ChangeListener.ChangeEvent event, Actor actor) {
        try {
            SStarlightDefender.startServerOnNewThread(Connection.local());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            ApplicationHook.get().switchTo(new CStarlightDefender(Connection.local()));
        } catch (Exception e) {
            PrintWriter writer = new PrintWriter(Gdx.files.external("crash.txt").writer(false));
            e.printStackTrace(writer);
            writer.close();
        }
    }
}
