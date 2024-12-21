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

public class AmmunitionDisplay implements Drawable, Disposable {

    BitmapFont font;
    int ammo = 100;
    boolean upToDate = false;

    public AmmunitionDisplay() {
        font = Files.font("homevideo",30);
        Shared.CLIENT.get(CComponents.gameEventHandler, EventHandler.class).
                register(QuickEvent.listener("ammo_changed",this::onAmmoChange));
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public void draw(RenderSystem system) {
        GlyphLayout layout = new GlyphLayout(font,"____ AMMO");
        float x = Gdx.graphics.getWidth() - layout.width - 20;
        float y = 40 + layout.height;
        system.begin(RenderSystem.OVERLAY);
        font.setColor(upToDate ? Color.WHITE : Color.GRAY);
        font.draw(system.overlays(),formatAmmo(),x,y);
        system.end(RenderSystem.OVERLAY);
    }

    private String formatAmmo() {
        return String.format(Locale.ENGLISH,"%04d AMMO",ammo);
    }

    public void onAmmoChange(QuickEvent event) {
        ammo = event.readInt("ammo");
        upToDate = true;
    }
}
