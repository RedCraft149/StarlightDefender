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

public class DeathScreen implements Drawable, Disposable {
    Texture texture;
    boolean visible;
    BitmapFont font;

    float respawnTime = 0.2f;

    public DeathScreen() {
        texture = Files.texture("deathscreen");
        font = Files.font("arial",30);
    }

    @Override
    public void draw(RenderSystem system) {
        if(!visible) return;
        system.begin(RenderSystem.OVERLAY);
        system.overlays().draw(texture, (Gdx.graphics.getWidth()-1000) / 2f, (Gdx.graphics.getHeight() - 400) /2f);

        GlyphLayout layout = new GlyphLayout(font,formatRespawn());
        font.draw(system.overlays(),formatRespawn(),(Gdx.graphics.getWidth()-layout.width)*0.5f,Gdx.graphics.getHeight() / 4f);
        system.end(RenderSystem.OVERLAY);
    }

    @Override
    public void dispose() {
        texture.dispose();
        font.dispose();
    }

    private String formatRespawn() {
        String time = String.format(Locale.ENGLISH,"%.2f",respawnTime);
        while (time.length()<4) time += "0";
        return "Respawn in "+time+" seconds";
    }

    public void setRespawnTime(float time) {
        this.respawnTime = time;
    }

    public void show() {
        visible = true;
    }
    public void hide() {
        visible = false;
    }
}
