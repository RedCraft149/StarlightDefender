package com.redcraft.starlight.client.elements.gui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.CComponents;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.shared.Shared;
import com.redcraft.starlight.util.Files;
import com.redcraft.rlib.events.EventHandler;
import com.redcraft.rlib.events.QuickEvent;

import java.util.Locale;

public class ScoreDisplay implements Drawable, Disposable {

    BitmapFont font;
    int score;
    float destroyedSpaceStation;

    public ScoreDisplay() {
        font = Files.font("homevideo",30);
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).
                register(QuickEvent.listener("score_changed",this::onScoreChange));
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).
                register(QuickEvent.listener("space_station_destroyed",this::onSpaceStationDestroyed));
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public void draw(RenderSystem system) {
        destroyedSpaceStation -= Gdx.graphics.getDeltaTime();
        GlyphLayout layout = new GlyphLayout(font, (destroyedSpaceStation > 0f ? "SPACE STATION DESTROYED   " : "")+"_____");
        float x = Gdx.graphics.getWidth()-layout.width - 20;
        float y = Gdx.graphics.getHeight()-layout.height - 20;
        system.begin(RenderSystem.OVERLAY);
        if(destroyedSpaceStation > 0f) {
            if(destroyedSpaceStation % 1f < 0.5f) font.setColor(Color.GREEN);
            else font.setColor(Color.CYAN);
        }
        font.draw(system.overlays(),formatScore(),x,y);
        font.setColor(Color.WHITE);
        system.end(RenderSystem.OVERLAY);
    }

    private String formatScore() {
        return (destroyedSpaceStation > 0f ? "SPACE STATION DESTROYED   " : "")+String.format(Locale.ENGLISH,"%05d",score);
    }

    public void onScoreChange(QuickEvent event) {
        if(!event.matches("score_changed")) return;
        score = event.readInt("score");
    }
    public void onSpaceStationDestroyed(QuickEvent event) {
        if(!event.matches("space_station_destroyed")) return;
        destroyedSpaceStation = 5f;
    }
}
