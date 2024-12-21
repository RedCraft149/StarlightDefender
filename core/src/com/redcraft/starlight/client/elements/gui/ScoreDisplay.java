package com.redcraft.starlight.client.elements.gui;


import com.badlogic.gdx.Gdx;
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

    public ScoreDisplay() {
        font = Files.font("homevideo",30);
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).
                register(QuickEvent.listener("score_changed",this::onScoreChange));
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public void draw(RenderSystem system) {
        GlyphLayout layout = new GlyphLayout(font,"_____");
        float x = Gdx.graphics.getWidth()-layout.width - 20;
        float y = Gdx.graphics.getHeight()-layout.height - 20;
        system.begin(RenderSystem.OVERLAY);
        font.draw(system.overlays(),formatScore(),x,y);
        system.end(RenderSystem.OVERLAY);
    }

    private String formatScore() {
        return String.format(Locale.ENGLISH,"%05d",score);
    }

    public void onScoreChange(QuickEvent event) {
        score = event.readInt("score");
    }
}
