package com.redcraft.starlight.client.elements.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Disposable;
import com.redcraft.starlight.client.rendering.Drawable;
import com.redcraft.starlight.client.rendering.RenderSystem;
import com.redcraft.starlight.util.Files;

import java.util.Locale;

public class LevelClearedScreen implements Drawable, Disposable {

    Texture texture;
    boolean visible;
    BitmapFont font;

    float pauseTime = 0.2f;

    public LevelClearedScreen() {
        texture = Files.texture("level_cleared");
        font = Files.font("homevideo",30);
    }


    @Override
    public void dispose() {
        texture.dispose();
        font.dispose();
    }

    @Override
    public void draw(RenderSystem system) {
        if(!visible) return;
        system.begin(RenderSystem.OVERLAY);
        system.overlays().draw(texture, (Gdx.graphics.getWidth()-1000) / 2f, (Gdx.graphics.getHeight() - 400) /2f);

        GlyphLayout layout = new GlyphLayout(font, formatTime());
        font.draw(system.overlays(), formatTime(),(Gdx.graphics.getWidth()-layout.width)*0.5f,Gdx.graphics.getHeight() / 4f);
        system.end(RenderSystem.OVERLAY);
    }

    private String formatTime() {
        String time = String.format(Locale.ENGLISH,"%.1f",pauseTime);
        while (time.length()<3) time += "0";
        return "Continuing in "+time+" seconds";
    }

    public void setPauseTime(float time) {
        this.pauseTime = time;
    }

    public void show() {
        visible = true;
    }
    public void hide() {
        visible = false;
    }
}
